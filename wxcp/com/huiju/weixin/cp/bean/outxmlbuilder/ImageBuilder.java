package com.huiju.weixin.cp.bean.outxmlbuilder;

import com.huiju.weixin.cp.bean.WxCpXmlOutImageMessage;

/**
 * 图片消息builder
 * @author Daniel Qian
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder, WxCpXmlOutImageMessage> {

  private String mediaId;

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回图片对象
   */
  public ImageBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  /**
   * 消息实体创建
   */
  public WxCpXmlOutImageMessage build() {
    WxCpXmlOutImageMessage m = new WxCpXmlOutImageMessage();
    setCommon(m);
    m.setMediaId(this.mediaId);
    return m;
  }
  
}
