package com.huiju.weixin.cp.bean.messagebuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 获得file消息builder
 * <pre>
 * 用法: WxCustomMessage m = WxCustomMessage.FILE().mediaId(...).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class FileBuilder extends BaseBuilder<FileBuilder> {
  /**
   * 媒体文件ID
   */
  private String mediaId;

  /**
   * 构造方法
   */
  public FileBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_FILE;
  }

  /**
   * 媒体文件ID
   * @param media_id 媒体文件ID
   * @return 返回FileBuilder
   */
  public FileBuilder mediaId(String media_id) {
    this.mediaId = media_id;
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setMediaId(this.mediaId);
    return m;
  }
}
