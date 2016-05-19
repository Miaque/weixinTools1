package com.huiju.weixin.cp.bean.outxmlbuilder;

import com.huiju.weixin.cp.bean.WxCpXmlOutVideoMessage;

/**
 * 视频消息builder
 * @author Daniel Qian
 *
 */
public final class VideoBuilder extends BaseBuilder<VideoBuilder, WxCpXmlOutVideoMessage> {

  private String mediaId;
  private String title;
  private String description;

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
   * 媒体文件ID
   * @param mediaId 媒体文件ID
   * @return 返回视频对象
   */
  public VideoBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  /**
   * 消息创建
   */
  public WxCpXmlOutVideoMessage build() {
    WxCpXmlOutVideoMessage m = new WxCpXmlOutVideoMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setMediaId(mediaId);
    return m;
  }
  
}
