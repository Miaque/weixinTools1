package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 分组群发的消息
 * 
 * @author chanjarster
 */
public class WxMpMassGroupMessage implements Serializable {

  private static final long serialVersionUID = -2696380446712961499L;
  /**
   * 群发到的分组的group_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写group_id
   */
  private Long groupId;
  /**
   * 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
   */
  private String msgtype;
  /**
   * 文本消息
   */
  private String content;
  /**
   * 消息发送任务的ID
   */
  private String mediaId;

  /**
   * 默认构造函数
   */
  public WxMpMassGroupMessage() {
    super();
  }

  /**
   * 这个方法的作用是获取群发的消息类型
   * @return 返回String类型变量
     */
  public String getMsgtype() {
    return msgtype;
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
   * @param msgtype 群发的消息类型
   */
  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }

  /**
   * 这个方法的作用是获取文本消息
   * @return 返回String类型变量
     */
  public String getContent() {
    return content;
  }

  /**
   * 这个方法的作用是设置文本消息
   * @param content 文本消息
     */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 这个方法的作用是获取消息发送任务的ID
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置消息发送任务的ID
   * @param mediaId 消息发送任务的ID
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
   * 这个方法的作用是获取群发到的分组的group_id
   * @return 返回Long类型变量
     */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * 这个方法的作用是设置群发到的分组的group_id，如果不设置则就意味着发给所有用户
   * @param groupId 群发到的分组的group_id
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

}
