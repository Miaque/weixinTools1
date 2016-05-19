package com.huiju.weixin.common.result;

import java.io.Serializable;

import com.huiju.weixin.common.util.json.WxGsonBuilder;

/**
 * 管理素材文件类.
 * <p>企业可以使用素材管理的接口将多媒体文件包括图片、音频、视频、文件以及图文消息上传到素材库。<br>
 */
public class WxMediaUploadResult implements Serializable {

  private static final long serialVersionUID = 4932000961754146015L;
  /**
   * 媒体文件类型
   */
  private String type;
  /**
   * 媒体文件上传后获取的唯一标识
   */
  private String mediaId;
  /**
   * 图文消息缩略图的media_id
   */
  private String thumbMediaId;
  /**
   * 媒体文件上传时间戳
   */
  private long createdAt;
  /**
   * 该方法的作用是获取媒体文件类型
   * @return 返回String类型变量
   */
  public String getType() {
    return type;
  }
  /**
   * 该方法的作用是设置媒体文件类型
   * @param type 媒体文件类型
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * 该方法的作用是媒体文件上传后获取的唯一标识
   * @return 返回String类型变量
   */
  public String getMediaId() {
    return mediaId;
  }
  /**
   * 该方法的作用是设置媒体文件上传后获取的唯一标识
   * @param mediaId 媒体文件上传后获取的唯一标识
   */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  /**
   * 该方法的作用是获取媒体文件上传时间戳
   * @return 返回long类型变量
   */
  public long getCreatedAt() {
    return createdAt;
  }
  /**
   * 该方法的作用是设置媒体文件上传时间戳
   * @param createdAt 媒体文件上传时间戳
   */
  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }
  /**
   * 该方法的作用是获取媒体文件上传时间戳
   * @return 返回String类型变量
   */
  public String getThumbMediaId() {
    return thumbMediaId;
  }
  /**
   * 该方法的作用是设置图文消息缩略图的media_id
   * @param thumbMediaId 图文消息缩略图的media_id
   */
  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }
  /**
   * 该方法的作用是将Json格式数据转化为WxMediaUploadResult对象类
   * @param json 格式数据
   */
  public static WxMediaUploadResult fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMediaUploadResult.class);
  }

  @Override
  public String toString() {
    return "WxUploadResult [type=" + type + ", media_id=" + mediaId + ", thumb_media_id=" + thumbMediaId
        + ", created_at=" + createdAt + "]";
  }

}
