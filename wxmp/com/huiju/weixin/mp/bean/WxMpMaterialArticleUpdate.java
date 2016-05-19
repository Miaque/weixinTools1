package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 修改永久图文素材类.
 */
public class WxMpMaterialArticleUpdate implements Serializable {
  /**
   * 修改的图文消息的id
   */
  private String mediaId;
  /**
   * 要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
   */
  private int index;
  /**
   * 永久图文素材
   */
  private WxMpMaterialNews.WxMpMaterialNewsArticle articles;

  /**
   * 这个方法的作用是获取修改的图文消息的id
   * @return
     */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 这个方法的作用是设置修改的图文消息的id
   * @param mediaId 修改的图文消息的id
     */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  /**
   * 这个方法的作用是获取要更新的文章在图文消息中的位置
   * @return 返回int类型变量
     */
  public int getIndex() {
    return index;
  }

  /**
   * 这个方法的作用是设置要更新的文章在图文消息中的位置
   * @param index 要更新的文章在图文消息中的位置
     */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * 这个方法的作用是获取永久图文素材
   * @return 返回永久图文素材
     */
  public WxMpMaterialNews.WxMpMaterialNewsArticle getArticles() {
    return articles;
  }

  /**
   * 这个方法的作用是设置获取永久图文素材
   * @param articles 获取永久图文素材
     */
  public void setArticles(WxMpMaterialNews.WxMpMaterialNewsArticle articles) {
    this.articles = articles;
  }

  /**
   * 这个方法的作用是将数据转化成Json格式数据
   * @return 返回String类型数据
     */
  public String toJson() {
    return WxMpGsonBuilder.create().toJson(this);
  }

  @Override
  public String toString() {
    return "WxMpMaterialArticleUpdate [" + "mediaId=" + mediaId + ", index=" + index + ", articles=" + articles + "]";
  }
}
