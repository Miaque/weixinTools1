package com.huiju.weixin.cp.bean.outxmlbuilder;

import com.huiju.weixin.cp.bean.WxCpXmlOutMessage;

/**
 * 消息创建父类方法
 * @author Administrator
 */
public abstract class BaseBuilder<BuilderType, ValueType> {
  /**
   * 消息接受者
   */
  protected String toUserName;
  /**
   * 消息发送者
   */
  protected String fromUserName;
  
  /**
   * 消息接受者
   * @param touser 消息接受者
   * @return 返回BuilderType
   */
  public BuilderType toUser(String touser) {
    this.toUserName = touser;
    return (BuilderType) this;
  }
  
  /**
   * 消息发送者
   * @param fromusername 消息发送者
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
  
  public void setCommon(WxCpXmlOutMessage m) {
    m.setToUserName(this.toUserName);
    m.setFromUserName(this.fromUserName);
    m.setCreateTime(System.currentTimeMillis() / 1000l);
  }
  
}
