package com.huiju.weixin.mp.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.mp.bean.WxMpXmlOutNewsMessage;

/**
 * 图文消息builder
 * @author chanjarster
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxMpXmlOutNewsMessage> {
  /**
   * 图文消息列表
   */
  protected final List<WxMpXmlOutNewsMessage.Item> articles = new ArrayList<WxMpXmlOutNewsMessage.Item>();
  
  /**
   * 添加图文消息
   * @param item 图文消息
   * @return 返回NewsBuilder
   */
  public NewsBuilder addArticle(WxMpXmlOutNewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }
  
  /**
   * 消息创建
   */
  public WxMpXmlOutNewsMessage build() {
    WxMpXmlOutNewsMessage m = new WxMpXmlOutNewsMessage();
    for(WxMpXmlOutNewsMessage.Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}
