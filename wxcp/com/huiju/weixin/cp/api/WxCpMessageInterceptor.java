package com.huiju.weixin.cp.api;

import java.util.Map;

import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.cp.bean.WxCpXmlMessage;

/**
 * 微信消息拦截器，可以用来做验证
 *
 * @author Daniel Qian
 */
public interface WxCpMessageInterceptor {

  /**
   * 拦截微信消息
   *
   * @param wxMessage      微信推送过来的消息
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param wxCpService    微信API服务
   * @param sessionManager session管理器
   * @return true代表OK，false代表不OK
   */
  public boolean intercept(WxCpXmlMessage wxMessage,
                          Map<String, Object> context,
                          WxCpService wxCpService,
                          WxSessionManager sessionManager) throws WxErrorException;

}
