package com.huiju.weixin.cp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpDepart;
import com.huiju.weixin.cp.bean.WxCpTag;
import com.huiju.weixin.cp.bean.WxCpUser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.huiju.weixin.common.bean.WxAccessToken;
import com.huiju.weixin.common.bean.WxJsapiSignature;
import com.huiju.weixin.common.bean.WxMenu;
import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.result.WxError;
import com.huiju.weixin.common.result.WxMediaUploadResult;
import com.huiju.weixin.common.session.StandardSessionManager;
import com.huiju.weixin.common.session.WxSession;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.common.util.RandomUtils;
import com.huiju.weixin.common.util.StringUtils;
import com.huiju.weixin.common.util.crypto.SHA1;
import com.huiju.weixin.common.util.fs.FileUtils;
import com.huiju.weixin.common.util.http.MediaDownloadRequestExecutor;
import com.huiju.weixin.common.util.http.MediaUploadRequestExecutor;
import com.huiju.weixin.common.util.http.RequestExecutor;
import com.huiju.weixin.common.util.http.SimpleGetRequestExecutor;
import com.huiju.weixin.common.util.http.SimplePostRequestExecutor;
import com.huiju.weixin.common.util.http.URIUtil;
import com.huiju.weixin.common.util.json.GsonHelper;
import com.huiju.weixin.cp.bean.WxCpKfList;
import com.huiju.weixin.cp.bean.WxCpKfMessage;
import com.huiju.weixin.cp.bean.WxCpMessage;
import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;

public class WxCpServiceImpl implements WxCpService {

  protected final Logger log = LoggerFactory.getLogger(WxCpServiceImpl.class);
  /**
   * 全局的是否正在刷新access token的锁
   */
  protected final Object globalAccessTokenRefreshLock = new Object();
  /**
   * 全局的是否正在刷新jsapi_ticket的锁
   */
  protected final Object globalJsapiTicketRefreshLock = new Object();
  /**
   * 微信客户端配置存储.
   */
  protected WxCpConfigStorage wxCpConfigStorage;
  /**
   * http客户端
   */
  protected CloseableHttpClient httpClient;
  /**
   * http代理
   */
  protected HttpHost httpProxy;
  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   */
  private int retrySleepMillis = 1000;
  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   */
  private int maxRetryTimes = 5;
  /**
   * 会话管理
   */
  protected WxSessionManager sessionManager = new StandardSessionManager();
  /**
   * 临时文件目录
   */
  protected File tmpDirFile;

  /**
   * <pre>
   * 验证推送过来的消息的正确性/被动响应消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   *
   * @param msgSignature 签名，验证调用者的合法性
   * @param timestamp 时间戳，企业指定
   * @param nonce 企业指定
   * @param data 微信传输过来的数据，有可能是echoStr，有可能是xml消息
   * @return 返回boolean类型变量
   */
  public boolean checkSignature(String msgSignature, String timestamp, String nonce, String data) {
    try {
      return SHA1.gen(wxCpConfigStorage.getToken(), timestamp, nonce, data).equals(msgSignature);
    } catch (Exception e) {
      return false;
    }
  }
  
  /**
   * <pre>
   *   用在二次验证的时候
   *   企业在员工验证成功后，调用本方法告诉企业号平台该员工关注成功。
   * </pre>
   *
   * @param userId 员工ID
   * @throws WxErrorException
   */
  public void userAuthenticated(String userId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?userid=" + userId;
    get(url, null);
  }

