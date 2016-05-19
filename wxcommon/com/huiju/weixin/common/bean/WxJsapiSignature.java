package com.huiju.weixin.common.bean;

import java.io.Serializable;

/**
 * JSAPI Signature类.
 * <p>微信JS-SDK是微信公众平台面向网页开发者提供的基于微信内的网页开发工具包。<br>
 */
public class WxJsapiSignature implements Serializable {
  /**
   * 企业号的唯一标识，此处填写企业号corpid
   */
  private String appid;
  /**
   * 签名的随机串
   */
  private String noncestr;
  /**
   * 签名的时间戳
   */
  private long timestamp;
  /**
   * 链接
   */
  private String url;
  /**
   * 签名
   */
  private String signature;

  /**
   * 该方法是用来获取签名
   * @return 返回String类型变量
   */
  public String getSignature() {
    return signature;
  }
  /**
   * 该方法是用来设置签名
   * @param signature 签名
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }
  /**
   * 该方法是用来获取生成的签名的随机串
   * @return 返回String类型变量
   */
  public String getNoncestr() {
    return noncestr;
  }
  /**
   * 该方法是用来设置签名的随机串
   * @param noncestr 签名的随机串
   */
  public void setNoncestr(String noncestr) {
    this.noncestr = noncestr;
  }
  /**
   * 该方法是用来获取签名的时间戳
   * @return 返回long类型变量
   */
  public long getTimestamp() {
    return timestamp;
  }
  /**
   * 该方法是用来设置签名的时间戳
   * @param timestamp 签名的时间戳
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
  /**
   * 该方法是用来获取链接
   * @return 返回String类型变量
   */
  public String getUrl() {
    return url;
  }
  /**
   * 该方法是用来设置链接
   * @param url 链接
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /**
   * 该方法是用来获取企业号的唯一标识
   * @return 返回String类型变量
   */
  public String getAppid() {
	  return appid;
  }
  /**
   * 该方法是用来设置企业号的唯一标识
   * @param appid 企业号的唯一标识
   */
  public void setAppid(String appid) {
	  this.appid = appid;
  }

}
