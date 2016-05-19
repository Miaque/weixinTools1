package com.huiju.weixin.cp.bean;
import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的视频消息，xml格式
 */
@XStreamAlias("xml")
public class WxCpXmlOutVideoMessage extends WxCpXmlOutMessage {
  /**
   * 实例化video
   */
  @XStreamAlias("Video")
  protected final Video video = new Video();

  /**
   * 默认构造函数
   */
  public WxCpXmlOutVideoMessage() {
    this.msgType = WxConsts.XML_MSG_VIDEO;
  }

  /**
   * 这个方法的作用是获取文件id
   * @return 返回String类型变量
     */
  public String getMediaId() {
    return video.getMediaId();
  }

  /**
   * 这个方法的作用是设置文件id
   * @param mediaId 文件id
     */
  public void setMediaId(String mediaId) {
    video.setMediaId(mediaId);
  }

  /**
   * 这个方法的作用是获取标题
   * @return 返回String类型变量
     */
  public String getTitle() {
    return video.getTitle();
  }

  /**
   * 这个方法的作用是设置标题
   * @param title 标题
     */
  public void setTitle(String title) {
    video.setTitle(title);
  }

  /**
   * 这个方法的作用是获取描述
   * @return 返回String类型变量
     */
  public String getDescription() {
    return video.getDescription();
  }

  /**
   * 这个方法的作用是设置描述
   * @param description 描述
     */
  public void setDescription(String description) {
    video.setDescription(description);
  }

  /**
   * 视频类
   */
  @XStreamAlias("Video")
  public static class Video {
    
	  /**
	   * 文件id，可以调用上传临时素材或者永久素材接口获取,永久素材media_id必须由发消息的应用创建
	   */
    @XStreamAlias("MediaId")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String mediaId;

    /**
     * 视频标题
     */
    @XStreamAlias("Title")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String title;

    /**
     * 视频描述
     */
    @XStreamAlias("Description")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String description;

    /**
     * 这个方法的作用是获取文件id
     * @return 返回String类型变量
       */
    public String getMediaId() {
      return mediaId;
    }

    /**
     * 这个方法的作用是设置文件id
     * @param mediaId 文件id
       */
    public void setMediaId(String mediaId) {
      this.mediaId = mediaId;
    }

    /**
     * 这个方法的作用是获取视频标题
     * @return 返回String类型变量
       */
    public String getTitle() {
      return title;
    }

    /**
     * 这个方法的作用是设置视频标题
     * @param title 视频标题
       */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * 这个方法的作用是获取视频描述
     * @return 返回String类型变量
       */
    public String getDescription() {
      return description;
    }

    /**
     * 这个方法的作用是设置视频描述
     * @param description 视频描述
       */
    public void setDescription(String description) {
      this.description = description;
    }
    
  }

}
