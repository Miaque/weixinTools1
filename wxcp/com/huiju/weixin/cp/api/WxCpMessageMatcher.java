package com.huiju.weixin.cp.api;

import com.huiju.weixin.cp.bean.WxCpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxCpMessageMatcher {

  /**
   * 消息是否匹配某种模式
   * @param message    微信推送过来的消息
   * @return true:匹配；false：不匹配
   */
  public boolean match(WxCpXmlMessage message);

}
