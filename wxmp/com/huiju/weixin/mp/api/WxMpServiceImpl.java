package com.huiju.weixin.mp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.bean.WxAccessToken;
import com.huiju.weixin.common.bean.WxJsapiSignature;
import com.huiju.weixin.common.bean.WxMenu;
import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.result.WxError;
import com.huiju.weixin.common.result.WxMediaUploadResult;
import com.huiju.weixin.common.session.StandardSessionManager;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.common.util.RandomUtils;
import com.huiju.weixin.common.util.StringUtils;
import com.huiju.weixin.common.util.crypto.SHA1;
import com.huiju.weixin.common.util.crypto.WxCryptUtil;
import com.huiju.weixin.common.util.fs.FileUtils;
import com.huiju.weixin.common.util.http.MediaDownloadRequestExecutor;
import com.huiju.weixin.common.util.http.MediaUploadRequestExecutor;
import com.huiju.weixin.common.util.http.RequestExecutor;
import com.huiju.weixin.common.util.http.SimpleGetRequestExecutor;
import com.huiju.weixin.common.util.http.SimplePostRequestExecutor;
import com.huiju.weixin.common.util.http.URIUtil;
import com.huiju.weixin.common.util.http.Utf8ResponseHandler;
import com.huiju.weixin.common.util.json.GsonHelper;
import com.huiju.weixin.common.util.json.WxGsonBuilder;
import com.huiju.weixin.common.util.xml.XStreamInitializer;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;
import com.huiju.weixin.mp.bean.WxMpGroup;
import com.huiju.weixin.mp.bean.WxMpMassGroupMessage;
import com.huiju.weixin.mp.bean.WxMpMassNews;
import com.huiju.weixin.mp.bean.WxMpMassOpenIdsMessage;
import com.huiju.weixin.mp.bean.WxMpMassVideo;
import com.huiju.weixin.mp.bean.WxMpMaterial;
import com.huiju.weixin.mp.bean.WxMpMaterialArticleUpdate;
import com.huiju.weixin.mp.bean.WxMpMaterialNews;
import com.huiju.weixin.mp.bean.WxMpSemanticQuery;
import com.huiju.weixin.mp.bean.WxMpTemplateMessage;
import com.huiju.weixin.mp.bean.result.WxMpMassSendResult;
import com.huiju.weixin.mp.bean.result.WxMpMassUploadResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialCountResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialUploadResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialVideoInfoResult;
import com.huiju.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import com.huiju.weixin.mp.bean.result.WxMpPayCallback;
import com.huiju.weixin.mp.bean.result.WxMpPayResult;
import com.huiju.weixin.mp.bean.result.WxMpPrepayIdResult;
import com.huiju.weixin.mp.bean.result.WxMpQrCodeTicket;
import com.huiju.weixin.mp.bean.result.WxMpSemanticQueryResult;
import com.huiju.weixin.mp.bean.result.WxMpUser;
import com.huiju.weixin.mp.bean.result.WxMpUserCumulate;
import com.huiju.weixin.mp.bean.result.WxMpUserList;
import com.huiju.weixin.mp.bean.result.WxMpUserSummary;
import com.huiju.weixin.mp.bean.result.WxRedpackResult;
import com.huiju.weixin.mp.util.http.MaterialDeleteRequestExecutor;
import com.huiju.weixin.mp.util.http.MaterialNewsInfoRequestExecutor;
import com.huiju.weixin.mp.util.http.MaterialUploadRequestExecutor;
import com.huiju.weixin.mp.util.http.MaterialVideoInfoRequestExecutor;
import com.huiju.weixin.mp.util.http.MaterialVoiceAndImageDownloadRequestExecutor;
import com.huiju.weixin.mp.util.http.QrCodeRequestExecutor;
import com.thoughtworks.xstream.XStream;

public class WxMpServiceImpl implements WxMpService {

