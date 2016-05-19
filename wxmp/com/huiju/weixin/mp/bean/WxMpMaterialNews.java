package com.huiju.weixin.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 图文素材
 * @author Administrator
 */
public class WxMpMaterialNews implements Serializable {
  /**
   * 图文素材列表
   */
  private List<WxMpMaterialNewsArticle> articles = new ArrayList<WxMpMaterialNewsArticle>();

  /**
   * 这个方法的作用是获取图文素材列表
   * @return 返回图文素材列表
     */
  public List<WxMpMaterialNewsArticle> getArticles() {
    return articles;
  }

  /**
   * 这个方法的作用是新建图文素材
   * @param article 图文素材
     */
  public void addArticle(WxMpMaterialNewsArticle article) {
    this.articles.add(article);
  }

  /**
   * 这个方法的作用是将数据转化成Json格式数据
   * @return 返回String类型数据
     */
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }

  /**
   * 这个方法的作用是判断图文素材是否为空
   * @return 返回true为空
     */
  public boolean isEmpty() {
    return articles == null || articles.isEmpty();
  }

  /**
   * <pre>
   * 群发图文消息article
   * 1. thumbMediaId  (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
   * 2. author          图文消息的作者
   * 3. title           (必填) 图文消息的标题
   * 4. contentSourceUrl 在图文消息页面点击“阅读原文”后的页面链接
   * 5. content (必填)  图文消息页面的内容，支持HTML标签
   * 6. digest          图文消息的描述
   * 7. showCoverPic  是否显示封面，true为显示，false为不显示
   * 8. url           点击图文消息跳转链接
   * </pre>
   *
   * @author chanjarster
   */
  public static class WxMpMaterialNewsArticle {
    /**
     * (必填) 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
     */
    private String thumbMediaId;
    /**
     * 图文消息的作者
     */
    private String author;
    /**
     * (必填) 图文消息的标题
     */
    private String title;
    /**
     * 在图文消息页面点击“阅读原文”后的页面链接
     */
    private String contentSourceUrl;
    /**
     * (必填) 图文消息页面的内容，支持HTML标签
     */
    private String content;
    /**
     * 图文消息的描述
     */
    private String digest;
    /**
     * 是否显示封面，true为显示，false为不显示
     */
    private boolean showCoverPic;

    /**
     * 点击图文消息跳转链接
    */
    private String url;

    /**
     * 这个方的作用是获取图文消息缩略图的media_id
     * @return 返回String类型变量
       */
    public String getThumbMediaId() {
      return thumbMediaId;
    }

    /**
     * 这个方的作用是设置图文消息缩略图的media_id
     * @param thumbMediaId 图文消息缩略图的media_id
       */
    public void setThumbMediaId(String thumbMediaId) {
      this.thumbMediaId = thumbMediaId;
    }

    /**
     * 这个方的作用是获取图文消息的作者
     * @return 返回String类型变量
       */
    public String getAuthor() {
      return author;
    }

    /**
     * 这个方的作用是设置图文消息的作者
     * @param author 图文消息的作者
       */
    public void setAuthor(String author) {
      this.author = author;
    }

    /**
     * 这个方的作用是获取图文消息的标题
     * @return 返回String类型变量
       */
    public String getTitle() {
      return title;
    }

    /**
     * 这个方的作用是设置图文消息的标题
     * @param title 图文消息的标题
       */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * 这个方的作用是获取在图文消息页面点击“阅读原文”后的页面链接
     * @return 返回String类型变量
       */
    public String getContentSourceUrl() {
      return contentSourceUrl;
    }

    /**
     * 这个方的作用是设置在图文消息页面点击“阅读原文”后的页面链接
     * @param contentSourceUrl 在图文消息页面点击“阅读原文”后的页面链接
       */
    public void setContentSourceUrl(String contentSourceUrl) {
      this.contentSourceUrl = contentSourceUrl;
    }

    /**
     * 这个方的作用是获取图文消息页面的内容
     * @return 返回String类型变量
       */
    public String getContent() {
      return content;
    }

    /**
     * 这个方的作用是设置图文消息页面的内容
     * @param content 图文消息页面的内容
       */
    public void setContent(String content) {
      this.content = content;
    }

    /**
     * 这个方的作用是获取图文消息的描述
     * @return 返回String类型变量
       */
    public String getDigest() {
      return digest;
    }

    /**
     * 这个方的作用是设置图文消息的描述
     * @param digest 图文消息的描述
       */
    public void setDigest(String digest) {
      this.digest = digest;
    }

    /**
     * 这个方的作用是是否显示封面
     * @return 返回true为显示，false为不显示
       */
    public boolean isShowCoverPic() {
      return showCoverPic;
    }

    /**
     * 这个方的作用是设置是否显示封面
     * @param showCoverPic 是否显示封面
       */
    public void setShowCoverPic(boolean showCoverPic) {
      this.showCoverPic = showCoverPic;
    }

    /**
     * 这个方的作用是获取点击图文消息跳转链接
     * @return 返回String类型变量
       */
    public String getUrl() {
        return url;
    }

    /**
     * 这个方的作用是设置点击图文消息跳转链接
     * @param url 点击图文消息跳转链接
       */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
      return "WxMpMassNewsArticle [" + "thumbMediaId=" + thumbMediaId + ", author=" + author + ", title=" + title +
          ", contentSourceUrl=" + contentSourceUrl + ", content=" + content + ", digest=" + digest +
          ", showCoverPic=" + showCoverPic +", url=" + url + "]";
    }
  }

  @Override
  public String toString() {
    return "WxMpMaterialNews [" + "articles=" + articles + "]";
  }
}
