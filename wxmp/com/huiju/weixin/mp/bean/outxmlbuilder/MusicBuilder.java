package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutMusicMessage;

/**
 * 音乐消息builder
 * 
 * @author chanjarster
 */
public final class MusicBuilder extends BaseBuilder<MusicBuilder, WxMpXmlOutMusicMessage> {
  /**
   * 标题
   */
  private String title;
  /**
   * 描述
   */
  private String description;
  /**
   * 高清音乐链接
   */
  private String hqMusicUrl;
  /**
   * 音乐链接
   */
  private String musicUrl;
  /**
   * 缩略图媒体文件ID
   */
  private String thumbMediaId;

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
   * 高品质音乐链接
   * @param hqMusicUrl 高品质音乐链接
   * @return 返回MusicBuilder
   */
  public MusicBuilder hqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
    return this;
  }

  /**
   * 音乐链接
   * @param musicUrl 音乐链接
   * @return 返回MusicBuilder
   */
  public MusicBuilder musicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
    return this;
  }

  /**
   * 缩略图媒体文件ID，可以调用多媒体文件下载接口拉取数据
   * @param thumbMediaId 缩略图媒体文件ID
   * @return 返回MusicBuilder
   */
  public MusicBuilder thumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
    return this;
  }

  /**
   * 消息创建
   */
  public WxMpXmlOutMusicMessage build() {
    WxMpXmlOutMusicMessage m = new WxMpXmlOutMusicMessage();
    setCommon(m);
    m.setTitle(title);
    m.setDescription(description);
    m.setHqMusicUrl(hqMusicUrl);
    m.setMusicUrl(musicUrl);
    m.setThumbMediaId(thumbMediaId);
    return m;
  }

}