  protected final Logger log = LoggerFactory.getLogger(WxMpServiceImpl.class);

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
  protected WxMpConfigStorage wxMpConfigStorage;
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
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   * @param timestamp  时间戳，企业指定
   * @param nonce  企业指定
   * @param signature  签名
   * @return 返回boolean类型变量
   */
  public boolean checkSignature(String timestamp, String nonce, String signature) {
    try {
      return SHA1.gen(wxMpConfigStorage.getToken(), timestamp, nonce).equals(signature);
    } catch (Exception e) {
      return false;
    }
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
   *
   * 另：本service的所有方法都会在access_token过期是调用此方法
   *
   * 程序员在非必要情况下尽量不要主动调用此方法

   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   * @param forceRefresh 强制刷新
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    if (forceRefresh) {
      wxMpConfigStorage.expireAccessToken();
    }
    if (wxMpConfigStorage.isAccessTokenExpired()) {
      synchronized (globalAccessTokenRefreshLock) {
        if (wxMpConfigStorage.isAccessTokenExpired()) {
          String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
              + "&appid=" + wxMpConfigStorage.getAppId()
              + "&secret=" + wxMpConfigStorage.getSecret();
          try {
            HttpGet httpGet = new HttpGet(url);
            if (httpProxy != null) {
              RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
              httpGet.setConfig(config);
            }
            CloseableHttpResponse response = getHttpclient().execute(httpGet);
            String resultContent = new BasicResponseHandler().handleResponse(response);
            WxError error = WxError.fromJson(resultContent);
            if (error.getErrorCode() != 0) {
              throw new WxErrorException(error);
            }
            WxAccessToken accessToken = WxAccessToken.fromJson(resultContent);
            wxMpConfigStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
          } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    return wxMpConfigStorage.getAccessToken();
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
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param forceRefresh 强制刷新
   * @return 返回String类型变量
   * @throws WxErrorException
   */
  public String getJsapiTicket(boolean forceRefresh) throws WxErrorException {
    if (forceRefresh) {
      wxMpConfigStorage.expireJsapiTicket();
    }
    if (wxMpConfigStorage.isJsapiTicketExpired()) {
      synchronized (globalJsapiTicketRefreshLock) {
        if (wxMpConfigStorage.isJsapiTicketExpired()) {
          String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
          String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
          JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
          JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
          String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
          int expiresInSeconds = tmpJsonObject.get("expires_in").getAsInt();
          wxMpConfigStorage.updateJsapiTicket(jsapiTicket, expiresInSeconds);
        }
      }
    }
    return wxMpConfigStorage.getJsapiTicket();
  }

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param url 链接
   * @return 返回JSAPI签名
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
      jsapiSignature.setAppid(wxMpConfigStorage.getAppId());
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
   * 发送客服消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
   * </pre>
   * @param message 发送的消息
   * @throws WxErrorException
   */
  public void customMessageSend(WxMpCustomMessage message) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    execute(new SimplePostRequestExecutor(), url, message.toJson());
  }

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   * 如果要创建个性化菜单，请设置matchrule属性
   * 详情请见:http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   * @param menu 菜单
   * @throws WxErrorException
   */
  public void menuCreate(WxMenu menu) throws WxErrorException {
    if (menu.getMatchRule() != null) {
      String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
      execute(new SimplePostRequestExecutor(), url, menu.toJson());
    } else {
      String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
      execute(new SimplePostRequestExecutor(), url, menu.toJson());
    }
  }

  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   * </pre>
   * @see #menuDelete(String)
   *
   * @throws WxErrorException
   */
  public void menuDelete() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    execute(new SimpleGetRequestExecutor(), url, null);
  }

  /**
   * <pre>
   * 删除个性化菜单接口
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   * @see #menuDelete()
   *
   * @param menuid 菜单ID
   * @throws WxErrorException
   */
  public void menuDelete(String menuid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional";
    execute(new SimpleGetRequestExecutor(), url, "menuid=" + menuid);
  }
  
  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   * </pre>
   * @return 返回菜单数据
   * @throws WxErrorException
   */
  public WxMenu menuGet() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/get";
    try {
      String resultContent = execute(new SimpleGetRequestExecutor(), url, null);
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
   * 测试个性化菜单匹配结果
   * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
   * </pre>
   * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
   * @return 返回结果 该接口将返回菜单配置。错误时的返回码请见接口返回码说明。
   * @throws WxErrorException
   */
  public WxMenu menuTryMatch(String userid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch";
    try {
      String resultContent = execute(new SimpleGetRequestExecutor(), url, "user_id=" + userid);
      return WxMenu.fromJson(resultContent);
    } catch (WxErrorException e) {
      // 46003 不存在的菜单数据     46002 不存在的菜单版本
      if (e.getError().getErrorCode() == 46003 || e.getError().getErrorCode() == 46002) {
        return null;
      }
      throw e;
    }
  }

  /**
   * <pre>
   * 上传多媒体文件
   *
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 1M，支持JPG格式
   *   语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
   *   视频（video）：10MB，支持MP4格式
   *   缩略图（thumb）：64KB，支持JPG格式
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   * @param mediaType         媒体类型, 请看{@link WxConsts}
   * @param fileType          文件类型，请看{@link WxConsts}
   * @param inputStream       输入流
   * @return 正确情况下将返回JSON数据包结果，错误返回errcode和errmsg
   * @throws WxErrorException
   */
  public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException {
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
    String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?type=" + mediaType;
    return execute(new MediaUploadRequestExecutor(), url, file);
  }

  /**
   * <pre>
   * 下载多媒体文件
   * 根据微信文档，视频文件下载不了，会返回null
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   * @param media_id 媒体文件上传后获取的唯一标识
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   */
  public File mediaDownload(String media_id) throws WxErrorException {
    String url = "http://file.api.weixin.qq.com/cgi-bin/media/get";
    return execute(new MediaDownloadRequestExecutor(wxMpConfigStorage.getTmpDirFile()), url, "media_id=" + media_id);
  }

  /**
   * <pre>
   * 上传非图文永久素材
   *
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式
   *   语音（voice）：语音大小不超过5M，长度不超过60秒，支持mp3/wma/wav/amr格式
   *   视频（video）：在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
   *   缩略图（thumb）：文档未说明
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
   * </pre>
   * @param mediaType         媒体类型, 请看{@link WxConsts}
   * @param material          上传的素材, 请看{@link WxMpMaterial}
   * @return 返回素材上传结果
   * @throws WxErrorException
   */
  public WxMpMaterialUploadResult materialFileUpload(String mediaType, WxMpMaterial material) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?type=" + mediaType;
    return execute(new MaterialUploadRequestExecutor(), url, material);
  }

  /**
   * <pre>
   * 上传永久图文素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
   * </pre>
   * @param news 上传的图文消息, 请看{@link WxMpMaterialNews}
   * @return 返回的即为新增的图文消息素材的media_id
   * @throws WxErrorException
   */
  public WxMpMaterialUploadResult materialNewsUpload(WxMpMaterialNews news) throws WxErrorException {
    if (news == null || news.isEmpty()) {
      throw new IllegalArgumentException("news is empty!");
    }
    String url = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    String responseContent = post(url, news.toJson());
    return WxMpMaterialUploadResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 下载声音或者图片永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   * @param media_id 永久素材的id
   * @return 返回声音或者图片永久素材，错误情况下的返回JSON数据包
   * @throws WxErrorException
   */
  public InputStream materialImageOrVoiceDownload(String media_id) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    return execute(new MaterialVoiceAndImageDownloadRequestExecutor(wxMpConfigStorage.getTmpDirFile()), url, media_id);
  }

  /**
   * <pre>
   * 获取视频永久素材的信息和下载地址
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   * @param media_id 永久素材的id
   * @return
   * <pre>
   *{
   *     "title":TITLE,
   *     "description":DESCRIPTION,
   *     "down_url":DOWN_URL,
   *}
   * </pre>
   * @throws WxErrorException
   */
  public WxMpMaterialVideoInfoResult materialVideoInfo(String media_id) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    return execute(new MaterialVideoInfoRequestExecutor(), url, media_id);
  }

  /**
   * <pre>
   * 获取图文消息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/b3546879f07623cb30df9ca0e420a5d0.html
   * </pre>
   * @param media_id 永久素材的id
   * @return 响应的直接为素材的内容
   * @throws WxErrorException
   */
  public WxMpMaterialNews materialNewsInfo(String media_id) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    return execute(new MaterialNewsInfoRequestExecutor(), url, media_id);
  }

