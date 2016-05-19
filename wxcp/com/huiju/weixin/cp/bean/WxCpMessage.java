package com.huiju.weixin.cp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.FileBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.ImageBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.MpNewsBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.NewsBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.TextBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.VideoBuilder;
import com.huiju.weixin.cp.bean.messagebuilder.VoiceBuilder;

/**
 * 消息
 * @author Daniel Qian
 *
 */
public class WxCpMessage implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 4156094300949904879L;
  /**
   * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
   */
  private String toUser;
  /**
   * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
   */
  private String toParty;
  /**
   * 标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数
   */
  private String toTag;
  /**
   * 企业应用的id，整型。可在应用的设置页面查看
   */
  private String agentId;
  /**
   * 消息类型，此时固定为：text
   */
  private String msgType;
  /**
   * 消息内容
   */
  private String content;
  /**
   * 文件id，可以调用上传临时素材或者永久素材接口获取,永久素材media_id必须由发消息的应用创建
   */
  private String mediaId;
  /**
   * 图文消息缩略图的media_id, 可以在上传多媒体文件接口中获得。此处thumb_media_id即上传接口返回的media_id
   */
  private String thumbMediaId;
  /**
   * 标题
   */
  private String title;
  /**
   * 描述
   */
  private String description;
  /**
   * 音频链接
   */
  private String musicUrl;
  /**
   * 
   */
  private String hqMusicUrl;
  /**
   * 表示是否是保密消息，0表示否，1表示是，默认0
   */
  private String safe;
  /**
   * 图文消息，一个图文消息支持1到10个图文
   */
  private List<WxArticle> articles = new ArrayList<WxArticle>(); 
  /**
   * 多媒体消息 
   */
  private List<WxMpArticle> mpArticles = new ArrayList<WxMpArticle>();
  
  public String getToUser() {
    return toUser;
  }
  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getToParty() {
    return toParty;
  }

  public void setToParty(String toParty) {
    this.toParty = toParty;
  }

  public String getToTag() {
    return toTag;
  }

  public void setToTag(String toTag) {
    this.toTag = toTag;
  }

  public String getAgentId() {
    return agentId;
  }

  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  public String getMsgType() {
    return msgType;
  }
  
  public String getSafe() {
    return safe;
  }

  public void setSafe(String safe) {
    this.safe = safe;
  }

  /**
   * <pre>
   * 请使用
   * {@link WxConsts#CUSTOM_MSG_TEXT}
   * {@link WxConsts#CUSTOM_MSG_IMAGE}
   * {@link WxConsts#CUSTOM_MSG_VOICE}
   * {@link WxConsts#CUSTOM_MSG_MUSIC}
   * {@link WxConsts#CUSTOM_MSG_VIDEO}
   * {@link WxConsts#CUSTOM_MSG_NEWS}
   * </pre>
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public String getMediaId() {
    return mediaId;
  }
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  public String getThumbMediaId() {
    return thumbMediaId;
  }
  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getMusicUrl() {
    return musicUrl;
  }
  public void setMusicUrl(String musicUrl) {
    this.musicUrl = musicUrl;
  }
  public String getHqMusicUrl() {
    return hqMusicUrl;
  }
  public void setHqMusicUrl(String hqMusicUrl) {
    this.hqMusicUrl = hqMusicUrl;
  }
  public List<WxArticle> getArticles() {
    return articles;
  }
  public void setArticles(List<WxArticle> articles) {
    this.articles = articles;
  } 
  public List<WxMpArticle> getMpArticles() {
	return mpArticles;
  }
  public void setMpArticles(List<WxMpArticle> mpArticles) {
	 this.mpArticles = mpArticles;
  }
  /**
   * 对象转json字符
   * @return
   */
  public String toJson() {
	 return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }
	  
  /**
   * 图文消息实体
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
     * 点击后链接路径
     */
    private String url;
    /**
     * 图片链接
     */
    private String picUrl;
    
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }
    public String getPicUrl() {
      return picUrl;
    }
    public void setPicUrl(String picUrl) {
      this.picUrl = picUrl;
    }
    
  } 
  
  /**
   * 多媒体消息实体
   * @author Administrator
   */
  public static class WxMpArticle {
    /**
     * 标题
     */
    private String title;    
    /**
     * 图文消息缩略图的media_id, 可以在上传多媒体文件接口中获得。
     * 此处thumb_media_id即上传接口返回的media_id 
     */
    private String thumbMediaId;
    /**
     * 图文消息的作者 
     */
    private String author;
    /**
     * 图文消息点击“阅读原文”之后的页面链接 
     */
    private String contentSourceUrl;
    /**
     * 图文消息的内容，支持html标签 
     */
    private String content;
    /**
     * 	图文消息的描述 
     */
    private String digest; 
    /**
     *是否显示封面，1为显示，0为不显示 
     */
    private String showCoverPic;
    
    public String getTitle() {
      return title;
    }
    public void setTitle(String title) {
      this.title = title;
    }
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContentSourceUrl() {
		return contentSourceUrl;
	}
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShowCoverPic() {
		return showCoverPic;
	}
	public void setShowCoverPic(String showCoverPic) {
		this.showCoverPic = showCoverPic;
	} 
    
  } 
  
  
  /**
   * 获得文本消息builder
   * @return
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得图片消息builder
   * @return
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder
   * @return
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }
  
  /**
   * 获得视频消息builder
   * @return
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }
  
  /**
   * 获得图文消息builder
   * @return
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
  
  /**
   * 获得多媒体消息builder
   * @return
   */
  public static MpNewsBuilder MPNEWS() {
    return new MpNewsBuilder();
  }

  /**
   * 获得文件消息builder
   * @return
   */
  public static FileBuilder FILE() {
    return new FileBuilder();
  }
  
}
