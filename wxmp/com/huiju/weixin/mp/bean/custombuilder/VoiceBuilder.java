package com.huiju.weixin.mp.bean.custombuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.VOICE().mediaId(...).toUser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder> {
  /**
   * 媒体ID
   */
  private String mediaId;

  /**
   * 默认构造函数
   */
  public VoiceBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_VOICE;
  }

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回VoiceBuilder
   */
  public VoiceBuilder mediaId(String media_id) {
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
