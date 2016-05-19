package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 消息创建父类方法
 * @author Administrator
 */
public class BaseBuilder<T> {
  /**
   * 消息类型：file、image、news、text、video、voice
   */
  protected String msgType;
  /**
   * 企业应用的id，整型。可在应用的设置页面查看
   */
  protected String agentId;
  /**
   * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
   */
  protected String toUser;
  /**
   * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
   */
  protected String toParty;
  /**
   * 标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
   */
  protected String toTag;
  /**
   * 表示是否是保密消息，0表示否，1表示是，默认0
   */
  protected String safe;

  /**
   * 企业ID设置
   * @param agentId 企业ID
   * @return 返回T
   */
  public T agentId(String agentId) {
    this.agentId = agentId;
    return (T) this;
  }

  /**
   * 消息接受者
   * @param toUser 消息接受者
   * @return 返回T
   */
  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  /**
   * 部门ID列表
   * @param toParty 部门ID列表
   * @return 返回T
   */
  public T toParty(String toParty) {
    this.toParty = toParty;
    return (T) this;
  }

  /**
   * 标签ID列表
   * @param toTag 标签ID列表
   * @return 返回T
   */
  public T toTag(String toTag) {
    this.toTag = toTag;
    return (T) this;
  }

  /**
   * 表示是否是保密消息，0表示否，1表示是，默认0
   * @param safe 是否是保密消息
   * @return 返回T
   */
  public T safe(String safe) {
    this.safe = safe;
    return (T) this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = new WxCpMessage();
    m.setAgentId(this.agentId);
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    m.setToParty(this.toParty);
    m.setToTag(this.toTag);
    m.setSafe(
      (this.safe == null || "".equals(this.safe))? WxConsts.CUSTOM_MSG_SAFE_NO: this.safe);
    return m;
  }

}
