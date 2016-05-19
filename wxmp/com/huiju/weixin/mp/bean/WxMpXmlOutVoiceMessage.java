package com.huiju.weixin.mp.bean;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
/**
 * 同步回复给用户的语音消息，xml格式
 */
@XStreamAlias("xml")
public class WxMpXmlOutVoiceMessage extends WxMpXmlOutMessage {
  /**
   * 文件ID
   */
  @XStreamAlias("Voice")
  @XStreamConverter(value = XStreamMediaIdConverter.class)
  private String mediaId;
  /**
   * 默认构造函数
   */
  public WxMpXmlOutVoiceMessage() {
    this.msgType = WxConsts.XML_MSG_VOICE;
  }
  /**
   * 这个方法的作用是获取文件ID
   * @return 返回String类型变量
   */
  public String getMediaId() {
    return mediaId;
  }
  /**
   * 这个方法的作用是设置文件ID
   * @param mediaId 文件ID
   */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
