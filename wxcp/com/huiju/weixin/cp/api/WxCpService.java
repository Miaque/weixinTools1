package com.huiju.weixin.cp.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.bean.WxJsapiSignature;
import com.huiju.weixin.common.bean.WxMenu;
import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.result.WxMediaUploadResult;
import com.huiju.weixin.common.session.StandardSessionManager;
import com.huiju.weixin.common.session.WxSession;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.common.util.http.MediaUploadRequestExecutor;
import com.huiju.weixin.common.util.http.RequestExecutor;
import com.huiju.weixin.cp.bean.WxCpDepart;
import com.huiju.weixin.cp.bean.WxCpKfList;
import com.huiju.weixin.cp.bean.WxCpKfMessage;
import com.huiju.weixin.cp.bean.WxCpMessage;
import com.huiju.weixin.cp.bean.WxCpTag;
import com.huiju.weixin.cp.bean.WxCpUser;

/**
 * 微信API的Service
 */
public interface WxCpService {

  /**
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   *
   * @param msgSignature  签名， 特定算法生成
   * @param timestamp    时间戳，企业指定
   * @param nonce        企业指定
   * @param data         微信传输过来的数据，有可能是echoStr，有可能是xml消息
   * @return boolean  
   */
  boolean checkSignature(String msgSignature, String timestamp, String nonce, String data);

  /**
   * <pre>
   *   用在二次验证的时候
   *   企业在员工验证成功后，调用本方法告诉企业号平台该员工关注成功。
   * </pre>
   *
   * @param userId 员工ID
   */
  void userAuthenticated(String userId) throws WxErrorException;

  /**
   * 获取access_token, 不强制刷新access_token
   * @see #getAccessToken(boolean)
   * @return
   * @throws WxErrorException
   */
  String getAccessToken() throws WxErrorException;

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   * 另：本service的所有方法都会在access_token过期是调用此方法
   * 程序员在非必要情况下尽量不要主动调用此方法
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   * @param forceRefresh 强制刷新
   * @return
   * @throws com.neusoft.weixin.common.exception.WxErrorException
   */
  String getAccessToken(boolean forceRefresh) throws WxErrorException;

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   * @see #getJsapiTicket(boolean)
   * @return
   * @throws WxErrorException
   */
  public String getJsapiTicket() throws WxErrorException;

