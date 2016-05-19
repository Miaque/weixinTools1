package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.huiju.weixin.mp.api.WxMpConfigStorage;
import com.huiju.weixin.mp.bean.outxmlbuilder.VoiceBuilder;
import com.huiju.weixin.mp.util.crypto.WxMpCryptUtil;
import com.huiju.weixin.mp.bean.outxmlbuilder.ImageBuilder;
import com.huiju.weixin.mp.bean.outxmlbuilder.MusicBuilder;
import com.huiju.weixin.mp.bean.outxmlbuilder.NewsBuilder;
import com.huiju.weixin.mp.bean.outxmlbuilder.TextBuilder;
import com.huiju.weixin.mp.bean.outxmlbuilder.TransferCustomerServiceBuilder;
import com.huiju.weixin.mp.bean.outxmlbuilder.VideoBuilder;
import com.huiju.weixin.mp.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
/**
 * 同步回复给用户的其他类型消息，xml格式
 */
@XStreamAlias("xml")
public abstract class WxMpXmlOutMessage implements Serializable {
  /**
   * 接收方微信号
   */
  @XStreamAlias("ToUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String toUserName;
  /**
   * 发送方微信号，若为普通用户，则是一个OpenID
   */
  @XStreamAlias("FromUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String fromUserName;
  /**
   * 消息创建时间（整型）
   */
  @XStreamAlias("CreateTime")
  protected Long createTime;
  /**
   * 消息类型
   */
  @XStreamAlias("MsgType")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String msgType;
  /**
   * 这个方法的作用是获取接收方微信
   * @return 返回String类型变量
   */
  public String getToUserName() {
    return toUserName;
  }
  /**
   * 这个方法的作用是设置接收方微信
   * @param toUserName 接收方微信
   */
  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }
  /**
   * 这个方法的作用是获取发送方微信号，若为普通用户，则是一个OpenID
   * @return 返回String类型变量
   */
  public String getFromUserName() {
    return fromUserName;
  }
  /**
   * 这个方法的作用是设置发送方微信号，若为普通用户，则是一个OpenID
   * @param fromUserName 发送方微信号，若为普通用户，则是一个OpenID
   */
  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }
  /**
   * 这个方法的作用是获取消息创建时间
   * @return 返回Long类型变量
   */
  public Long getCreateTime() {
    return createTime;
  }
  /**
   * 这个方法的作用是设置消息创建时间
   * @param createTime 消息创建时间
   */
  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }
  /**
   * 这个方法的作用是获取消息类型
   * @return 返回String类型变量
   */
  public String getMsgType() {
    return msgType;
  }
  /**
   * 这个方法的作用是设置消息类型
   * @param msgType 消息类型
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
  /**
   * 这个方法的作用是将对象转化为xml格式数据
   * @return 返回String类型xml格式数据
   */
  public String toXml() {
    return XStreamTransformer.toXml((Class) this.getClass(), this);
  }

  /**
   * 这个方法的作用是将wxCpConfigStorage类对象转换成加密的xml格式数据
   * @return 返回String类型xml格式加密数据
   */
  public String toEncryptedXml(WxMpConfigStorage wxMpConfigStorage) {
    String plainXml = toXml();
    WxMpCryptUtil pc = new WxMpCryptUtil(wxMpConfigStorage);
    return pc.encrypt(plainXml);
  }

  /**
   * 获得文本消息builder
   * @return 返回文本Builder
   */
  public static TextBuilder TEXT() {
    return new TextBuilder();
  }

  /**
   * 获得图片消息builder
   * @return 返回图片Builder
   */
  public static ImageBuilder IMAGE() {
    return new ImageBuilder();
  }

  /**
   * 获得语音消息builder
   * @return 返回语音Builder
   */
  public static VoiceBuilder VOICE() {
    return new VoiceBuilder();
  }

  /**
   * 获得视频消息builder
   * @return 返回视频Builder
   */
  public static VideoBuilder VIDEO() {
    return new VideoBuilder();
  }
  
  /**
   * 获得音乐消息builder
   * @return 音乐消息builder
   */
  public static MusicBuilder MUSIC() {
    return new MusicBuilder();
  }

  /**
   * 获得图文消息builder
   * @return 返回消息Builder
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
 /**
   * 获得客服消息builder
   *
   * @return 客服消息builder
   */
  public static TransferCustomerServiceBuilder TRANSFER_CUSTOMER_SERVICE() {
    return new TransferCustomerServiceBuilder();
  }
}
