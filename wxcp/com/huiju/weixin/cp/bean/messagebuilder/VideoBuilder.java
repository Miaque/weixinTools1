package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 视频消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE()
 *                              .mediaId(...)
 *                              .title(...)
 *                              .thumbMediaId(..)
 *                              .description(..)
 *                              .toUser(...)
 *                              .build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder> {
  /**
   * 媒体ID
   */
  private String mediaId;
  /**
   * 标题
   */
  private String title;
  /**
   * 描述
   */
  private String description;
  /**
   * 缩略图ID
   */
  private String thumbMediaId;

  /**
   * 默认构造函数
   */
  public VideoBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_VIDEO;
  }

  /**
   * 媒体文件ID
   * @param mediaId 媒体文件ID
   * @return 返回VideoBuilder
   */
  public VideoBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  /**
   * 视频标题
   * @param title 视频标题
   * @return 返回VideoBuilder
   */
  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }

  /**
   * 视频描述
   * @param description 视频描述
   * @return 返回VideoBuilder
   */
  public VideoBuilder description(String description) {
    this.description = description;
    return this;
  }

  /**
   * 消息缩略图的media_id
   * @param thumb_media_id 消息缩略图的media_id
   * @return 返回VideoBuilder
   */
  public VideoBuilder thumbMediaId(String thumb_media_id) {
    this.thumbMediaId = thumb_media_id;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setMediaId(this.mediaId);
    m.setTitle(title);
    m.setDescription(description);
    m.setThumbMediaId(thumbMediaId);
    return m;
  }
}
