package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * <pre>
 * 上传群发用的素材的结果
 * 视频和图文消息需要在群发前上传素材
 * </pre>
 * @author chanjarster
 *
 */
public class WxMpMassUploadResult implements Serializable {

  /**
   * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），次数为news，即图文消息
   */
  private String type;
  /**
   * 媒体文件ID，上传后的唯一标识
   */
  private String mediaId;
  /**
   * 媒体文件上传时间
   */
  private long createdAt;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * 返回结果数据转换
   * @param json
   * @return
   */
  public static WxMpMassUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMassUploadResult.class);
  }

  @Override
  public String toString() {
    return "WxUploadResult [type=" + type + ", media_id=" + mediaId + ", created_at=" + createdAt + "]";
  }

}