  /**
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://qydev.weixin.qq.com/wiki/index.php?title=微信JS接口#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param forceRefresh 强制刷新
   * @return
   * @throws WxErrorException
   */
  public String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://qydev.weixin.qq.com/wiki/index.php?title=微信JS接口#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param url       url
   * @return
   */
  public WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;

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
  WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream)
      throws WxErrorException, IOException;

  /**
   * @param mediaType   媒体类型
   * @param file        文件
   * @throws WxErrorException
   * @see #mediaUpload(String, String, InputStream)
   */
  WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException;

  /**
   * <pre>
   * 下载多媒体文件
   * 根据微信文档，视频文件下载不了，会返回null
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   *
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   * @params media_id
   */
  File mediaDownload(String media_id) throws WxErrorException;

  /**
   * <pre>
   * 发送消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送消息
   * </pre>
   *
   * @param message 发送的消息
   * @throws WxErrorException
   */
  void messageSend(WxCpMessage message) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #menuCreate(String, WxMenu)
   *
   * @param menu  菜单
   * @throws WxErrorException
   */
  void menuCreate(WxMenu menu) throws WxErrorException;

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
   * @param menu   菜单
   * @throws WxErrorException
   */
  void menuCreate(String agentId, WxMenu menu) throws WxErrorException;

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
  void menuDelete() throws WxErrorException;

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
  void menuDelete(String agentId) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   *
   * 注意: 这个方法使用WxCpConfigStorage里的agentId
   * </pre>
   * @see #menuGet(String)
   *
   * @return
   * @throws WxErrorException
   */
  WxMenu menuGet() throws WxErrorException;

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
   * @return
   * @throws WxErrorException
   */
  WxMenu menuGet(String agentId) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 创建部门
   * 最多支持创建500个部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   *
   * @param depart 部门
   * @return 部门id
   * @throws WxErrorException
   */
  Integer departCreate(WxCpDepart depart) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 查询所有部门
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=部门管理接口
   * </pre>
   *
   * @return 部门集合
   * @throws WxErrorException
   */
  List<WxCpDepart> departGet() throws WxErrorException;

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
  void departUpdate(WxCpDepart group) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 删除部门
   * </pre>
   *
   * @param departId 部门ID
   * @throws WxErrorException
   */
  void departDelete(Integer departId) throws WxErrorException;

  /**
   * <pre>
   * 获取部门成员(详情)
   *
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98.28.E8.AF.A6.E6.83.85.29
   * </pre>
   * @param departId    必填。部门id
   * @param fetchChild  非必填。1/0：是否递归获取子部门下面的成员
   * @param status      非必填。0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
   * @return
   * @throws WxErrorException
   */
  List<WxCpUser> userList(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException;

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
   * @return
   * @throws WxErrorException
   */
  List<WxCpUser> departGetUsers(Integer departId, Boolean fetchChild, Integer status) throws WxErrorException;

  /**
   * 新建用户
   *
   * @param user  微信用户
   * @throws WxErrorException
   */
  void userCreate(WxCpUser user) throws WxErrorException;

  /**
   * 更新用户
   *
   * @param user  微信用户
   * @throws WxErrorException
   */
  void userUpdate(WxCpUser user) throws WxErrorException;

  /**
   * 删除用户
   *
   * @param userid  用户ID
   * @throws WxErrorException
   */
  void userDelete(String userid) throws WxErrorException;

  /**
   * <pre>
   * 批量删除成员
   *
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E6.89.B9.E9.87.8F.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98
   * </pre>
   * @param userids   员工UserID列表。对应管理端的帐号
   * @throws WxErrorException
   */
  void userDelete(String[] userids) throws WxErrorException;

  /**
   * 获取用户
   *
   * @param userid    用户ID
   * @return WxCpUser   用户信息
   * @throws WxErrorException
   */
  WxCpUser userGet(String userid) throws WxErrorException;

  /**
   * 创建标签
   *
   * @param tagName  标签名称
   * @return
   */
  String tagCreate(String tagName) throws WxErrorException;

  /**
   * 更新标签
   *
   * @param tagId   标签ID
   * @param tagName 标签名称
   */
  void tagUpdate(String tagId, String tagName) throws WxErrorException;

  /**
   * 删除标签
   *
   * @param tagId  标签ID
   */
  void tagDelete(String tagId) throws WxErrorException;

  /**
   * 获得标签列表
   *
   * @return 标签列表
   */
  List<WxCpTag> tagGet() throws WxErrorException;

  /**
   * 获取标签成员
   *
   * @param tagId  标签ID
   * @return  对应标签成员列表
   */
  List<WxCpUser> tagGetUsers(String tagId) throws WxErrorException;

  /**
   * 增加标签成员
   *
   * @param tagId  标签ID
   * @param userIds   添加的成员集合
   */
  void tagAddUsers(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException;

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://qydev.weixin.qq.com/wiki/index.php?title=企业获取code
   * </pre>
   * @param redirectUri   授权后重定向的回调链接地址，请使用urlencode对链接进行处理
   * @param state  重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值，长度不可超过128个字节
   * @return url
   */
  String oauth2buildAuthorizationUrl(String redirectUri, String state);

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
   * @param code
   * @return [userid, deviceid]
   */
  String[] oauth2getUserInfo(String code) throws WxErrorException;

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
   * @param code
   * @return [userid, deviceid]
   */
  String[] oauth2getUserInfo(String agentId, String code) throws WxErrorException;


  /**
   * 移除标签成员
   *
   * @param tagId   标签ID
   * @param userIds 用户ID集
   */
  void tagRemoveUsers(String tagId, List<String> userIds) throws WxErrorException;

  /**
   * <pre>
   * 邀请成员关注
   * http://qydev.weixin.qq.com/wiki/index.php?title=管理成员#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8
   * </pre>
   * @param userId      用户的userid
   * @param inviteTips  推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
   * @return 1:微信邀请 2.邮件邀请
   * @throws WxErrorException
   */
  int invite(String userId, String inviteTips) throws WxErrorException;

  /**
   * <pre>
   * 获取微信服务器的ip段
   * http://qydev.weixin.qq.com/wiki/index.php?title=回调模式#.E8.8E.B7.E5.8F.96.E5.BE.AE.E4.BF.A1.E6.9C.8D.E5.8A.A1.E5.99.A8.E7.9A.84ip.E6.AE.B5
   * </pre>
   * @return { "ip_list": ["101.226.103.*", "101.226.62.*"] }
   * @throws WxErrorException
   */
  String[] getCallbackIp() throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   * @param url
   * @param queryParam
   * @return
   * @throws WxErrorException
   */
  String get(String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   * @param url
   * @param postData
   * @return
   * @throws WxErrorException
   */
  String post(String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link MediaUploadRequestExecutor}的实现方法
   * </pre>
   * @param executor
   * @param uri
   * @param data
   * @param <T>
   * @param <E>
   * @return
   * @throws WxErrorException
   */
  <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;

  /**
   * 注入 {@link WxCpConfigStorage} 的实现
   *
   * @param wxConfigProvider
   */
  void setWxCpConfigStorage(WxCpConfigStorage wxConfigProvider);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   * @param retrySleepMillis
   */
  void setRetrySleepMillis(int retrySleepMillis);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   * @param maxRetryTimes
   */
  void setMaxRetryTimes(int maxRetryTimes);

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，则新建一个并返回。
   * @param id id可以为任意字符串，建议使用FromUserName作为id
   * @return
   */
  WxSession getSession(String id);

  /**
   * 获取某个sessionId对应的session,如果sessionId没有对应的session，若create为true则新建一个，否则返回null。
   * @param id id可以为任意字符串，建议使用FromUserName作为id
   * @param create
   * @return
   */
  WxSession getSession(String id, boolean create);

  /**
   * <pre>
   * 设置WxSessionManager，只有当需要使用个性化的WxSessionManager的时候才需要调用此方法，
   * WxCpService默认使用的是{@link StandardSessionManager}
   * </pre>
   * @param sessionManager
   */
  void setSessionManager(WxSessionManager sessionManager);
  
  /**
   * 上传部门列表覆盖企业号上的部门信息
   * @param mediaId  媒体文件ID
   * @throws WxErrorException
   */
  String replaceParty(String mediaId) throws WxErrorException;
  
  /**
   * 上传用户列表覆盖企业号上的用户信息
   * @param mediaId  媒体文件ID
   * @throws WxErrorException
   */
  String replaceUser(String mediaId) throws WxErrorException;
  
  /**
   * 获取异步任务结果
   * @param joinId 
   * @return
   * @throws WxErrorException
   */
  String getTaskResult(String joinId) throws WxErrorException;
  
  
  /**
   * 向客服人员发送消息，支持文本、图片、文件消息
   * 详情请见:http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%AE%A2%E6%9C%8D%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E
   * @param message  
   * @return
   * @throws WxErrorException
   */
  String sendKfMessage(WxCpKfMessage message) throws WxErrorException;
 
  /**
   * 获取客服列表
   * 详情请见:http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%AE%A2%E6%9C%8D%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E
   * @param type  客服类型1. internal 只获取内部客服列表2. external只获取外部客服列表 不填时，同时返回内部、外部客服列表  
   * @return
   * @throws WxErrorException
   */
  List<WxCpKfList>  getKfList(String type) throws WxErrorException;
  
  /**
   * 获取设备及用户信息
   * 详情请见:http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E8%AE%BE%E5%A4%87%E5%8F%8A%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF
   * @param ticket  摇周边业务的ticket，可在摇到的URL中得到，ticket生效时间为30分钟，每一次摇都会重新生成新的ticket 
   * @return
   * @throws WxErrorException
   */
  String  getShakeInfo(String ticket) throws WxErrorException;
}
