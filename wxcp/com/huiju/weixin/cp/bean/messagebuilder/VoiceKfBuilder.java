package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 语音消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.VOICE().mediaId(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class VoiceKfBuilder extends BaseKfBuilder<VoiceKfBuilder> {
  /**
   * 媒体ID
   */
  private String mediaId;

  /**
   * 默认构造函数
   */
  public VoiceKfBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_VOICE;
  }
  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回VoiceKfBuilder
   */
  public VoiceKfBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpKfMessage build() {
	WxCpKfMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
