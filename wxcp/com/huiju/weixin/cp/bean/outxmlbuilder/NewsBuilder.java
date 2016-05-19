package com.huiju.weixin.cp.bean.outxmlbuilder;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.cp.bean.WxCpXmlOutNewsMessage;

/**
 * 图文消息builder
 * @author Daniel Qian
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxCpXmlOutNewsMessage> {

  protected final List<WxCpXmlOutNewsMessage.Item> articles = new ArrayList<WxCpXmlOutNewsMessage.Item>();
  
  /**
   * 图文消息，一个图文消息支持1到10个图文
   * @param item 图文消息
   * @return 返回消息对象
   */
  public NewsBuilder addArticle(WxCpXmlOutNewsMessage.Item item) {
    this.articles.add(item);
    return this;
  }
  
  /**
   * 消息创建
   */
  public WxCpXmlOutNewsMessage build() {
    WxCpXmlOutNewsMessage m = new WxCpXmlOutNewsMessage();
    for(WxCpXmlOutNewsMessage.Item item : articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }
  
}
