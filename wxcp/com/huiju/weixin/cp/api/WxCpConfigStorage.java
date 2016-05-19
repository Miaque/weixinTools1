package com.huiju.weixin.cp.api;

import java.io.File;

import com.huiju.weixin.common.bean.WxAccessToken;

/**
 * 微信客户端配置存储.
 * @author Daniel Qian
 *
 */
public interface WxCpConfigStorage {
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
   * 这个方法的作用是获取企业Id
   * @return 返回String类型变量
   */
  public String getCorpId();
  /**
   * 这个方法的作用是获取管理组的凭证密钥
   * @return 返回String类型变量
   */
  public String getCorpSecret();
  /**
   * 这个方法的作用是获取企业应用的id
   * @return 返回String类型变量
   */
  public String getAgentId();
  /**
   * 这个方法的作用是获取令牌
   * @return 返回String类型变量
   */
  public String getToken();
  /**
   * 这个方法的作用是获取加密所用的秘钥
   * @return 返回String类型变量
   */
  public String getAesKey();
  /**
   * 这个方法的作用是获取有效时长
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

}
