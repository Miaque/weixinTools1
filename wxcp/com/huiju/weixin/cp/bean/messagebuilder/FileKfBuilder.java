package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 获得file客服消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.FILE().mediaId(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class FileKfBuilder extends BaseKfBuilder<FileKfBuilder> {
  /**
   * 媒体文件ID
   */
  private String mediaId;

  /**
   * 默认构造方法
   */
  public FileKfBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_FILE;
  }

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回FileKfBuilder
   */
  public FileKfBuilder mediaId(String media_id) {
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
