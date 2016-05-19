package com.huiju.weixin.cp.bean.messagebuilder;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpMessage;

/**
 * 图文消息builder
 * <pre>
 * 用法:
 * WxCustomMessage m = WxCustomMessage.MPNEWS().addArticle(mpArticle).toUser(...).build();
 * </pre>
 * @author Daniel Qian
 *
 */
public final class MpNewsBuilder extends BaseBuilder<MpNewsBuilder> {
  /**
   * 图文消息列表
   */
  private List<WxCpMessage.WxMpArticle> articles = new ArrayList<WxCpMessage.WxMpArticle>();

  /**
   * 默认构造函数
   */
  public MpNewsBuilder() {
    this.msgType = WxConsts.MASS_MSG_NEWS;
  }

  /**
   * 多媒体消息 
   * @param article 多媒体消息
   * @return 返回MpNewsBuilder
   */
  public MpNewsBuilder addArticle(WxCpMessage.WxMpArticle article) {
    this.articles.add(article);
    return this;
  }

  /**
   * 消息实体创建
   * @return 返回WxCpMessage
   */
  public WxCpMessage build() {
    WxCpMessage m = super.build();
    m.setMpArticles(this.articles);
    return m;
  }
}
