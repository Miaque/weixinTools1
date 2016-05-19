package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutMessage;

/**
 * 消息builder 父类
 * @author Administrator
 */
public abstract class BaseBuilder<BuilderType, ValueType> {
  /**
   * 接受者微信号
   */
  protected String toUserName;
  /**
   * 发送者微信号
   */
  protected String fromUserName;
  
  /**
   * 接受者微信号
   * @param touser 接受者微信号
   * @return 返回BuilderType
   */
  public BuilderType toUser(String touser) {
    this.toUserName = touser;
    return (BuilderType) this;
  }
  
  /**
   * 发送者微信号
   * @param fromusername 发送者微信号
   * @return 返回BuilderType
   */
  public BuilderType fromUser(String fromusername) {
    this.fromUserName = fromusername;
    return (BuilderType) this;
  }

  /**
   * 消息创建
   */
  public abstract ValueType build();

  /**
   *
   * @param m 同步
     */
  public void setCommon(WxMpXmlOutMessage m) {
    m.setToUserName(this.toUserName);
    m.setFromUserName(this.fromUserName);
    m.setCreateTime(System.currentTimeMillis() / 1000l);
  }
  
}
