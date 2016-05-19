package com.huiju.weixin.cp.bean;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的文本消息，xml格式
 */
@XStreamAlias("xml")
public class WxCpXmlOutTextMessage extends WxCpXmlOutMessage {
  
  /**
   * 消息文本
   */
  @XStreamAlias("Content")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String content;

  /**
   * 默认构造函数
   */
  public WxCpXmlOutTextMessage() {
    this.msgType = WxConsts.XML_MSG_TEXT;
  }

  /**
   * 这个方法的作用是获取消息文本
   * @return 返回string类型变量
     */
  public String getContent() {
    return content;
  }

  /**
   * 这个方法的作用是设置消息文本
   * @param content 消息文本
     */
  public void setContent(String content) {
    this.content = content;
  }

  
}
