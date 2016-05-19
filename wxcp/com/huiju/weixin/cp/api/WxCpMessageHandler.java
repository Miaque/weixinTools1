package com.huiju.weixin.cp.api;

import java.util.Map;

import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.cp.bean.WxCpXmlMessage;
import com.huiju.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * 处理微信推送消息的处理器接口
 *
 * @author Daniel Qian
 */
public interface WxCpMessageHandler {

  /**
   * @param wxMessage      微信推送过来的消息
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param wxCpService	         微信API服务
   * @param sessionManager session管理器
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                                  Map<String, Object> context,
                                  WxCpService wxCpService,
                                  WxSessionManager sessionManager) throws WxErrorException;

}
