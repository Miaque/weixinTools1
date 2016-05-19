package com.huiju.weixin.cp.bean;

import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.huiju.weixin.cp.bean.outxmlbuilder.TextBuilder;
import com.huiju.weixin.cp.bean.outxmlbuilder.VoiceBuilder;
import com.huiju.weixin.cp.api.WxCpConfigStorage;
import com.huiju.weixin.cp.bean.outxmlbuilder.ImageBuilder;
import com.huiju.weixin.cp.bean.outxmlbuilder.NewsBuilder;
import com.huiju.weixin.cp.bean.outxmlbuilder.VideoBuilder;
import com.huiju.weixin.cp.util.crypto.WxCpCryptUtil;
import com.huiju.weixin.cp.util.xml.XStreamTransformer;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的其他类型消息，xml格式
 */
@XStreamAlias("xml")
public abstract class WxCpXmlOutMessage {

	/**
	   * 企业号CorpID
	   */
  @XStreamAlias("ToUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  protected String toUserName;
  
  /**
   * 成员UserID
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
   * 这个方法的作用是获取企业号CorpID
   * @return 返回String类型变量
     */
  public String getToUserName() {
    return toUserName;
  }

  /**
   * 这个方法的作用是设置获取企业号CorpID
   * @param toUserName 获取企业号CorpID
     */
  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  /**
   * 这个方法的作用是获取成员UserID
   * @return 返回String类型变量
     */
  public String getFromUserName() {
    return fromUserName;
  }

  /**
   * 这个方法的作用是设置成员UserID
   * @param fromUserName 成员UserID
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
  protected String toXml() {
    return XStreamTransformer.toXml((Class)this.getClass(), this);
  }

  /**
   * 这个方法的作用是将wxCpConfigStorage类对象转换成加密的xml格式数据
   * @return 返回String类型xml格式加密数据
   */
  public String toEncryptedXml(WxCpConfigStorage wxCpConfigStorage) {
    String plainXml = toXml();
    WxCpCryptUtil pc = new WxCpCryptUtil(wxCpConfigStorage);
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
   * 获得图文消息builder
   * @return 返回消息Builder
   */
  public static NewsBuilder NEWS() {
    return new NewsBuilder();
  }
}
