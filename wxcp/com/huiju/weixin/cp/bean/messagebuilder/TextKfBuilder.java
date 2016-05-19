package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 文本消息builder
 * <pre>
 * 用法: WxCpKfMessage m = WxCpKfMessage.TEXT().content(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class TextKfBuilder extends BaseKfBuilder<TextKfBuilder> {
  /**
   * 文本内容
   */
  private String content;

  /**
   * 默认构造函数
   */
  public TextKfBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_TEXT;
  }

  /**
   * 文本消息内容
   * @param content 文本消息内容
   * @return 返回TextKfBuilder
   */
  public TextKfBuilder content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpKfMessage
   */
  public WxCpKfMessage build() {
    WxCpKfMessage m = super.build();
    m.setContent(this.content);
    return m;
  }
}
