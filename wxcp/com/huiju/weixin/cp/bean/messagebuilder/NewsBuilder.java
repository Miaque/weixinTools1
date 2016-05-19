package com.huiju.weixin.cp.bean.messagebuilder;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.NEWS().addArticle(article).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder> {
  /**
   * 图文消息列表
   */
  private List<WxCpMessage.WxArticle> articles = new ArrayList<WxCpMessage.WxArticle>();

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
  public NewsBuilder addArticle(WxCpMessage.WxArticle article) {
    this.articles.add(article);
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setArticles(this.articles);
    return m;
  }
}
