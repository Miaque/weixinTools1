package com.huiju.weixin.cp.bean;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamMediaIdConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的图片消息，xml格式
 */
@XStreamAlias("xml")
public class WxCpXmlOutImageMessage extends WxCpXmlOutMessage {
 
  /**
   * 媒体文件ID
   */
  @XStreamAlias("Image")
  @XStreamConverter(value=XStreamMediaIdConverter.class)
  private String mediaId;

  /**
   * 默认构造函数
   */
  public WxCpXmlOutImageMessage() {
    this.msgType = WxConsts.XML_MSG_IMAGE;
  }

  /**
   * 这个方法的作用是获取媒体文件ID
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置媒体文件ID
   * @param mediaId 媒体文件ID
     */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  
}
