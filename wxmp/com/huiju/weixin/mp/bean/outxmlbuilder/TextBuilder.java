package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutTextMessage;

/**
 * 文本消息builder
 * @author chanjarster
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder, WxMpXmlOutTextMessage> {
  /**
   * 文本内容
   */
  private String content;

  /**
   * 文本内容
   * @param content 文本内容
   * @return 返回TextBuilder
   */
  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 消息创建
   */
  public WxMpXmlOutTextMessage build() {
    WxMpXmlOutTextMessage m = new WxMpXmlOutTextMessage();
    setCommon(m);
    m.setContent(this.content);
    return m;
  }
}
