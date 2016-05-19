package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 换取二维码的Ticket
 * 
 * @author chanjarster
 */
public class WxMpQrCodeTicket implements Serializable {
  
  protected String ticket;//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
  protected int expire_seconds = -1;//该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
  protected String url;//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  /**
   * 如果返回-1说明是永久
   */
  public int getExpire_seconds() {
    return expire_seconds;
  }

  public void setExpire_seconds(int expire_seconds) {
    this.expire_seconds = expire_seconds;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 返回结果数据转换
   * @param json
   * @return
   */
  public static WxMpQrCodeTicket fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpQrCodeTicket.class);
  }
}
