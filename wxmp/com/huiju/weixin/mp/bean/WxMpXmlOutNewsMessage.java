package com.huiju.weixin.mp.bean;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
/**
 * 同步回复给用户的图文消息，xml格式
 */
@XStreamAlias("xml")
public class WxMpXmlOutNewsMessage extends WxMpXmlOutMessage {
  /**
   * 图文消息数量
   */
  @XStreamAlias("ArticleCount")
  protected int articleCount;
  /**
   * 图文消息，一个图文消息支持1到10个图文
   */
  @XStreamAlias("Articles")
  protected final List<Item> articles = new ArrayList<Item>();
  /**
   * 默认构造函数
   */
  public WxMpXmlOutNewsMessage() {
    this.msgType = WxConsts.XML_MSG_NEWS;
  }
  /**
   * 这个方法的作用是获取图文消息数量
   * @return 返回int类型变量
   */
  public int getArticleCount() {
    return articleCount;
  }
  /**
   * 这个方法的作用是新建图文消息
   * @param item 素材
   */
  public void addArticle(Item item) {
    this.articles.add(item);
    this.articleCount = this.articles.size();
  }
  /**
   * 这个方法的作用是获取图文消息列表
   * @return 返回文章数组
   */
  public List<Item> getArticles() {
    return articles;
  }

  /**
   * 图文消息类
   */
  @XStreamAlias("item")
  public static class Item {
    /**
     * 标题
     */
    @XStreamAlias("Title")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String Title;
    /**
     * 描述
     */
    @XStreamAlias("Description")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String Description;
    /**
     * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。如不填，在客户端不显示图片
     */
    @XStreamAlias("PicUrl")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String PicUrl;
    /**
     * 点击后跳转的链接。
     */
    @XStreamAlias("Url")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String Url;
    /**
     * 这个方法的作用是获取标题
     * @return 返回String类型变量
     */
    public String getTitle() {
      return Title;
    }
    /**
     * 这个方法的作用是设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
      Title = title;
    }
    /**
     * 这个方法的作用是获取描述
     * @return 返回String类型变量
     */
    public String getDescription() {
      return Description;
    }
    /**
     * 这个方法的作用是设置描述
     * @param description 描述
     */
    public void setDescription(String description) {
      Description = description;
    }
    /**
     * 这个方法的作用是获取图文消息的图片链接
     * @return 返回String类型变量
     */
    public String getPicUrl() {
      return PicUrl;
    }
    /**
     * 这个方法的作用是设置图文消息的图片链接
     * @param picUrl 图文消息的图片链接
     */
    public void setPicUrl(String picUrl) {
      PicUrl = picUrl;
    }
    /**
     * 这个方法的作用是获取点击后跳转的链接
     * @return 返回String类型变量
     */
    public String getUrl() {
      return Url;
    }
    /**
     * 这个方法的作用是设置点击后跳转的链接
     * @param url 点击后跳转的链接
     */
    public void setUrl(String url) {
      Url = url;
    }

  }


}
