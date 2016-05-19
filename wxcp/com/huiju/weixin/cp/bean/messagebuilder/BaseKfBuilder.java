package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 客服消息创建父类方法
 * @author Administrator
 */
public class BaseKfBuilder<T> {
  /**
   * 消息类型：file、image、text、voice
   */
  protected String msgType; 
  
  /**
   * 发送人类型
   * 用户类型，有下面几种
		1. kf
		客服
		2. userid
		客户，企业员工userid
		3. openid
		客户，公众号openid 
   */
  protected String senderType;
  
  /**
   * 发送人用户ID
   */
  protected String senderId;
  
  /**
   * 接收人 类型
   * 用户类型，有下面几种
		1. kf
		客服
		2. userid
		客户，企业员工userid
		3. openid
		客户，公众号openid 
   */
  protected String receiverType;
  
  /**
   * 接收人用户ID
   */
  protected String receiverId; 
  
  /**
   * 发送类型
   * @param msgType 发送类型
   * @return 返回T
   */
  public T msgType(String msgType) {
    this.msgType = msgType;
    return (T) this;
  }

  /**
   * 发送人类型设置
   * @param senderType 发送人类型设置
   * @return 返回T
   */
  public T senderType(String senderType) {
    this.senderType = senderType;
    return (T) this;
  }

  /**
   * 发送人用户ID
   * @param senderId 发送人用户ID
   * @return 返回T
   */
  public T senderId(String senderId) {
    this.senderId = senderId;
    return (T) this;
  }

  /**
   * 接收人类型
   * @param receiverType 接收人类型
   * @return 返回T
   */
  public T receiverType(String receiverType) {
    this.receiverType = receiverType;
    return (T) this;
  }

  /**
   * 接收人Id
   * @param receiverId 接收人Id
   * @return 返回T
   */
  public T receiverId(String receiverId) {
    this.receiverId = receiverId;
    return (T) this;
  }
 

  /**
   * 消息实体创建
   * @return 返回WxCpKfMessage
   */
  public WxCpKfMessage build() {
    WxCpKfMessage m = new  WxCpKfMessage();
    m.setReceiverId(this.receiverId);
    m.setReceiverType(this.receiverType);
    m.setSenderId(this.senderId);
    m.setMsgType(this.msgType);
    m.setSenderType(this.senderType); 
    return m;
  }

}