  /**
   * <pre>
   * 更新图文永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/4/19a59cba020d506e767360ca1be29450.html
   * </pre>
   * @param wxMpMaterialArticleUpdate 用来更新图文素材的bean, 请看{@link WxMpMaterialArticleUpdate}
   * @return
   * <pre>
   *{
   *     "errcode": ERRCODE,
   *     "errmsg": ERRMSG
   *}
   * 正确时errcode的值应为0。
   * </pre>
   * @throws WxErrorException
   */
  public boolean materialNewsUpdate(WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/update_news";
    String responseText = post(url, wxMpMaterialArticleUpdate.toJson());
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return true;
    } else {
      throw new WxErrorException(wxError);
    }
  }

  /**
   * <pre>
   * 删除永久素材
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/5/e66f61c303db51a6c0f90f46b15af5f5.html
   * </pre>
   * @param media_id 永久素材的id
   * @return 返回
   * <pre>
   *{
   *  "errcode":ERRCODE,
   *  "errmsg":ERRMSG
   *}
   * 正常情况下调用成功时，errcode将为0
   * </pre>
   * @throws WxErrorException
   */
  public boolean materialDelete(String media_id) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/del_material";
    return execute(new MaterialDeleteRequestExecutor(), url, media_id);
  }

  /**
   * <pre>
   * 获取各类素材总数
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/16/8cc64f8c189674b421bee3ed403993b8.html
   * </pre>
   * @return 返回参数（voice_count，video_count，image_count，news_count）
   * @throws WxErrorException
   */
  public WxMpMaterialCountResult materialCount() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";
    String responseText = get(url, null);
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialCountResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  /**
   * <pre>
   * 分页获取图文素材列表
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
   * </pre>
   * @param offset      从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count       返回素材的数量，取值在1到20之间
   * @return  返回图文素材列表
   * @throws WxErrorException
   */
  public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(int offset, int count) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", WxConsts.MATERIAL_NEWS);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = post(url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialNewsBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  /**
   * <pre>
   * 分页获取其他媒体素材列表
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html
   * </pre>
   * @param type        媒体类型, 请看{@link WxConsts}
   * @param offset      从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count       返回素材的数量，取值在1到20之间
   * @return 返回图文素材列表
   * @throws WxErrorException
   */
  public WxMpMaterialFileBatchGetResult materialFileBatchGet(String type, int offset, int count) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";
    Map<String, Object> params = new HashMap<>();
    params.put("type", type);
    params.put("offset", offset);
    params.put("count", count);
    String responseText = post(url, WxGsonBuilder.create().toJson(params));
    WxError wxError = WxError.fromJson(responseText);
    if (wxError.getErrorCode() == 0) {
      return WxMpGsonBuilder.create().fromJson(responseText, WxMpMaterialFileBatchGetResult.class);
    } else {
      throw new WxErrorException(wxError);
    }
  }

  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param news 多人图文消息
   * @throws WxErrorException
   * @see #massGroupMessageSend(WxMpMassGroupMessage)
   * @see #massOpenIdsMessageSend(WxMpMassOpenIdsMessage)
   */
  public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
    String responseContent = execute(new SimplePostRequestExecutor(), url, news.toJson());
    return WxMpMassUploadResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param video 多人视频消息
   * @throws WxErrorException
   * @see #massGroupMessageSend(WxMpMassGroupMessage)
   * @see #massOpenIdsMessageSend(WxMpMassOpenIdsMessage)
   */
  public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
    String url = "http://file.api.weixin.qq.com/cgi-bin/media/uploadvideo";
    String responseContent = execute(new SimplePostRequestExecutor(), url, video.toJson());
    return WxMpMassUploadResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 分组群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param message 群发的消息
   * @throws WxErrorException
   * @return 正确返回WxMpMassSendResult类对象
   */
  public WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
    String responseContent = execute(new SimplePostRequestExecutor(), url, message.toJson());
    return WxMpMassSendResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 按openId列表群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param message 群发的消息
   * @return 正确返回WxMpMassSendResult类对象
   * @throws WxErrorException
   */
  public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
    String responseContent = execute(new SimplePostRequestExecutor(), url, message.toJson());
    return WxMpMassSendResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 分组管理接口 - 创建分组
   * 最多支持创建500个分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @param name 分组名字（30个字符以内）
   * @throws WxErrorException
   */
  public WxMpGroup groupCreate(String name) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/groups/create";
    JsonObject json = new JsonObject();
    JsonObject groupJson = new JsonObject();
    json.add("group", groupJson);
    groupJson.addProperty("name", name);

    String responseContent = execute(
        new SimplePostRequestExecutor(),
        url,
        json.toString());
    return WxMpGroup.fromJson(responseContent);
  }

  /**
   * <pre>
   * 分组管理接口 - 查询所有分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @return 返回分组列表
   * @throws WxErrorException
   */
  public List<WxMpGroup> groupGet() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/groups/get";
    String responseContent = execute(new SimpleGetRequestExecutor(), url, null);
    /*
     * 操蛋的微信API，创建时返回的是 { group : { id : ..., name : ...} }
     * 查询时返回的是 { groups : [ { id : ..., name : ..., count : ... }, ... ] }
     */
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("groups"),
        new TypeToken<List<WxMpGroup>>() {
        }.getType());
  }

  /**
   * <pre>
   * 分组管理接口 - 查询用户所在分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @param openid 微信用户的openid
   * @return 返回用户所属的groupid
   * @throws WxErrorException
   */
  public long userGetGroup(String openid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/groups/getid";
    JsonObject o = new JsonObject();
    o.addProperty("openid", openid);
    String responseContent = execute(new SimplePostRequestExecutor(), url, o.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return GsonHelper.getAsLong(tmpJsonElement.getAsJsonObject().get("groupid"));
  }

  /**
   * <pre>
   * 分组管理接口 - 修改分组名
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   *
   * 如果id为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   * @param group 要更新的group，group的id,name必须设置
   * @throws WxErrorException
   */
  public void groupUpdate(WxMpGroup group) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/groups/update";
    execute(new SimplePostRequestExecutor(), url, group.toJson());
  }

  /**
   * <pre>
   * 分组管理接口 - 移动用户分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   *
   * 如果to_groupid为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   * @param openid      用户openid
   * @param to_groupid  移动到的分组id
   * @throws WxErrorException
   */
  public void userUpdateGroup(String openid, long to_groupid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
    JsonObject json = new JsonObject();
    json.addProperty("openid", openid);
    json.addProperty("to_groupid", to_groupid);
    execute(new SimplePostRequestExecutor(), url, json.toString());
  }

  /**
   * <pre>
   * 设置用户备注名接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=设置用户备注名接口
   * </pre>
   * @param openid    用户openid
   * @param remark    备注名
   * @throws WxErrorException
   */
  public void userUpdateRemark(String openid, String remark) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark";
    JsonObject json = new JsonObject();
    json.addProperty("openid", openid);
    json.addProperty("remark", remark);
    execute(new SimplePostRequestExecutor(), url, json.toString());
  }

  /**
   * <pre>
   * 获取用户基本信息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
   * </pre>
   * @param openid  用户openid
   * @param lang    语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
   * @return 返回用户基本信息
   * @throws WxErrorException
   */
  public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/user/info";
    lang = lang == null ? "zh_CN" : lang;
    String responseContent = execute(new SimpleGetRequestExecutor(), url, "openid=" + openid + "&lang=" + lang);
    return WxMpUser.fromJson(responseContent);
  }

  /**
   * <pre>
   * 获取关注者列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
   * </pre>
   * @param next_openid  可选，第一个拉取的OPENID，null为从头开始拉取
   * @return 返回关注者列表
   * @throws WxErrorException
   */
  public WxMpUserList userList(String next_openid) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/user/get";
    String responseContent = execute(new SimpleGetRequestExecutor(), url, next_openid == null ? null : "next_openid=" + next_openid);
    return WxMpUserList.fromJson(responseContent);
  }

  /**
   * <pre>
   * 换取临时二维码ticket
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param scene_id 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @param expire_seconds 	该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
   * @return 返回WxMpQrCodeTicket类对象
   * @throws WxErrorException
   */
  public WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_SCENE");
    if (expire_seconds != null) {
      json.addProperty("expire_seconds", expire_seconds);
    }
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_id", scene_id);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  /**
   * <pre>
   * 换取永久二维码ticket
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param scene_id    参数。永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return 返回WxMpQrCodeTicket类对象
   * @throws WxErrorException
   */
  public WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_LIMIT_SCENE");
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_id", scene_id);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  /**
   * <pre>
   * 换取永久字符串二维码ticket
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   *
   * @param scene_str 参数。字符串类型长度现在为1到64
   * @return 返回WxMpQrCodeTicket类对象
   * @throws WxErrorException
   */
  public WxMpQrCodeTicket qrCodeCreateLastTicket(String scene_str) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    JsonObject json = new JsonObject();
    json.addProperty("action_name", "QR_LIMIT_STR_SCENE");
    JsonObject actionInfo = new JsonObject();
    JsonObject scene = new JsonObject();
    scene.addProperty("scene_str", scene_str);
    actionInfo.add("scene", scene);
    json.add("action_info", actionInfo);
    String responseContent = execute(new SimplePostRequestExecutor(), url, json.toString());
    return WxMpQrCodeTicket.fromJson(responseContent);
  }

  /**
   * <pre>
   * 换取二维码图片文件，jpg格式
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param ticket    二维码ticket
   * @return http 正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。
   * @throws WxErrorException
   */
  public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
    String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
    return execute(new QrCodeRequestExecutor(), url, ticket);
  }

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   * @param long_url 需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
   * @return 正确返回示例
   * <pre>
   *{
   *     "errcode":0,
   *     "errmsg":"ok",
   *     "short_url":"http:\/\/w.url.cn\/s\/AvCo6Ih"
   *}
   * </pre>
   * @throws WxErrorException
   */
  public String shortUrl(String long_url) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/shorturl";
    JsonObject o = new JsonObject();
    o.addProperty("action", "long2short");
    o.addProperty("long_url", long_url);
    String responseContent = execute(new SimplePostRequestExecutor(), url, o.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
  }

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=模板消息接口
   * </pre>
   * @param templateMessage 模板消息
   * @throws WxErrorException
   * @return msgid
   */
  public String templateSend(WxMpTemplateMessage templateMessage) throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    String responseContent = execute(new SimplePostRequestExecutor(), url, templateMessage.toJson());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    final JsonObject jsonObject = tmpJsonElement.getAsJsonObject();
    if (jsonObject.get("errcode").getAsInt() == 0)
      return jsonObject.get("msgid").getAsString();
    throw new WxErrorException(WxError.fromJson(responseContent));
  }

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   * @param semanticQuery 语义理解
   * @return 返回语义查询的结果
   * @throws WxErrorException
   */
  public WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException {
    String url = "https://api.weixin.qq.com/semantic/semproxy/search";
    String responseContent = execute(new SimplePostRequestExecutor(), url, semanticQuery.toJson());
    return WxMpSemanticQueryResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   * @param scope 授权作用域
   * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
   * @return url
   */
  @Override
  public String oauth2buildAuthorizationUrl(String scope, String state) {
    return this.oauth2buildAuthorizationUrl(wxMpConfigStorage.getOauth2redirectUri(), scope, state);
  }

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   * @param redirectURI
   *   用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @param scope 授权作用域
   * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
   * @return url
   */
  @Override
  public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    url += "appid=" + wxMpConfigStorage.getAppId();
    url += "&redirect_uri=" + URIUtil.encodeURIComponent(redirectURI);
    url += "&response_type=code";
    url += "&scope=" + scope;
    if (state != null) {
      url += "&state=" + state;
    }
    url += "#wechat_redirect";
    return url;
  }

  /**
   * <pre>
   * 用code换取oauth2的access token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   * @param code 填写第一步获取的code参数
   * @return 返回网页授权access_token
   */
  @Override
  public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException {
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
    url += "appid=" + wxMpConfigStorage.getAppId();
    url += "&secret=" + wxMpConfigStorage.getSecret();
    url += "&code=" + code;
    url += "&grant_type=authorization_code";

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
      return WxMpOAuth2AccessToken.fromJson(responseText);
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 刷新oauth2的access token
   * </pre>
   * @param refreshToken 填写通过access_token获取到的refresh_token参数
   * @return 返回WxMpOAuth2AccessToken类对象
   */
  @Override
  public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException {
    String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";
    url += "appid=" + wxMpConfigStorage.getAppId();
    url += "&grant_type=refresh_token";
    url += "&refresh_token=" + refreshToken;

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
      return WxMpOAuth2AccessToken.fromJson(responseText);
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
   * </pre>
   * @param oAuth2AccessToken 网页授权接口调用凭证
   * @param lang zh_CN, zh_TW, en
   * @return 返回用户信息
   */
  @Override
  public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException {
    String url = "https://api.weixin.qq.com/sns/userinfo?";
    url += "access_token=" + oAuth2AccessToken.getAccessToken();
    url += "&openid=" + oAuth2AccessToken.getOpenId();
    if (lang == null) {
      url += "&lang=zh_CN";
    } else {
      url += "&lang=" + lang;
    }

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(getHttpclient(), httpProxy, url, null);
      return WxMpUser.fromJson(responseText);
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 验证oauth2的access token是否有效
   * </pre>
   * @param oAuth2AccessToken 网页授权接口调用凭证
   * @return 返回是否有效（true or false）
   */
  @Override
  public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken) {
    String url = "https://api.weixin.qq.com/sns/auth?";
    url += "access_token=" + oAuth2AccessToken.getAccessToken();
    url += "&openid=" + oAuth2AccessToken.getOpenId();

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      executor.execute(getHttpclient(), httpProxy, url, null);
    } catch (ClientProtocolException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (WxErrorException e) {
      return false;
    }
    return true;
  }

  /**
   * <pre>
   * 获取微信服务器IP地址
   * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
   * </pre>
   * @return 返回ip数组
   * @throws WxErrorException
   */
  @Override
  public String[] getCallbackIP() throws WxErrorException {
    String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
    String responseContent = get(url, null);
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
    String[] ipArray = new String[ipList.size()];
    for (int i = 0; i < ipList.size(); i++) {
      ipArray[i] = ipList.get(i).getAsString();
    }
    return ipArray;
  }


  /**
   * <pre>
   * 获取用户增减数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   * @return 返回获取用户增减数据
   * @throws WxErrorException
   */
  @Override
  public List<WxMpUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
    String url = "https://api.weixin.qq.com/datacube/getusersummary";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = post(url, param.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
        new TypeToken<List<WxMpUserSummary>>() {
        }.getType());
  }

  /**
   * <pre>
   * 获取累计用户数据
   * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
   * </pre>
   * @param beginDate 最大时间跨度7天
   * @param endDate   endDate不能早于begingDate
   * @return 返回累计用户数据
   * @throws WxErrorException
   */
  @Override
  public List<WxMpUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
    String url = "https://api.weixin.qq.com/datacube/getusercumulate";
    JsonObject param = new JsonObject();
    param.addProperty("begin_date", SIMPLE_DATE_FORMAT.format(beginDate));
    param.addProperty("end_date", SIMPLE_DATE_FORMAT.format(endDate));
    String responseContent = post(url, param.toString());
    JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
    return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("list"),
        new TypeToken<List<WxMpUserCumulate>>() {
        }.getType());
  }

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   * @param url
   * @param queryParam
   * @return
   * @throws WxErrorException
   */
  public String get(String url, String queryParam) throws WxErrorException {
    return execute(new SimpleGetRequestExecutor(), url, queryParam);
  }

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   * @param url
   * @param postData
   * @return
   * @throws WxErrorException
   */
  public String post(String url, String postData) throws WxErrorException {
    return execute(new SimplePostRequestExecutor(), url, postData);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   *
   * @param executor
   * @param uri
   * @param data
   * @return
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
    } while (++retryTimes < maxRetryTimes);

    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

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
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        wxMpConfigStorage.expireAccessToken();
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
   * 注入 {@link WxMpConfigStorage} 的实现
   * @param wxConfigProvider 微信客户端配置存储
   */
  public void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider) {
    this.wxMpConfigStorage = wxConfigProvider;

    String http_proxy_host = wxMpConfigStorage.getHttp_proxy_host();
    int http_proxy_port = wxMpConfigStorage.getHttp_proxy_port();
    String http_proxy_username = wxMpConfigStorage.getHttp_proxy_username();
    String http_proxy_password = wxMpConfigStorage.getHttp_proxy_password();

    final HttpClientBuilder builder = HttpClients.custom();
    if (StringUtils.isNotBlank(http_proxy_host)) {
      // 使用代理服务器
      if (StringUtils.isNotBlank(http_proxy_username)) {
        // 需要用户认证的代理服务器
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
            new AuthScope(http_proxy_host, http_proxy_port),
            new UsernamePasswordCredentials(http_proxy_username, http_proxy_password));
        builder
            .setDefaultCredentialsProvider(credsProvider);
      } else {
        // 无需用户认证的代理服务器
      }
      httpProxy = new HttpHost(http_proxy_host, http_proxy_port);
    }
    if (wxConfigProvider.getSSLContext() != null){
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
          wxConfigProvider.getSSLContext(),
          new String[] { "TLSv1" },
          null,
          SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
      builder.setSSLSocketFactory(sslsf);
    }
    httpClient = builder.build();
  }

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   * @param retrySleepMillis 重新发起请求等待时间
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
   * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
   * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
   * @param openId 支付人openId
   * @param outTradeNo 商户端对应订单号
   * @param amt 金额(单位元)
   * @param body 商品描述
   * @param tradeType 交易类型 JSAPI，NATIVE，APP，WAP
   * @param ip 发起支付的客户端IP
   * @return 返回——返回状态码和返回信息
   * @deprecated Use WxMpService.getPrepayId(Map<String, String>) instead
   */
  @Override
  public WxMpPrepayIdResult getPrepayId(String openId, String outTradeNo, double amt, String body, String tradeType, String ip, String callbackUrl) {
    Map<String, String> packageParams = new HashMap<String, String>();
    packageParams.put("appid", wxMpConfigStorage.getAppId());
    packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
    packageParams.put("body", body);
    packageParams.put("out_trade_no", outTradeNo);
    packageParams.put("total_fee", (int) (amt * 100) + "");
    packageParams.put("spbill_create_ip", ip);
    packageParams.put("notify_url", callbackUrl);
    packageParams.put("trade_type", tradeType);
    packageParams.put("openid", openId);

    return getPrepayId(packageParams);
  }

  /**
   * 统一下单(详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)
   * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
   * 
   * @param parameters All required/optional parameters for weixin payment
   * @return 返回——返回状态码和返回信息
   * @throws IllegalArgumentException
   */
  public WxMpPrepayIdResult getPrepayId(final Map<String, String> parameters) {
    String nonce_str = System.currentTimeMillis() + "";

    final SortedMap<String, String> packageParams = new TreeMap<String, String>(parameters);
    packageParams.put("appid", wxMpConfigStorage.getAppId());
    packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
    packageParams.put("nonce_str", nonce_str);
    checkParameters(packageParams);

    String sign = WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey());
    packageParams.put("sign", sign);

    StringBuilder request = new StringBuilder("<xml>");
    for (Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
    }
    request.append("</xml>");

    HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try {
      CloseableHttpResponse response = getHttpclient().execute(httpPost);
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPrepayIdResult.class);
      WxMpPrepayIdResult wxMpPrepayIdResult = (WxMpPrepayIdResult) xstream.fromXML(responseContent);
      return wxMpPrepayIdResult;
    } catch (IOException e) {
      throw new RuntimeException("Failed to get prepay id due to IO exception.", e);
    }
  }

  final String[] REQUIRED_ORDER_PARAMETERS = new String[] { "appid", "mch_id", "body", "out_trade_no", "total_fee", "spbill_create_ip", "notify_url",
      "trade_type", };

  private void checkParameters(Map<String, String> parameters) {
    for (String para : REQUIRED_ORDER_PARAMETERS) {
      if (!parameters.containsKey(para))
        throw new IllegalArgumentException("Reqiured argument '" + para + "' is missing.");
    }
    if ("JSAPI".equals(parameters.get("trade_type")) && !parameters.containsKey("openid"))
      throw new IllegalArgumentException("Reqiured argument 'openid' is missing when trade_type is 'JSAPI'.");
    if ("NATIVE".equals(parameters.get("trade_type")) && !parameters.containsKey("product_id"))
      throw new IllegalArgumentException("Reqiured argument 'product_id' is missing when trade_type is 'NATIVE'.");
  }
	