  /**
   * 获取access_token, 不强制刷新access_token
   * @see #getAccessToken(boolean)
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getAccessToken() throws WxErrorException {
    return getAccessToken(false);
  }
  
  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   * 另：本service的所有方法都会在access_token过期是调用此方法
   * 程序员在非必要情况下尽量不要主动调用此方法
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   * @param forceRefresh 强制刷新
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    if (forceRefresh) {
      wxCpConfigStorage.expireAccessToken();
    }
    if (wxCpConfigStorage.isAccessTokenExpired()) {
      synchronized (globalAccessTokenRefreshLock) {
        if (wxCpConfigStorage.isAccessTokenExpired()) {
          String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
              + "&corpid=" + wxCpConfigStorage.getCorpId()
              + "&corpsecret=" + wxCpConfigStorage.getCorpSecret();
          try {
            HttpGet httpGet = new HttpGet(url);
            if (httpProxy != null) {
              RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
              httpGet.setConfig(config);
            }
            CloseableHttpClient httpclient = getHttpclient();
            String resultContent = null;
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
              resultContent = new BasicResponseHandler().handleResponse(response);
            }
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
              throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            wxCpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
          } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    return wxCpConfigStorage.getAccessToken();
  }

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   * @see #getJsapiTicket(boolean)
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getJsapiTicket() throws WxErrorException {
    return getJsapiTicket(false);
  }
  
  /**
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://qydev.weixin.qq.com/wiki/index.php?title=微信JS接口#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param forceRefresh 强制刷新
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
    if (forceRefresh) {
      wxCpConfigStorage.expireJsapiTicket();
    }
    if (wxCpConfigStorage.isJsapiTicketExpired()) {
      synchronized (globalJsapiTicketRefreshLock) {
        if (wxCpConfigStorage.isJsapiTicketExpired()) {
          String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
          String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
          JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
          JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
          String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
          int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
          wxCpConfigStorage.updateJsapiTicket(jsapiTicket, expiresInSeconds);
        }
      }
    }
    return wxCpConfigStorage.getJsapiTicket();
  }

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://qydev.weixin.qq.com/wiki/index.php?title=微信JS接口#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param url 链接
   * @throws WxErrorException
   */
  public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException {
    long timestamp = System.currentTimeMillis() / 1000;
    String noncestr = RandomUtils.getRandomStr();
    String jsapiTicket = getJsapiTicket(false);
    try {
      String signature = SHA1.genWithAmple(
          "jsapi_ticket=" + jsapiTicket,
          "noncestr=" + noncestr,
          "timestamp=" + timestamp,
          "url=" + url
      );
      WxJsapiSignature jsapiSignature = new WxJsapiSignature();
      jsapiSignature.setTimestamp(timestamp);
      jsapiSignature.setNoncestr(noncestr);
      jsapiSignature.setUrl(url);
      jsapiSignature.setSignature(signature);
      return jsapiSignature;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 发送消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送消息
   * </pre>
   *
   * @param message 发送的消息
   * @throws WxErrorException
   */
  public void messageSend(WxCpMessage message) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
    post(url, message.toJson());
  }

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #menuCreate(String, WxMenu)
   *
   * @param menu 菜单
   * @throws WxErrorException
   */
  @Override
  public void menuCreate(WxMenu menu) throws WxErrorException {
    menuCreate(wxCpConfigStorage.getAgentId(), menu);
  }
  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   *
   * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
   * </pre>
   * @see #menuCreate(WxMenu)
   *
   * @param agentId 企业号应用的id
   * @param menu 菜单
   * @throws WxErrorException
   */
  @Override
  public void menuCreate(String agentId, WxMenu menu) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?agentid=" + wxCpConfigStorage.getAgentId();
    post(url, menu.toJson());
  }

  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #menuDelete(String)
   *
   * @throws WxErrorException
   */
  @Override
  public void menuDelete() throws WxErrorException {
    menuDelete(wxCpConfigStorage.getAgentId());
  }
  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   *
   * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
   * </pre>
   * @see #menuDelete()
   *
   * @param agentId 企业号应用的id
   * @throws WxErrorException
   */
  @Override
  public void menuDelete(String agentId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?agentid=" + agentId;
    get(url, null);
  }

  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #menuGet(String)
   *
   * @return WxMenu 
   * @throws WxErrorException
   */
  @Override
  public WxMenu menuGet() throws WxErrorException {
    return menuGet(wxCpConfigStorage.getAgentId());
  }

  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   *
   * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
   * </pre>
   * @see #menuGet()
   *
   * @param agentId 企业号应用的id
   * @return WxMenu
   * @throws WxErrorException
   */
  @Override
  public WxMenu menuGet(String agentId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?agentid=" + agentId;
    try {
      String resultContent = get(url, null);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据
      if (e.getError().getErrorCode() == 46003) {
        return null;
      }
      throw e;
    }
  }

