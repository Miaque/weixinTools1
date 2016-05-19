package com.huiju.weixin.cp.api;

import java.io.File;

import com.huiju.weixin.common.bean.WxAccessToken;

/**
 * 基于内存的微信配置provider，在实际生产环境中应该将这些配置持久化
 * @author Daniel Qian
 *
 */
public class WxCpInMemoryConfigStorage implements WxCpConfigStorage {
  /**
   * 企业号的标识
   */
  protected volatile String corpId;
  /**
   * 管理组的凭证密钥
   */
  protected volatile String corpSecret;
  /**
   * 可由企业任意填写，用于生成签名
   */
  protected volatile String token;
  /**
   * 调用接口凭证
   */
  protected volatile String accessToken;
  /**
   * 加密所用的秘钥
   */
  protected volatile String aesKey;
  /**
   * 应用id
   */
  protected volatile String agentId;
  /**
   * 有效时长
   */
  protected volatile long expiresTime;
  /**
   * 对重定向链接鉴权
   */
  protected volatile String oauth2redirectUri;
  /**
   * http代理主机
   */
  protected volatile String http_proxy_host;
  /**
   * http代理主机端口
   */
  protected volatile int http_proxy_port;
  /**
   * http代理用户名
   */
  protected volatile String http_proxy_username;
  /**
   * http代理用户名密码
   */
  protected volatile String http_proxy_password;
  /**
   * 临时票据
   */
  protected volatile String jsapiTicket;
  /**
   * 临时票据有效时间
   */
  protected volatile long jsapiTicketExpiresTime;
  /**
   * 临时文件目录
   */
  protected volatile File tmpDirFile;
  /**
   * 这个方法的作用是获取接口凭证
   * @return 返回String类型变量
   */
  public String getAccessToken() {
    return this.accessToken;
  }
  /**
   * 这个方法的作用是判断接口凭证是否过期
   * @return 返回boolean类型变量
   */
  public boolean isAccessTokenExpired() {
    return System.currentTimeMillis() > this.expiresTime;
  }
  /**
   * 这个方法的作用是强制使接口凭证过期
   */
  public void expireAccessToken() {
    this.expiresTime = 0;
  }
  /**
   * 这个方法的作用是更新接口凭证
   * @param accessToken 凭证
   */
  public synchronized void updateAccessToken(WxAccessToken accessToken) {
    updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
  }
  /**
   * 这个方法的作用是更新接口凭证
   * @param accessToken 凭证
   * @param expiresInSeconds 有效时间（秒）
   */
  public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
    this.accessToken = accessToken;
    this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }
  /**
   * 这个方法的作用是获取临时票据
   * @return 返回String类型变量
   */
  @Override
  public String getJsapiTicket() {
    return jsapiTicket;
  }
  /**
   * 这个方法的作用是设置临时票据
   * @param jsapiTicket 临时票据
   */
  public void setJsapiTicket(String jsapiTicket) {
    this.jsapiTicket = jsapiTicket;
  }
  /**
   * 这个方法的作用是获取临时票据有效时间
   * @return 返回Long类型变量
   */
  public long getJsapiTicketExpiresTime() {
    return jsapiTicketExpiresTime;
  }
  /**
   * 这个方法的作用是设置临时票据有效时间
   * @param jsapiTicketExpiresTime 临时票据有效时间
   */
  public void setJsapiTicketExpiresTime(long jsapiTicketExpiresTime) {
    this.jsapiTicketExpiresTime = jsapiTicketExpiresTime;
  }
  /**
   * 这个方法的作用是判断临时票据是否过期
   * @return 返回boolean类型变量
   */
  public boolean isJsapiTicketExpired() {
    return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
  }
  /**
   * 这个方法的作用是更新接口凭证
   * @param jsapiTicket 临时票据
   * @param expiresInSeconds 有效时间（秒）
   */
  public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
    this.jsapiTicket = jsapiTicket;
    // 预留200秒的时间
    this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
  }
  /**
   * 这个方法的作用是强制使临时票据过期
   */
  public void expireJsapiTicket() {
    this.jsapiTicketExpiresTime = 0;
  }
  /**
   * 这个方法的作用是获取企业号的标识
   * @return 返回String类型变量
   */
  public String getCorpId() {
    return this.corpId;
  }
  /**
   * 这个方法的作用是管理组的凭证密钥
   * @return 返回String类型变量
   */
  public String getCorpSecret() {
    return this.corpSecret;
  }
  /**
   * 这个方法的作用是获取凭证
   * @return 返回String类型变量
   */
  public String getToken() {
    return this.token;
  }
  /**
   * 这个方法的作用是获取有效时间
   * @return 返回Long类型变量
   */
  public long getExpiresTime() {
    return this.expiresTime;
  }
  /**
   * 这个方法的作用是设置企业号的标识
   * @param corpId 企业号的标识
   */
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  /**
   * 这个方法的作用是设置管理组的凭证密钥
   * @param corpSecret 管理组的凭证密钥
   */
  public void setCorpSecret(String corpSecret) {
    this.corpSecret = corpSecret;
  }
  /**
   * 这个方法的作用是设置凭证
   * @param token 凭证
   */
  public void setToken(String token) {
    this.token = token;
  }
  /**
   * 这个方法的作用是获取加密所用的秘钥
   * @return 返回String类型变量
   */
  public String getAesKey() {
    return aesKey;
  }
  /**
   * 这个方法的作用是设置加密所用的秘钥
   * @param aesKey 加密所用的秘钥
   */
  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }
  /**
   * 这个方法的作用是设置接口凭证
   * @param accessToken 接口凭证
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  /**
   * 这个方法的作用是设置有效时间
   * @param expiresTime 有效时间
   */
  public void setExpiresTime(long expiresTime) {
    this.expiresTime = expiresTime;
  }
  /**
   * 这个方法的作用是获取应用id
   * @return 返回String类型变量
   */
  public String getAgentId() {
    return agentId;
  }
  /**
   * 这个方法的作用是设置应用id
   * @param agentId 应用id
   */
  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }
  /**
   * 这个方法的作用是获取重定向链接鉴权
   * @return 返回String类型变量
   */
  @Override
  public String getOauth2redirectUri() {
    return this.oauth2redirectUri;
  }
  /**
   * 这个方法的作用是设置重定向链接鉴权
   * @param oauth2redirectUri 重定向链接鉴权
   */
  public void setOauth2redirectUri(String oauth2redirectUri) {
    this.oauth2redirectUri = oauth2redirectUri;
  }
  /**
   * 这个方法的作用是获取http代理服务主机
   * @return 返回String类型变量
   */
  public String getHttp_proxy_host() {
    return http_proxy_host;
  }
  /**
   * 这个方法的作用是设置http代理服务主机
   * @param http_proxy_host http代理服务主机
   */
  public void setHttp_proxy_host(String http_proxy_host) {
    this.http_proxy_host = http_proxy_host;
  }
  /**
   * 这个方法的作用是获取http代理服务主机端口
   * @return 返回int类型变量
   */
  public int getHttp_proxy_port() {
    return http_proxy_port;
  }
  /**
   * 这个方法的作用是设置http代理服务主机端口
   * @param http_proxy_port http代理服务主机端口
   */
  public void setHttp_proxy_port(int http_proxy_port) {
    this.http_proxy_port = http_proxy_port;
  }
  /**
   * 这个方法的作用是获取http代理用户名
   * @return 返回String类型变量
   */
  public String getHttp_proxy_username() {
    return http_proxy_username;
  }
  /**
   * 这个方法的作用是设置http_proxy_username
   * @param http_proxy_username http_proxy_username
   */
  public void setHttp_proxy_username(String http_proxy_username) {
    this.http_proxy_username = http_proxy_username;
  }
  /**
   * 这个方法的作用是获取http代理用户名密码
   * @return 返回String类型变量
   */
  public String getHttp_proxy_password() {
    return http_proxy_password;
  }
  /**
   * 这个方法的作用是
   * @param
   */
  public void setHttp_proxy_password(String http_proxy_password) {
    this.http_proxy_password = http_proxy_password;
  }

  @Override
  public String toString() {
    return "WxCpInMemoryConfigStorage{" +
        "corpId='" + corpId + '\'' +
        ", corpSecret='" + corpSecret + '\'' +
        ", token='" + token + '\'' +
        ", accessToken='" + accessToken + '\'' +
        ", aesKey='" + aesKey + '\'' +
        ", agentId='" + agentId + '\'' +
        ", expiresTime=" + expiresTime +
        ", http_proxy_host='" + http_proxy_host + '\'' +
        ", http_proxy_port=" + http_proxy_port +
        ", http_proxy_username='" + http_proxy_username + '\'' +
        ", http_proxy_password='" + http_proxy_password + '\'' +
        ", jsapiTicket='" + jsapiTicket + '\'' +
        ", jsapiTicketExpiresTime='" + jsapiTicketExpiresTime + '\'' +
        ", tmpDirFile='" + tmpDirFile + '\'' +
        '}';
  }
  /**
   * 这个方法的作用是获取临时文件目录
   * @return 返回File类型文件
   */
  public File getTmpDirFile() {
    return tmpDirFile;
  }
  /**
   * 这个方法的作用是设置临时文件目录
   * @param tmpDirFile 临时文件目录
   */
  public void setTmpDirFile(File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

}
