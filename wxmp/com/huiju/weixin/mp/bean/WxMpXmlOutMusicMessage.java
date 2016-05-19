package com.huiju.weixin.mp.bean;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的音乐消息，xml格式
 */
@XStreamAlias("xml")
public class WxMpXmlOutMusicMessage extends WxMpXmlOutMessage {
  /**
   * 音乐
   */
  @XStreamAlias("Music")
  protected final Music music = new Music();

  /**
   * 默认构造函数
   */
  public WxMpXmlOutMusicMessage() {
    this.msgType = WxConsts.XML_MSG_MUSIC;
  }

  /**
   * 这个方法的作用是获取标题
   * @return 返回String类型变量
     */
  public String getTitle() {
    return music.getTitle();
  }

  /**
   * 这个方法的作用是设置标题
   * @param title 标题
     */
  public void setTitle(String title) {
    music.setTitle(title);
  }

  /**
   * 这个方法的作用是获取描述
   * @return 返回String类型变量
     */
  public String getDescription() {
    return music.getDescription();
  }

  /**
   * 这个方法的作用是设置描述
   * @param description 描述
     */
  public void setDescription(String description) {
    music.setDescription(description);
  }

  /**
   * 这个方法的作用是获取缩略图的媒体id
   * @return 返回String类型变量
     */
  public String getThumbMediaId() {
    return music.getThumbMediaId();
  }

  /**
   * 这个方法的作用是设置缩略图的媒体id
   * @param thumbMediaId 缩略图的媒体id
     */
  public void setThumbMediaId(String thumbMediaId) {
    music.setThumbMediaId(thumbMediaId);
  }

  /**
   * 这个方法的作用是获取音乐路径
   * @return 返回String类型变量
     */
  public String getMusicUrl() {
    return music.getMusicUrl();
  }

  /**
   * 这个方法的作用是设置音乐路径
   * @param musicUrl 音乐路径
     */
  public void setMusicUrl(String musicUrl) {
    music.setMusicUrl(musicUrl);
  }

  /**
   * 这个方法的作用获取高清音乐链接
   * @return 返回String类型变量
     */
  public String getHqMusicUrl() {
    return music.getHqMusicUrl();
  }

  /**
   * 这个方法的作用设置高清音乐链接
   * @param hqMusicUrl 高清音乐链接
     */
  public void setHqMusicUrl(String hqMusicUrl) {
    music.setHqMusicUrl(hqMusicUrl);
  }
  
  /**
   * 音乐文件实体
   * @author Administrator
   */
  @XStreamAlias("Music")
  public static class Music {
    
	/**
	 * 标题
	 */
    @XStreamAlias("Title")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String title;

    /**
     * 描述
     */
    @XStreamAlias("Description")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String description;

    /**
     * 缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String thumbMediaId;
    
    /**
     * 音乐路径
     */
    @XStreamAlias("MusicUrl")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String musicUrl;
    
    /**
     * 高品质音乐链接
     */
    @XStreamAlias("HQMusicUrl")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String hqMusicUrl;

    /**
     * 这个方法的作用是获取标题
     * @return 返回String类型变量
     */
    public String getTitle() {
      return title;
    }

    /**
     * 这个方法的作用是设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * 这个方法的作用是获取描述
     * @return 返回String类型变量
     */
    public String getDescription() {
      return description;
    }

    /**
     * 这个方法的作用是设置描述
     * @param description 描述
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * 这个方法的作用是获取缩略图的媒体id
     * @return 返回String类型变量
     */
    public String getThumbMediaId() {
      return thumbMediaId;
    }

    /**
     * 这个方法的作用是设置缩略图的媒体id
     * @param thumbMediaId 缩略图的媒体id
     */
    public void setThumbMediaId(String thumbMediaId) {
      this.thumbMediaId = thumbMediaId;
    }

    /**
     * 这个方法的作用是获取音乐路径
     * @return 返回String类型变量
     */
    public String getMusicUrl() {
      return musicUrl;
    }

    /**
     * 这个方法的作用是设置音乐路径
     * @param musicUrl 音乐路径
     */
    public void setMusicUrl(String musicUrl) {
      this.musicUrl = musicUrl;
    }

    /**
     * 这个方法的作用获取高清音乐链接
     * @return 返回String类型变量
     */
    public String getHqMusicUrl() {
      return hqMusicUrl;
    }

    /**
     * 这个方法的作用设置高清音乐链接
     * @param hqMusicUrl 高清音乐链接
     */
    public void setHqMusicUrl(String hqMusicUrl) {
      this.hqMusicUrl = hqMusicUrl;
    }
    
  }

}