/**
 * 该接口调用“统一下单”接口，并拼装JSSDK发起支付请求需要的参数
 * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82
 * @param openId 支付人openId
 * @param outTradeNo 商户端对应订单号
 * @param amt 金额(单位元)
 * @param body 商品描述
 * @param tradeType 交易类型 JSAPI，NATIVE，APP，WAP
 * @param ip 发起支付的客户端IP
 * @param callbackUrl 通知地址
 * @return 返回支付信息
 * @deprecated Use WxMpService.getJSSDKPayInfo(Map<String, String>) instead
 */
  @Override
  public Map<String, String> getJSSDKPayInfo(String openId, String outTradeNo, double amt, String body, String tradeType, String ip, String callbackUrl) {
    Map<String, String> packageParams = new HashMap<String, String>();
    packageParams.put("appid", wxMpConfigStorage.getAppId());
    packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
    packageParams.put("body", body);
    packageParams.put("out_trade_no", outTradeNo);
    packageParams.put("total_fee", (int) (amt * 100) + "");
    packageParams.put("spbill_create_ip", ip);
    packageParams.put("notify_url", callbackUrl);
    packageParams.put("trade_type", tradeType);
    packageParams.put("openid", openId);

    return getJSSDKPayInfo(packageParams);
  }

  /**
   * 该接口调用“统一下单”接口，并拼装JSSDK发起支付请求需要的参数
   * 详见http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82
   * @param parameters
   *            the required or optional parameters
   * @return 返回支付信息
   */
  @Override
  public Map<String, String> getJSSDKPayInfo(Map<String, String> parameters) {
    WxMpPrepayIdResult wxMpPrepayIdResult = getPrepayId(parameters);
    String prepayId = wxMpPrepayIdResult.getPrepay_id();
    if (prepayId == null || prepayId.equals("")) {
      throw new RuntimeException(String.format("Failed to get prepay id due to error code '%s'(%s).", wxMpPrepayIdResult.getErr_code(), wxMpPrepayIdResult.getErr_code_des()));
    }

    Map<String, String> payInfo = new HashMap<String, String>();
    payInfo.put("appId", wxMpConfigStorage.getAppId());
    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    payInfo.put("nonceStr", System.currentTimeMillis() + "");
    payInfo.put("package", "prepay_id=" + prepayId);
    payInfo.put("signType", "MD5");

    String finalSign = WxCryptUtil.createSign(payInfo, wxMpConfigStorage.getPartnerKey());
    payInfo.put("paySign", finalSign);
    return payInfo;
  }

  /**
   * 该接口提供所有微信支付订单的查询,当支付通知处理异常戒丢失的情冴,商户可以通过该接口查询订单支付状态。
   * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
   * @param transactionId 微信订单号
   * @param outTradeNo 商户订单号
   */
  @Override
  public WxMpPayResult getJSSDKPayResult(String transactionId, String outTradeNo) {
    String nonce_str = System.currentTimeMillis() + "";

    SortedMap<String, String> packageParams = new TreeMap<String, String>();
    packageParams.put("appid", wxMpConfigStorage.getAppId());
    packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
    if (transactionId != null && !"".equals(transactionId.trim()))
      packageParams.put("transaction_id", transactionId);
    else if (outTradeNo != null && !"".equals(outTradeNo.trim()))
      packageParams.put("out_trade_no", outTradeNo);
    else
      throw new IllegalArgumentException("Either 'transactionId' or 'outTradeNo' must be given.");
    packageParams.put("nonce_str", nonce_str);
    packageParams.put("sign", WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey()));

    StringBuilder request = new StringBuilder("<xml>");
    for (Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
    }
    request.append("</xml>");

    HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try {
      CloseableHttpResponse response = httpClient.execute(httpPost);
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPayResult.class);
      WxMpPayResult wxMpPayResult = (WxMpPayResult) xstream.fromXML(responseContent);
      return wxMpPayResult;
    } catch (IOException e) {
      throw new RuntimeException("Failed to query order due to IO exception.", e);
    }
  }

  /**
   * 读取支付结果通知
   * 详见http://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
   * @param xmlData xml格式数据
   * @return 返回支付结果
   */
  @Override
  public WxMpPayCallback getJSSDKCallbackData(String xmlData) {
    try {
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxMpPayCallback.class);
      WxMpPayCallback wxMpCallback = (WxMpPayCallback) xstream.fromXML(xmlData);
      return wxMpCallback;
    } catch (Exception e){
      e.printStackTrace();
    }
    return new WxMpPayCallback();
  }
  
  /**
   * <pre>
   * 计算Map键值对是否和签名相符,
   * 按照字段名的 ASCII 码从小到大排序(字典序)后,使用 URL 键值对的 格式(即 key1=value1&key2=value2...)拼接成字符串
   * </pre>
   * @param kvm Map键值
   * @param signature 签名
   * @return 返回Map键值对是否和签名相符
   */
  @Override
  public boolean checkJSSDKCallbackDataSignature(Map<String, String> kvm, String signature) {
	  return signature.equals(WxCryptUtil.createSign(kvm, wxMpConfigStorage.getPartnerKey()));
  }
  
 /**
  * 发送微信红包给个人用户
  * @param parameters 参数
  * @return 返回向微信用户个人发现金红包返回结果
  * @throws WxErrorException
  */
  @Override
  public WxRedpackResult sendRedpack(Map<String, String> parameters) throws WxErrorException {
    String nonce_str = System.currentTimeMillis() + "";

    SortedMap<String, String> packageParams = new TreeMap<String, String>(parameters);
    packageParams.put("wxappid", wxMpConfigStorage.getAppId());
    packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
    packageParams.put("nonce_str", nonce_str);

    String sign = WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey());
    packageParams.put("sign", sign);
    
    StringBuilder request = new StringBuilder("<xml>");
    for (Entry<String, String> para : packageParams.entrySet()) {
      request.append(String.format("<%s>%s</%s>", para.getKey(), para.getValue(), para.getKey()));
    }
    request.append("</xml>");
    
    HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
    if (httpProxy != null) {
      RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
      httpPost.setConfig(config);
    }

    StringEntity entity = new StringEntity(request.toString(), Consts.UTF_8);
    httpPost.setEntity(entity);
    try {
      CloseableHttpResponse response = getHttpclient().execute(httpPost);
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      XStream xstream = XStreamInitializer.getInstance();
      xstream.processAnnotations(WxRedpackResult.class);
      WxRedpackResult wxMpRedpackResult = (WxRedpackResult) xstream.fromXML(responseContent);
      return wxMpRedpackResult;
    } catch (IOException e) {
      log.error(MessageFormatter.format("The exception was happened when sending redpack '{}'.", request.toString()).getMessage(), e);
      WxError error = new WxError();
      error.setErrorCode(-1);
      throw new WxErrorException(error);
    }
  }
}
