package com.huiju.weixin.cp.bean.outxmlbuilder;

import com.huiju.weixin.cp.bean.WxCpXmlOutTextMessage;

/**
 * 文本消息builder
 * @author Daniel Qian
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxCpXmlOutTextMessage> {
  private String content;

  /**
   * 文本消息内容
   * @param content 文本消息内容
   * @return 返回文本对象
   */
  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 消息创建
   */
  public WxCpXmlOutTextMessage build() {
    WxCpXmlOutTextMessage m = new WxCpXmlOutTextMessage();
    setCommon(m);
    m.setContent(this.content);
    return m;
  }
}
