package com.huiju.weixin.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * OpenId列表群发的消息
 * 
 * @author chanjarster
 */
public class WxMpMassOpenIdsMessage implements Serializable {
  /**
   * 填写图文消息的接收者，一串OpenID列表，OpenID最少2个，最多10000个
   */
  private List<String> toUsers = new ArrayList<String>();
  /**
   * 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
   */
  private String msgType;
  /**
   * 发送文本消息时文本的内容
   */
  private String content;
  /**
   * 用于群发的消息的media_id
   */
  private String mediaId;

  /**
   * 默认构造函数
   */
  public WxMpMassOpenIdsMessage() {
    super();
  }

  /**
   * 这个方法的作用是获取群发的消息类型
   * @return 返回String类型变量
     */
  public String getMsgType() {
    return msgType;
  }

  /**
   * 这个方法的作用是设置群发的消息类型
   * <pre>
   * 请使用
   * {@link WxConsts#MASS_MSG_IMAGE}
   * {@link WxConsts#MASS_MSG_NEWS}
   * {@link WxConsts#MASS_MSG_TEXT}
   * {@link WxConsts#MASS_MSG_VIDEO}
   * {@link WxConsts#MASS_MSG_VOICE}
   * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
   * </pre>
   * @param msgType 群发的消息类型
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  /**
   * 这个方法的作用是获取消息文本内容
   * @return 返回String类型变量
     */
  public String getContent() {
    return content;
  }

  /**
   * 这个方法的作用是设置消息文本内容
   * @param content 消息文本内容
     */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 这个方法的作用是获取群发的消息的media_id
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置群发的消息的media_id
   * @param mediaId 群发的消息的media_id
     */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  /**
   * 这个方法的作用是将数据转化成Json格式
   * @return 返回String类型变量
     */
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  /**
   * OpenId列表，最多支持10,000个
   * @return 返回接受者列表
   */
  public List<String> getToUsers() {
    return toUsers;
  }

  /**
   * 添加OpenId，最多支持10,000个
   * @param openId 公众号的普通用户的一个唯一的标识
   */
  public void addUser(String openId) {
    this.toUsers.add(openId);
  }
}
