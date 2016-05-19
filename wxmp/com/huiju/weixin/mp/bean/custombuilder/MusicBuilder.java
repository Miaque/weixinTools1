package com.huiju.weixin.mp.bean.custombuilder;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;

/**
 * 音乐消息builder
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpCustomMessage.MUSIC()
 *                      .musicUrl(...)
 *                      .hqMusicUrl(...)
 *                      .title(...)
 *                      .thumbMediaId(..)
 *                      .description(..)
 *                      .toUser(...)
 *                      .build();
 * </pre>
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder> {
  /**
   * 标题
   */
  private String title;
  /**
   * 描述
   */
  private String description;
  /**
   * 缩略图的媒体id
   */
  private String thumbMediaId;
  /**
   * 音乐链接
   */
  private String musicUrl;
  /**
   * 高品质音乐链接
   */
  private String hqMusicUrl;

  /**
   * 默认构造函数
   */
  public MusicBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_MUSIC;
  }

  /**
   * 音乐链接
   * @param musicurl 音乐链接
   * @return 返回MusicBuilder
   */
  public MusicBuilder musicUrl(String musicurl) {
    this.musicUrl = musicurl;
    return this;
  }

  /**
   * 高品质音乐链接，wifi环境优先使用该链接播放音乐
   * @param hqMusicurl 高品质音乐链接
   * @return 返回MusicBuilder
   */
  public MusicBuilder hqMusicUrl(String hqMusicurl) {
    this.hqMusicUrl = hqMusicurl;
    return this;
  }

  /**
   * 标题
   * @param title 标题
   * @return 返回MusicBuilder
   */
  public MusicBuilder title(String title) {
    this.title = title;
    return this;
  }

  /**
   * 描述
   * @param description 描述
   * @return 返回MusicBuilder
   */
  public MusicBuilder description(String description) {
    this.description = description;
    return this;
  }

  /**
   * 缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param thumb_media_id 缩略图的媒体id
   * @return 返回MusicBuilder
   */
  public MusicBuilder thumbMediaId(String thumb_media_id) {
    this.thumbMediaId = thumb_media_id;
    return this;
  }

  /**
   * 消息创建
   * @return  返回WxMpCustomMessage
   */
  public WxMpCustomMessage build() {
    WxMpCustomMessage m = super.build();
    m.setMusicUrl(this.musicUrl);
    m.setHqMusicUrl(this.hqMusicUrl);
    m.setTitle(title);
    m.setDescription(description);
    m.setThumbMediaId(thumbMediaId);
    return m;
  }
}
