package com.huiju.weixin.cp.bean.outxmlbuilder;

import com.huiju.weixin.cp.bean.WxCpXmlOutVoiceMessage;

/**
 * 语音消息builder
 * @author Daniel Qian
 */
public final class VoiceBuilder extends BaseBuilder<VoiceBuilder, WxCpXmlOutVoiceMessage> {

  private String mediaId;
  
  /**
   * 媒体文件ID
   * @param mediaId 媒体文件ID
   * @return 返回语音对象
   */
  public VoiceBuilder mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }
  
  /**
   * 消息创建
   */
  public WxCpXmlOutVoiceMessage build() {
    WxCpXmlOutVoiceMessage m = new WxCpXmlOutVoiceMessage();
    setCommon(m);
    m.setMediaId(mediaId);
    return m;
  }
  
}
