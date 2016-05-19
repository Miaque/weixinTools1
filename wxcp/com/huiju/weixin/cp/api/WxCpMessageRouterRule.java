package com.huiju.weixin.cp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.huiju.weixin.common.api.WxErrorExceptionHandler;
import com.huiju.weixin.common.bean.exception.WxErrorException;
import com.huiju.weixin.common.session.WxSessionManager;
import com.huiju.weixin.cp.bean.WxCpXmlOutMessage;
import com.huiju.weixin.cp.bean.WxCpXmlMessage;

/**
 * 微信消息路由器规则
 * @author Administrator
 */
public class WxCpMessageRouterRule {

  private final WxCpMessageRouter routerBuilder;

  private boolean async = true;

  private String fromUser;

  private String msgType;

  private String event;

  private String eventKey;

  private String content;

  private String rContent;

  private WxCpMessageMatcher matcher;

  private boolean reEnter = false;

  private Integer agentId;

  private List<WxCpMessageHandler> handlers = new ArrayList<WxCpMessageHandler>();

  private List<WxCpMessageInterceptor> interceptors = new ArrayList<WxCpMessageInterceptor>();

  protected WxCpMessageRouterRule(WxCpMessageRouter routerBuilder) {
    this.routerBuilder = routerBuilder;
  }

  /**
   * 设置是否异步执行，默认是true
   *
   * @param async 是否异步标识
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule async(boolean async) {
    this.async = async;
    return this;
  }

  /**
   * 如果agentId匹配
   *
   * @param agentId  企业应用ID
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule agentId(Integer agentId) {
    this.agentId = agentId;
    return this;
  }

  /**
   * 如果msgType等于某值
   *
   * @param msgType 消息类型
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule msgType(String msgType) {
    this.msgType = msgType;
    return this;
  }

  /**
   * 如果event等于某值
   *
   * @param event 事件
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule event(String event) {
    this.event = event;
    return this;
  }

  /**
   * 如果eventKey等于某值
   *
   * @param eventKey   事件key值
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule eventKey(String eventKey) {
    this.eventKey = eventKey;
    return this;
  }

  /**
   * 如果content等于某值
   *
   * @param content 文本消息
   * @return WxCpMessageRouterRule
   */
  public WxCpMessageRouterRule content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 如果content匹配该正则表达式
   *
   * @param regex 正则表达式
   * @return
   */
  public WxCpMessageRouterRule rContent(String regex) {
    this.rContent = regex;
    return this;
  }

  /**
   * 如果fromUser等于某值
   *
   * @param fromUser  发送者
   * @return
   */
  public WxCpMessageRouterRule fromUser(String fromUser) {
    this.fromUser = fromUser;
    return this;
  }

  /**
   * 如果消息匹配某个matcher，用在用户需要自定义更复杂的匹配规则的时候
   *
   * @param matcher  消息匹配器
   * @return
   */
  public WxCpMessageRouterRule matcher(WxCpMessageMatcher matcher) {
    this.matcher = matcher;
    return this;
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor  消息拦截器
   * @return
   */
  public WxCpMessageRouterRule interceptor(WxCpMessageInterceptor interceptor) {
    return interceptor(interceptor, (WxCpMessageInterceptor[]) null);
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor 消息拦截器
   * @param otherInterceptors  消息拦截器集合
   * @return
   */
  public WxCpMessageRouterRule interceptor(WxCpMessageInterceptor interceptor, WxCpMessageInterceptor... otherInterceptors) {
    this.interceptors.add(interceptor);
    if (otherInterceptors != null && otherInterceptors.length > 0) {
      for (WxCpMessageInterceptor i : otherInterceptors) {
        this.interceptors.add(i);
      }
    }
    return this;
  }

  /**
   * 设置微信消息处理器
   *
   * @param handler 消息处理器
   * @return
   */
  public WxCpMessageRouterRule handler(WxCpMessageHandler handler) {
    return handler(handler, (WxCpMessageHandler[]) null);
  }

  /**
   * 设置微信消息处理器
   *
   * @param handler  消息处理器
   * @param otherHandlers  消息处理器集
   * @return
   */
  public WxCpMessageRouterRule handler(WxCpMessageHandler handler, WxCpMessageHandler... otherHandlers) {
    this.handlers.add(handler);
    if (otherHandlers != null && otherHandlers.length > 0) {
      for (WxCpMessageHandler i : otherHandlers) {
        this.handlers.add(i);
      }
    }
    return this;
  }

  /**
   * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
   *
   * @return
   */
  public WxCpMessageRouter end() {
    this.routerBuilder.getRules().add(this);
    return this.routerBuilder;
  }

  /**
   * 规则结束，但是消息还会进入其他规则
   *
   * @return
   */
  public WxCpMessageRouter next() {
    this.reEnter = true;
    return end();
  }

  protected boolean test(WxCpXmlMessage wxMessage) {
    return
        (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUserName()))
            &&
            (this.agentId == null || this.agentId.equals(wxMessage.getAgentId()))
            &&
            (this.msgType == null || this.msgType.equals(wxMessage.getMsgType()))
            &&
            (this.event == null || this.event.equals(wxMessage.getEvent()))
            &&
            (this.eventKey == null || this.eventKey.equals(wxMessage.getEventKey()))
            &&
            (this.content == null || this.content
                .equals(wxMessage.getContent() == null ? null : wxMessage.getContent().trim()))
            &&
            (this.rContent == null || Pattern
                .matches(this.rContent, wxMessage.getContent() == null ? "" : wxMessage.getContent().trim()))
            &&
            (this.matcher == null || this.matcher.match(wxMessage))
        ;
  }

  /**
   * 处理微信推送过来的消息
   *
   * @param wxMessage 微信发送过来的消息
   * @return true 代表继续执行别的router，false 代表停止执行别的router
   */
  protected WxCpXmlOutMessage service(WxCpXmlMessage wxMessage,
                                      WxCpService wxCpService,
                                      WxSessionManager sessionManager,
                                      WxErrorExceptionHandler exceptionHandler) {

    try {

      Map<String, Object> context = new HashMap<String, Object>();
      // 如果拦截器不通过
      for (WxCpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxCpService, sessionManager)) {
          return null;
        }
      }

      // 交给handler处理
      WxCpXmlOutMessage res = null;
      for (WxCpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxCpService, sessionManager);
      }
      return res;

    } catch (WxErrorException e) {
      exceptionHandler.handle(e);
    }

    return null;

  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setrContent(String rContent) {
    this.rContent = rContent;
  }

  public void setMatcher(WxCpMessageMatcher matcher) {
    this.matcher = matcher;
  }

  public void setAgentId(Integer agentId) {
    this.agentId = agentId;
  }

  public void setHandlers(List<WxCpMessageHandler> handlers) {
    this.handlers = handlers;
  }

  public void setInterceptors(List<WxCpMessageInterceptor> interceptors) {
    this.interceptors = interceptors;
  }

  public boolean isAsync() {
    return async;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  public boolean isReEnter() {
    return reEnter;
  }

  public void setReEnter(boolean reEnter) {
    this.reEnter = reEnter;
  }

}
