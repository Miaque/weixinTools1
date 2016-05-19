package com.huiju.weixin.mp.bean.custombuilder;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxMpCustomMessage m = WxMpCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author chanjarster
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {
  /**
   * 图文消息列表
   */
  private List<WxMpCustomMessage.WxArticle> articles = new ArrayList<WxMpCustomMessage.WxArticle>();
  /**
   * 默认构造函数
   */
  public NewsBuilder() {
    this.msgType = WxConsts.CUSTOM_MSG_NEWS;
  }

  /**
   * 添加图文消息，一个图文消息支持1到10个图文
   * @param article 图文消息
   * @return 返回NewsBuilder
   */
  public NewsBuilder addArticle(WxMpCustomMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxMpCustomMessage
   */
  public WxMpCustomMessage build() {
    WxMpCustomMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
