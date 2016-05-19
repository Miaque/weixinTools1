package com.huiju.weixin.mp.bean.custombuilder;

import com.huiju.weixin.mp.bean.WxMpCustomMessage;

/**
 * 消息builder 父类
 * @author Administrator
 */
public class BaseBuilder<T> {
  /**
   * 消息类型
   */
  protected String msgType;
  /**
   * 接受者微信号e
   */
  protected String toUser;

  /**
   * 接受者
   * @param toUser 接受者
   * @return 返回接受者
   */
  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  /**
   * 消息创建
   * @return 返回客服消息
   */
  public WxMpCustomMessage build() {
    WxMpCustomMessage m = new WxMpCustomMessage();
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    return m;
  }
}
