package com.huiju.weixin.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;
import com.huiju.weixin.mp.bean.custombuilder.ImageBuilder;
import com.huiju.weixin.mp.bean.custombuilder.MusicBuilder;
import com.huiju.weixin.mp.bean.custombuilder.NewsBuilder;
import com.huiju.weixin.mp.bean.custombuilder.TextBuilder;
import com.huiju.weixin.mp.bean.custombuilder.VideoBuilder;
import com.huiju.weixin.mp.bean.custombuilder.VoiceBuilder;

/**
 * 客服消息
 * @author chanjarster
 *
 */
public class WxMpCustomMessage implements Serializable {

  private static final long serialVersionUID = -3258398451494228385L;
  /**
   * 普通用户openid
   */
  private String toUser;
  /**
   * 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，
   * 图文消息（点击跳转到外链）为news，图文消息（点击跳转到图文消息页面）为mpnews，卡券为wxcard
   */
  private String msgType;
  /**
   * 文本消息内容
   */
  private String content;
  /**
   * 发送的图片/语音/视频/图文消息（点击跳转到图文消息页）的媒体ID
   */
  private String mediaId;
  /**
   * 缩略图的媒体ID
   */
  private String thumbMediaId;
  /**
   * 图文消息/视频消息/音乐消息的标题
   */
  private String title;
  /**
   * 图文消息/视频消息/音乐消息的描述
   */
  private String description;
  /**
   * 音乐链接
   */
  private String musicUrl;
  /**
   * 高品质音乐链接，wifi环境优先使用该链接播放音乐
   */
  private String hqMusicUrl;
  /**
   * 图文消息，一个图文消息支持1到10个图文
   */
  private List<WxArticle> articles = new ArrayList<WxArticle>();

  /**
   * 这个方法的作用是获取普通用户openid
   * @return 返回String类型变量
     */
  public String getToUser() {
    return toUser;
  }

  /**
   * 这个方法的作用是设置普通用户openid
   * @param toUser 普通用户openid
     */
  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  /**
   * 这个方法的作用是获取消息类型
   * @return 返回String类型变量
     */
  public String getMsgType() {
    return msgType;
  }
  
  /**
   * 这个方法的作用是设置消息类型
   * <pre>
   * 请使用
   * {@link WxConsts#CUSTOM_MSG_TEXT}
   * {@link WxConsts#CUSTOM_MSG_IMAGE}
   * {@link WxConsts#CUSTOM_MSG_VOICE}
   * {@link WxConsts#CUSTOM_MSG_MUSIC}
   * {@link WxConsts#CUSTOM_MSG_VIDEO}
   * {@link WxConsts#CUSTOM_MSG_NEWS}
   * </pre>
   * @param msgType 返回消息类型
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  /**
   * 这个方法的作用是获取文本消息内容
   * @return 返回String类型变量
     */
  public String getContent() {
    return content;
  }

  /**
   * 这个方法的作用是设置文本消息内容
   * @param content 文本消息内容
     */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 这个方法的作用是获取媒体ID
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置媒体ID
   * @param mediaId 媒体ID
     */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  /**
   * 这个方法的作用是获取缩略图的媒体ID
   * @return 返回String类型变量
     */
  public String getThumbMediaId() {
    return thumbMediaId;
  }

  /**
   * 这个方法的作用是设置缩略图的媒体ID
   * @param thumbMediaId 缩略图的媒体ID
     */
  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

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
   * 这个方法的作用是获取音乐链接
   * @return 返回String类型变量
     */
  public String getMusicUrl() {
    return musicUrl;
  }

  /**
   * 这个方法的作用是设置音乐链接
   * @param musicUrl 音乐链接
     */
  public void setMusicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
  }

  /**
   * 这个方法的作用是获取高品质音乐链接
   * @return 返回String类型变量
     */
  public String getHqMusicUrl() {
    return hqMusicUrl;
  }

  /**
   * 这个方法的作用是设置高品质音乐链接
   * @param hqMusicUrl 高品质音乐链接
     */
  public void setHqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
  }

  /**
   * 这个方法的作用是获取图文消息
   * @return 返回图文列表
     */
  public List<WxArticle> getArticles() {
    return articles;
  }

  /**
   * 这个方法的作用是设置图文消息
   * @param articles 图文消息
     */
  public void setArticles(List<WxArticle> articles) {
    this.articles = articles;
  }

  /**
   * 这个方法的作用是将数据转化成Json格式
   * @return 返回String类型变量
     */
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
  
  /**
   * 图文实体
   * @author Administrator
   */
  public static class WxArticle {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 链接
     */
    private String url;
    /**
     * 图片链接
     */
    private String picUrl;

    /**
     * 这个方法的作用是获取图文标题
     * @return 返回String类型变量
       */
    public String getTitle() {
      return title;
    }

    /**
     * 这个方法的作用是设置图文标题
     * @param title 图文标题
       */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * 这个方法的作用是获取图文描述
     * @return 返回String类型变量
       */
    public String getDescription() {
      return description;
    }

    /**
     * 这个方法的作用是设置图文描述
     * @param description 图文描述
       */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * 这个方法的作用是获取图文链接
     * @return 返回String类型变量
       */
    public String getUrl() {
      return url;
    }

    /**
     * 这个方法的作用是设置图文链接
     * @param url 图文链接
       */
    public void setUrl(String url) {
      this.url = url;
    }

    /**
     * 这个方法的作用是获取图片链接
     * @return 返回String类型变量
       */
    public String getPicUrl() {
      return picUrl;
    }

    /**
     * 这个方法的作用是设置图片链接
     * @param picUrl 图片链接
       */
    public void setPicUrl(String picUrl) {
      this.picUrl = picUrl;
    }
    
  }
  
  /**
   * 获得文本消息builder
   * @return 返回文本消息builder
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得图片消息builder
   * @return 返回 图片消息builder
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder
   * @return 返回语音消息builder
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }
  
  /**
   * 获得视频消息builder
   * @return 返回视频消息builder
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }
  
  /**
   * 获得音乐消息builder
   * @return 返回音乐消息builder
   */
  public static MusicBuilder MUSIC() {
    return new MusicBuilder();
  }
  
  /**
   * 获得图文消息builder
   * @return 返回图文消息builder
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
  
}
