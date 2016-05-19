package com.huiju.weixin.mp.bean.custombuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;

/**
 * 获得消息builder
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.IMAGE().mediaId(...).toUser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class ImageBuilder extends BaseBuilder<ImageBuilder> {
  private String mediaId;

  public ImageBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_IMAGE;
  }

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
   * @return 返回WxMpCustomMessage
   */
  public WxMpCustomMessage build() {
    WxMpCustomMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
