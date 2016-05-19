package com.huiju.weixin.mp.api;

import java.io.File;

import javax.net.ssl.SSLContext;

import com.huiju.weixin.common.bean.WxAccessToken;

/**
 * 微信客户端配置存储
 * @author chanjarster
 *
 */
public interface WxMpConfigStorage {
  /**
   * 这个方法的作用是获取凭证
   * @return 返回String类型变量
   */
  public String getAccessToken();
  /**
   * 这个方法的作用是判断凭证是否有效
   * @return 返回boolean类型变量
   */
  public boolean isAccessTokenExpired();
  /**
   * 这个方法的作用是强制将access token过期
   */
  public void expireAccessToken();

  /**
   * 这个方法的作用是更新accessToken凭证
   * @param accessToken 凭证
   */
  public void updateAccessToken(WxAccessToken accessToken);

  /**
   * 这个方法的作用是更新accessToken凭证
   * @param accessToken 凭证
   * @param expiresIn 凭证有效时间
   */
  public void updateAccessToken(String accessToken, int expiresIn);
  /**
   * 这个方法的作用是获取用于调用微信JS接口的临时票据
   * @return 返回String类型变量
   */
  public String getJsapiTicket();
  /**
   * 这个方法的作用是判断临时票据是否有效
   * @return 返回boolean类型变量
   */
  public boolean isJsapiTicketExpired();

  /**
   * 这个方法的作用是强制将jsapi ticket过期掉
   */
  public void expireJsapiTicket();

  /**
   * 这个方法的作用是更新用于调用微信JS接口的临时票据
   * @param jsapiTicket 临时票据
   * @param expiresInSeconds 有效时间（秒）
   */
  public void updateJsapiTicket(String jsapiTicket, int expiresInSeconds);

  /**
   * 第三方用户唯一凭证
   * @return 返回String类型变量
     */
  public String getAppId();

  /**
   * 第三方用户唯一凭证密钥
   * @return 返回String类型变量
     */
  public String getSecret();

  /**
   * 财付通商户身份的标识
   * @return 返回String类型变量
     */
    public String getPartnerId();

  /**
   * 财付通商户权限密钥Key
   * @return 返回String类型变量
     */
    public String getPartnerKey();
  /**
   * 这个方法的作用是获取凭证
   * @return 返回String类型变量
   */
  public String getToken();
  /**
   * 这个方法的作用是获取加密所用的秘钥
   * @return 返回String类型变量
   */
  public String getAesKey();
  /**
   * 这个方法的作用是获取凭证有效时长
   * @return 返回long类型变量
   */
  public long getExpiresTime();
  /**
   * 这个方法的作用是获取鉴权后的链接
   * @return 返回String类型变量
   */
  public String getOauth2redirectUri();
  /**
   * 这个方法的作用是获取http代理主机
   * @return 返回String类型变量
   */
  public String getHttp_proxy_host();
  /**
   * 这个方法的作用是获取http代理端口
   * @return 返回int类型变量
   */
  public int getHttp_proxy_port();
  /**
   * 这个方法的作用是获取http代理用户名
   * @return 返回String类型变量
   */
  public String getHttp_proxy_username();
  /**
   * 这个方法的作用是获取http代理密码
   * @return 返回String类型变量
   */
  public String getHttp_proxy_password();
  /**
   * 这个方法的作用是获取临时文件目录
   * @return 返回File类型文件
   */
  public File getTmpDirFile();

  /**
   * 这个方法的作用是获取SSLContext
   * @return 返回SSLContext
     */
  public SSLContext getSSLContext();
}
