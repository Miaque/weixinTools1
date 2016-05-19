package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 获得Image消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.IMAGE().mediaId(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class ImageKfBuilder extends BaseKfBuilder<ImageKfBuilder> {
  /**
   * 媒体ID
   */
  private String mediaId;

  /**
   * 默认构造函数
   */
  public ImageKfBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_IMAGE;
  }

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回ImageKfBuilder
   */
  public ImageKfBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpKfMessage
   */
  public WxCpKfMessage build() {
	WxCpKfMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
