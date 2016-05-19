package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutImageMessage;


/**
 * 图片消息builder
 * @author chanjarster
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxMpXmlOutImageMessage> {
  /**
   * 媒体文件ID
   */
  private String mediaId;

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回ImageBuilder
   */
  public ImageBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  /**
   * 消息创建
   */
  public WxMpXmlOutImageMessage build() {
    WxMpXmlOutImageMessage m = new WxMpXmlOutImageMessage();
    setCommon(m);
    m.setMediaId(this.mediaId);
    return m;
  }
  
}
