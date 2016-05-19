package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.TEXT().content(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class TextBuilder extends BaseBuilder<TextBuilder> {
  /**
   * 文本内容
   */
  private String content;

  /**
   * 默认构造函数
   */
  public TextBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_TEXT;
  }

  /**
   * 文本消息内容
   * @param content 文本消息内容
   * @return 返回TextBuilder
   */
  public TextBuilder content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}