  /**
   * <pre>
   * 上传多媒体文件
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 1M，支持JPG格式
   *   语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
   *   视频（video）：10MB，支持MP4格式
   *   缩略图（thumb）：64KB，支持JPG格式
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   *
   * @param mediaType   媒体类型, 请看{@link WxConsts}
   * @param fileType    文件类型，请看{@link WxConsts}
   * @param inputStream 输入流
   * @throws WxErrorException
   */
  public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream)
      throws WxErrorException, IOException {
    return mediaUpload(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
  }

  /**
   * 上传多媒体文件
   * @param mediaType 媒体类型
   * @param file 文件
   * @throws WxErrorException
   * @see #mediaUpload(String, String, InputStream)
   */
  public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
    return execute(new MediaUploadRequestExecutor(), url, file);
  }

  /**
   * <pre>
   * 下载多媒体文件
   * 根据微信文档，视频文件下载不了，会返回null
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   *
   * @param media_id 媒体文件上传后获取的唯一标识
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   */
  public File mediaDownload(String media_id) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get";
    return execute(new MediaDownloadRequestExecutor(wxCpConfigStorage.getTmpDirFile()), url, "media_id=" + media_id);
  }


  /**
   * <pre>
   * 部门管理接口 - 创建部门
   * 最多支持创建500个部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   *
   * @param depart 部门
   * @return 返回integer类型变量
   * @throws WxErrorException
   */
  public Integer departCreate(WxCpDepart depart) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/create";
    String responseContent = execute(
        new SimplePostRequestExecutor(),
        url,
        depart.toJson());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return GsonHelper.getAsInteger(tmpJsonElement.getAsJsonObject().get("id"));
  }

  /**
   * <pre>
   * 部门管理接口 - 修改部门名
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * 如果id为0(未部门),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   *
   * @param group 要更新的group，group的id,name必须设置
   * @throws WxErrorException
   */
  public void departUpdate(WxCpDepart group) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/update";
    post(url, group.toJson());
  }

  /**
   * <pre>
   * 部门管理接口 - 删除部门
   * </pre>
   *
   * @param departId 部门ID
   * @throws WxErrorException
   */
  public void departDelete(Integer departId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?id=" + departId;
    get(url, null);
  }

  /**
   * <pre>
   * 部门管理接口 - 查询所有部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   *
   * @return 部门集合
   * @throws WxErrorException
   */
  public List<WxCpDepart> departGet() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
    String responseContent = get(url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("department"),
            new TypeToken<List<WxCpDepart>>() {
            }.getType()
        );
  }

  /**
   * 新建用户
   *
   * @param user 微信用户
   * @throws WxErrorException
   */
  @Override
  public void userCreate(WxCpUser user) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
    post(url, user.toJson());
  }

  /**
   * 更新用户
   *
   * @param user 微信用户
   * @throws WxErrorException
   */
  @Override
  public void userUpdate(WxCpUser user) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/update";
    post(url, user.toJson());
  }

  /**
   * 删除用户
   *
   * @param userid 用户ID
   * @throws WxErrorException
   */
  @Override
  public void userDelete(String userid) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?userid=" + userid;
    get(url, null);
  }

  /**
   * <pre>
   * 批量删除成员
   *
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E6.89.B9.E9.87.8F.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98
   * </pre>
   * @param userids 员工UserID列表。对应管理端的帐号
   * @throws WxErrorException
   */
  @Override
  public void userDelete(String[] userids) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete";
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    for (int i = 0; i < userids.length; i++) {
      jsonArray.add(new JsonPrimitive(userids[i]));
    }
    jsonObject.add("useridlist", jsonArray);
    post(url, jsonObject.toString());
  }

  /**
   * 获取用户
   *
   * @param userid 用户ID
   * @return 返回微信用户
   * @throws WxErrorException
   */
  @Override
  public WxCpUser userGet(String userid) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?userid=" + userid;
    String responseContent = get(url, null);
    return WxCpUser.fromJson(responseContent);
  }

  /**
   * <pre>
   * 获取部门成员(详情)
   *
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98.28.E8.AF.A6.E6.83.85.29
   * </pre>
   * @param departId    必填。部门id
   * @param fetchChild  非必填。1/0：是否递归获取子部门下面的成员
   * @param status      非必填。0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
   * @return 微信用户集合
   * @throws WxErrorException
   */
  @Override
  public List<WxCpUser> userList(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?department_id=" + departId;
    String params = "";
    if (fetchChild != null) {
      params += "&fetch_child=" + (fetchChild ? "1" : "0");
    }
    if (status != null) {
      params += "&status=" + status;
    } else {
      params += "&status=0";
    }

    String responseContent = get(url, params);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("userlist"),
            new TypeToken<List<WxCpUser>>() { }.getType()
        );
  }

  /**
   * <pre>
   * 获取部门成员
   *
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98
   * </pre>
   *
   * @param departId   必填。部门id
   * @param fetchChild 非必填。1/0：是否递归获取子部门下面的成员
   * @param status     非必填。0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
   * @return 返回微信用户集合
   * @throws WxErrorException
   */
  @Override
  public List<WxCpUser> departGetUsers(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?department_id=" + departId;
    String params = "";
    if (fetchChild != null) {
      params += "&fetch_child=" + (fetchChild ? "1" : "0");
    }
    if (status != null) {
      params += "&status=" + status;
    } else {
      params += "&status=0";
    }

    String responseContent = get(url, params);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("userlist"),
            new TypeToken<List<WxCpUser>>() { }.getType()
        );
  }

  /**
   * 创建标签
   *
   * @param tagName 标签名称
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  @Override
  public String tagCreate(String tagName) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/create";
    JsonObject o = new JsonObject();
    o.addProperty("tagname", tagName);
    String responseContent = post(url, o.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return tmpJsonElement.getAsJsonObject().get("tagid").getAsString();
  }

  /**
   * 更新标签
   *
   * @param tagId 标签ID
   * @param tagName  标签名称
   * @throws WxErrorException
   */
  @Override
  public void tagUpdate(String tagId, String tagName) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/update";
    JsonObject o = new JsonObject();
    o.addProperty("tagid", tagId);
    o.addProperty("tagname", tagName);
    post(url, o.toString());
  }

  /**
   * 删除标签
   *
   * @param tagId 标签ID
   * @throws WxErrorException
   */
  @Override
  public void tagDelete(String tagId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?tagid=" + tagId;
    get(url, null);
  }

  /**
   * 获得标签列表
   *
   * @return 标签列表
   * @throws WxErrorException
   */
  @Override
  public List<WxCpTag> tagGet() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/list";
    String responseContent = get(url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("taglist"),
            new TypeToken<List<WxCpTag>>() {
            }.getType()
        );
  }

  /**
   * 获取某标签下用户
   *
   * @param tagId 标签ID
   * @return 微信用户集合
   * @throws WxErrorException
   */
  @Override
  public List<WxCpUser> tagGetUsers(String tagId) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?tagid=" + tagId;
    String responseContent = get(url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxCpGsonBuilder.INSTANCE.create()
        .fromJson(
            tmpJsonElement.getAsJsonObject().get("userlist"),
            new TypeToken<List<WxCpUser>>() { }.getType()
        );
  }

  /**
   * 将用户添加到标签
   *
   * @param tagId  标签ID
   * @param userIds 用户ID集合
   * @param partyIds 部门ID表
   * @throws WxErrorException
   */
  @Override
  public void tagAddUsers(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    if (userIds != null) {
      JsonArray jsonArray = new JsonArray();
      for (String userId : userIds) {
        jsonArray.add(new JsonPrimitive(userId));
      }
      jsonObject.add("userlist", jsonArray);
    }
    if (partyIds != null) {
      JsonArray jsonArray = new JsonArray();
      for (String userId : partyIds) {
        jsonArray.add(new JsonPrimitive(userId));
      }
      jsonObject.add("partylist", jsonArray);
    }
    post(url, jsonObject.toString());
  }

  /**
   * 将用户从标签中移除
   *
   * @param tagId   标签ID
   * @param userIds 用户ID集
   * @throws WxErrorException
   */
  @Override
  public void tagRemoveUsers(String tagId, List<String> userIds) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("tagid", tagId);
    JsonArray jsonArray = new JsonArray();
    for (String userId : userIds) {
      jsonArray.add(new JsonPrimitive(userId));
    }
    jsonObject.add("userlist", jsonArray);
    post(url, jsonObject.toString());
  }

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://qydev.weixin.qq.com/wiki/index.php?title=企业获取code
   * </pre>
   * @param redirectUri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
   * @param state 重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值，长度不可超过128个字节
   * @return 返回String类型变量
   */
  @Override
  public String oauth2buildAuthorizationUrl(String redirectUri, String state) {
    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" ;
    url += "appid=" + wxCpConfigStorage.getCorpId();
    url += "&redirect_uri=" + URIUtil.encodeURIComponent(redirectUri);
    url += "&response_type=code";
    url += "&scope=snsapi_base";
    if (state != null) {
      url += "&state=" + state;
    }
    url += "#wechat_redirect";
    return url;
  }

  /**
   * <pre>
   * 用oauth2获取用户信息
   * http://qydev.weixin.qq.com/wiki/index.php?title=根据code获取成员信息
   * 因为企业号oauth2.0必须在应用设置里设置通过ICP备案的可信域名，所以无法测试，因此这个方法很可能是坏的。
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #oauth2getUserInfo(String, String)
   *
   * @param code 编码
   * @return [userid, deviceid]
   * @throws WxErrorException
   */
  @Override
  public String[] oauth2getUserInfo(String code) throws WxErrorException {
    return oauth2getUserInfo(wxCpConfigStorage.getAgentId(), code);
  }

  /**
   * <pre>
   * 用oauth2获取用户信息
   * http://qydev.weixin.qq.com/wiki/index.php?title=根据code获取成员信息
   * 因为企业号oauth2.0必须在应用设置里设置通过ICP备案的可信域名，所以无法测试，因此这个方法很可能是坏的。
   *
   * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
   * </pre>
   * @see #oauth2getUserInfo(String)
   *
   * @param agentId 企业号应用的id
   * @param code 编码
   * @return 返回String类型数组
   * @throws WxErrorException
   */
  @Override
  public String[] oauth2getUserInfo(String agentId, String code) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?"
        + "code=" + code
        + "&agendid=" + agentId;
    String responseText = get(url, null);
    JsonElement je = Streams.parse(new JsonReader(new StringReader(responseText)));
    JsonObject jo = je.getAsJsonObject();
    return new String[] {GsonHelper.getString(jo, "UserId"), GsonHelper.getString(jo, "DeviceId")};
  }

  /**
   * <pre>
   * 邀请成员关注
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8
   * </pre>
   * @param userId      用户的userid
   * @param inviteTips  推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
   * @return 返回int类型变量（1:微信邀请 2.邮件邀请）
   * @throws WxErrorException
   */
  @Override
  public int invite(String userId, String inviteTips) throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/invite/send";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userid", userId);
    if (StringUtils.isNotEmpty(inviteTips)) {
      jsonObject.addProperty("invite_tips", inviteTips);
    }
    String responseContent = post(url, jsonObject.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return tmpJsonElement.getAsJsonObject().get("type").getAsInt();
  }

  /**
   * <pre>
   * 获取微信服务器的ip段
   * http://qydev.weixin.qq.com/wiki/index.php?title=回调模式#.E8.8E.B7.E5.8F.96.E5.BE.AE.E4.BF.A1.E6.9C.8D.E5.8A.A1.E5.99.A8.E7.9A.84ip.E6.AE.B5
   * </pre>
   * @return 返回String类型数组，{ "ip_list": ["101.226.103.*", "101.226.62.*"] }
   * @throws WxErrorException
   */
  @Override
  public String[] getCallbackIp() throws WxErrorException {
    String url = "https://qyapi.weixin.qq.com/cgi-bin/getcallbackip";
    String responseContent = get(url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    JsonArray jsonArray = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
    String[] ips = new String[jsonArray.size()];
    for(int i = 0; i < jsonArray.size(); i++) {
      ips[i] = jsonArray.get(i).getAsString();
    }
    return ips;
  }

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   * @param url 链接
   * @param queryParam 查询参数
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String get(String url, String queryParam) throws WxErrorException {
    return execute(new SimpleGetRequestExecutor(), url, queryParam);
  }

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   * @param url 链接
   * @param postData postData
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String post(String url, String postData) throws WxErrorException {
    return execute(new SimplePostRequestExecutor(), url, postData);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   *
   * @param executor 执行器
   * @param uri 资源标志符
   * @param data 数据
   * @throws WxErrorException
   */
  public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    int retryTimes = 0;
    do {
      try {
        return executeInternal(executor, uri, data);
      } catch (WxErrorException e) {
        WxError error = e.getError();
        /**
         * -1 系统繁忙, 1000ms后重试
         */
        if (error.getErrorCode() == -1) {
          int sleepMillis = retrySleepMillis * (1 << retryTimes);
          try {
            log.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
          }
        } else {
          throw e;
        }
      }
    } while(++retryTimes < maxRetryTimes);

    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

  /**
   *
   * @param executor 执行器
   * @param uri 资源标识符
   * @param data 数据
   * @param <T> <T>
   * @param <E> <E>
   * @return execute(executor, uri, data)
   * @throws WxErrorException
   */
  protected synchronized <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    if (uri.indexOf("access_token=") != -1) {
      throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
    }
    String accessToken = getAccessToken(false);

    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

    try {
      return executor.execute(getHttpclient(), httpProxy, uriWithAccessToken, data);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
        // 强制设置wxCpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        wxCpConfigStorage.expireAccessToken();
        return execute(executor, uri, data);
      }
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return null;
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  protected CloseableHttpClient getHttpclient() {
    return httpClient;
  }

  /**
   * 注入 {@link WxCpConfigStorage} 的实现
   *
   * @param wxConfigProvider
   */
  public void setWxCpConfigStorage(WxCpConfigStorage wxConfigProvider) {
    this.wxCpConfigStorage = wxConfigProvider;

    String http_proxy_host = wxCpConfigStorage.getHttp_proxy_host();
    int http_proxy_port = wxCpConfigStorage.getHttp_proxy_port();
    String http_proxy_username = wxCpConfigStorage.getHttp_proxy_username();
    String http_proxy_password = wxCpConfigStorage.getHttp_proxy_password();

    if(StringUtils.isNotBlank(http_proxy_host)) {
      // 使用代理服务器
      if(StringUtils.isNotBlank(http_proxy_username)) {
        // 需要用户认证的代理服务器
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(http_proxy_host, http_proxy_port),
            new UsernamePasswordCredentials(http_proxy_username, http_proxy_password));
        httpClient = HttpClients
            .custom()
            .setDefaultCredentialsProvider(credsProvider)
            .build();
      } else {
        // 无需用户认证的代理服务器
        httpClient = HttpClients.createDefault();
      }
      httpProxy = new HttpHost(http_proxy_host, http_proxy_port);
    } else {
      httpClient = HttpClients.createDefault();
    }
  }

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   * @param retrySleepMillis 等待时间
   */
  @Override
  public void setRetrySleepMillis(int retrySleepMillis) {
    this.retrySleepMillis = retrySleepMillis;
  }


  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   * @param maxRetryTimes 最大重试次数
   */
  @Override
  public void setMaxRetryTimes(int maxRetryTimes) {
    this.maxRetryTimes = maxRetryTimes;
  }

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，则新建一个并返回。
   * @param id id可以为任意字符串，建议使用FromUserName作为id
   * @return 返回session
   */
  @Override
  public WxSession getSession(String id) {
    if (sessionManager == null) {
      return null;
    }
    return sessionManager.getSession(id);
  }

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，若create为true则新建一个，否则返回null。
   * @param id id可以为任意字符串，建议使用FromUserName作为id
   * @param create 新建
   * @return 返回session
   */
  @Override
  public WxSession getSession(String id, boolean create) {
    if (sessionManager == null) {
      return null;
    }
    return sessionManager.getSession(id, create);
  }


  /**
   * <pre>
   * 设置WxSessionManager，只有当需要使用个性化的WxSessionManager的时候才需要调用此方法，
   * WxCpService默认使用的是{@link StandardSessionManager}
   * </pre>
   * @param sessionManager 会话管理
   */
  @Override
  public void setSessionManager(WxSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }
  
  /**
   * 上传部门列表覆盖企业号上的部门信息
   * @param mediaId  媒体文件ID
   * @return 返回String类型变量
   * @throws WxErrorException
   */  
  @Override
  public String replaceParty(String mediaId) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty";
	  JsonObject jsonObject = new JsonObject();
	  jsonObject.addProperty("media_id", mediaId);
	  return post(url, jsonObject.toString());
  }
  
  /**
   * 上传用户列表覆盖企业号上的用户信息
   * @param mediaId 媒体文件ID
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  @Override
  public String replaceUser(String mediaId) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser";
	  JsonObject jsonObject = new JsonObject();
	  jsonObject.addProperty("media_id", mediaId);
	  return post(url, jsonObject.toString());
  }
  
  /**
   * 获取异步任务结果
   * @param joinId 异步任务ID
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  @Override
  public String getTaskResult(String joinId) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/batch/getresult?jobid="+joinId;
	  return get(url, null);
  }

  
  /**
   * 向客服人员发送消息，支持文本、图片、文件消息
   * @param message 发送消息实体
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  @Override
  public String sendKfMessage(WxCpKfMessage message) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/kf/send";
	  return post(url, message.toString());
  }
  
  /**
   * 获取客服列表
   * @param type 
   *        客服类型
			1. internal
			只获取内部客服列表
			2. external
			只获取外部客服列表
			不填时，同时返回内部、外部客服列表 
   * @return 返回客服列表
   * @throws WxErrorException
   */
  @Override
  public List<WxCpKfList> getKfList(String type) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/kf/list?type="+type;
	  String responseContent = get(url, null);
	  JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
	   return WxCpGsonBuilder.INSTANCE.create()
	        .fromJson(
	            tmpJsonElement.getAsJsonObject(),
	            new TypeToken<List<WxCpKfList>>() { }.getType()
	        ); 
  }
  
  /**
   * 获取设备及用户信息
   * 详情请见:http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E8%AE%BE%E5%A4%87%E5%8F%8A%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF
   * @param ticket 票据
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  @Override
  public String getShakeInfo(String ticket) throws WxErrorException {
	  String url = "https://qyapi.weixin.qq.com/cgi-bin/shakearound/getshakeinfo";
	  return post(url, ticket);
  }

  /**
   * 获取临时文件目录
   * @return 返回File类型文件
   */
  public File getTmpDirFile() {
    return tmpDirFile;
  }

  /**
   * 设置临时文件目录
   * @param tmpDirFile 临时文件目录
   */
  public void setTmpDirFile(File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }




}
