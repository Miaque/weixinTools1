package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutVideoMessage;

/**
 * 视频消息builder
 * @author chanjarster
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxMpXmlOutVideoMessage> {
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
   * 标题
   * @param title 标题
   * @return 返回VideoBuilder
   */
  public VideoBuilder title(String title) {
    this.title = title;
    return this;
  }
  
  /**
   * 描述
   * @param description 描述
   * @return 返回VideoBuilder
   */
  public VideoBuilder description(String description) {
    this.description = description;
    return this;
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
   * 消息创建
   */
  public WxMpXmlOutVideoMessage build() {
    WxMpXmlOutVideoMessage m = new WxMpXmlOutVideoMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setMediaId(mediaId);
    return m;
  }
  
}
