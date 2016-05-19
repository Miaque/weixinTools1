package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 群发时用到的视频素材
 * 
 * @author chanjarster
 */
public class WxMpMassVideo implements Serializable {

  /**
   * 用于群发的消息的media_id
   */
  private String mediaId;
  /**
   * 群发标题
   */
  private String title;
  /**
   * 消息的描述
   */
  private String description;

  /**
   * 这个方法的作用是获取群发标题
   * @return 返回String类型变量
     */
  public String getTitle() {
    return title;
  }

  /**
   * 这个方法的作用是设置群发标题
   * @param title 群发标题
     */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 这个方法的作用是获取消息的描述
   * @return 返回String类型变量
     */
  public String getDescription() {
    return description;
  }

  /**
   * 这个方法的作用是设置消息的描述
   * @param description 消息的描述
     */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 这个方法的作用是获取用于群发的消息的media_id
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置用于群发的消息的media_id
   * @param mediaId 用于群发的消息的media_id
     */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  /**
   * 这个方法的作用是将数据转化为Json格式
   * @return 返回String类型变量
     */
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
