package com.huiju.weixin.cp.bean;

import java.io.Serializable;

/**
 * 客服消息.
 * @author Daniel Qian
 *
 */
public class WxCpKfMessage implements Serializable {

  private static final long serialVersionUID = 7673861693786789540L;
  
  /**
   * 消息类型
   */
  private String msgType;
  
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
  private String senderType;
  
  /**
   * 发送人用户ID
   */
  private String senderId;
  
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
  private String receiverType;
  
  /**
   * 接收人用户ID
   */
  private String receiverId;  
  /**
   * 内容
   */
  private String content;
  /**
   *上传素材文件id
   */
  private String mediaId;

    /**
     * 这个方法的作用是获取消息类型
     * @return 返回String类型变量
     */
  public String getMsgType() {
	return msgType;
  }

    /**
     * 这个方法的作用是设置消息类型
     * @param msgType 消息类型
     */
  public void setMsgType(String msgType) {
  	this.msgType = msgType;
  }

    /**
     * 这个方法的作用是获取发送人类型
     * @return 返回String类型变量
     */
  public String getSenderType() {
	 return senderType;
  }

    /**
     * 这个方法的作用是设置发送人类型
     * @param senderType 发送人类型
     */
  public void setSenderType(String senderType) {
	 this.senderType = senderType;
  }

    /**
     * 这个方法的作用是获取发送人用户ID
     * @return 返回String类型变量
     */
  public String getSenderId() {
	 return senderId;
  }

    /**
     * 这个方法的作用是设置发送人用户ID
     * @param senderId 发送人用户ID
     */
  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

    /**
     * 这个方法的作用是获取接收人类型
     * @return 返回String类型变量
     */
  public String getReceiverType() {
    return receiverType;
  }

    /**
     * 这个方法的作用是设置接收人类型
     * @param receiverType 接收人类型
     */
  public void setReceiverType(String receiverType) {
    this.receiverType = receiverType;
  }

    /**
     * 这个方法的作用是获取接收人用户ID
     * @return 返回String类型变量
     */
  public String getReceiverId() {
	 return receiverId;
  }

    /**
     * 这个方法的作用是设置接收人用户ID
     * @param receiverId 接收人用户ID
     */
  public void setReceiverId(String receiverId) {
     this.receiverId = receiverId;
  }

    /**
     * 这个方法的作用是获取内容
     * @return 返回String类型变量
     */
  public String getContent() {
	return content;
  }

    /**
     * 这个方法的作用是设置内容
     * @param content 内容
     */
  public void setContent(String content) {
	  this.content = content;
  }

    /**
     * 这个方法的作用是获取上传素材文件id
     * @return 返回String类型变量
     */
  public String getMediaId() {
	  return mediaId;
  }

    /**
     * 这个方法的作用是设置上传素材文件id
     * @param mediaId 上传素材文件id
     */
  public void setMediaId(String mediaId) {
	  this.mediaId = mediaId;
  }
  
  
  

  /**
   * 获得文本消息builder
   * @return
   */
  /*public static TextKfBuilder TEXT() {
    return new TextBuilder();
  }

  *//**
   * 获得图片消息builder
   * @return
   *//*
  public static ImageKfBuilder IMAGE() {
    return new ImageBuilder();
  }

  *//**
   * 获得语音消息builder
   * @return
   *//*
  public static VoiceKfBuilder VOICE() {
    return new VoiceBuilder();
  }
    

  *//**
   * 获得文件消息builder
   * @return
   *//*
  public static FileKfBuilder FILE() {
    return new FileBuilder();
  }*/
  
}
