package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutVoiceMessage;

/**
 * 语音消息builder
 * @author chanjarster
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxMpXmlOutVoiceMessage> {
  /**
   * 媒体ID
   */
  private String mediaId;

  /**
   * 媒体文件ID
   * @param mediaId 媒体文件ID
   * @return 返回VoiceBuilder
   */
  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  /**
   * 消息创建
   */
  public WxMpXmlOutVoiceMessage build() {
    WxMpXmlOutVoiceMessage m = new WxMpXmlOutVoiceMessage();
    setCommon(m);
    m.setMediaId(mediaId);
    return m;
  }
  
}
