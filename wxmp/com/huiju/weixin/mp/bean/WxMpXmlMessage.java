package com.huiju.weixin.mp.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.api.WxMpConfigStorage;
import com.huiju.weixin.mp.util.crypto.WxMpCryptUtil;
import com.huiju.weixin.mp.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;

import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 *
 * @author chanjarster
 */
@XStreamAlias("xml")
public class WxMpXmlMessage implements Serializable {

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  /**
   * 接收方微信号
   */
  @XStreamAlias("ToUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String toUserName;

  /**
   * 发送方微信号，若为普通用户，则是一个OpenID
   */
  @XStreamAlias("FromUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String fromUserName;

  /**
   * 消息创建时间
   */
  @XStreamAlias("CreateTime")
  private Long createTime;

  /**
   * 消息类型，link
   */
  @XStreamAlias("MsgType")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String msgType;

  /**
   * 消息文本内容
   */
  @XStreamAlias("Content")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String content;

  /**
   * 消息id，64位整型
   */
  @XStreamAlias("MsgId")
  private Long msgId;

  /**
   * 图片链接
   */
  @XStreamAlias("PicUrl")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String picUrl;

  /**
   * 媒体id，可以调用多媒体文件下载接口拉取数据
   */
  @XStreamAlias("MediaId")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String mediaId;

  /**
   * 语音格式，如amr，speex等
   */
  @XStreamAlias("Format")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String format;

  /**
   * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
   */
  @XStreamAlias("ThumbMediaId")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String thumbMediaId;

  /**
   * 地理位置维度
   */
  @XStreamAlias("Location_X")
  private Double locationX;

  /**
   * 地理位置经度
   */
  @XStreamAlias("Location_Y")
  private Double locationY;

  /**
   * 地图缩放大小
   */
  @XStreamAlias("Scale")
  private Double scale;

  /**
   * 地理位置信息
   */
  @XStreamAlias("Label")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String label;

  /**
   * 消息标题
   */
  @XStreamAlias("Title")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String title;

  /**
   * 消息描述
   */
  @XStreamAlias("Description")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String description;

  /**
   * 消息链接
   */
  @XStreamAlias("Url")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String url;

  /**
   * 事件类型，subscribe
   */
  @XStreamAlias("Event")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String event;

  /**
   * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
   */
  @XStreamAlias("EventKey")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String eventKey;

  /**
   * 二维码的ticket，可用来换取二维码图片
   */
  @XStreamAlias("Ticket")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String ticket;

  /**
   * 地理位置纬度
   */
  @XStreamAlias("Latitude")
  private Double latitude;

  /**
   * 地理位置经度
   */
  @XStreamAlias("Longitude")
  private Double longitude;

  /**
   * 地理位置精度
   */
  @XStreamAlias("Precision")
  private Double precision;

  @XStreamAlias("Recognition")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String recognition;

  ///////////////////////////////////////
  // 群发消息返回的结果
  ///////////////////////////////////////
  /**
   * 群发的结果
   */
  @XStreamAlias("Status")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String status;
  /**
   * group_id下粉丝数；或者openid_list中的粉丝数
   */
  @XStreamAlias("TotalCount")
  private Integer totalCount;
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，filterCount = sentCount + errorCount
   */
  @XStreamAlias("FilterCount")
  private Integer filterCount;
  /**
   * 发送成功的粉丝数
   */
  @XStreamAlias("SentCount")
  private Integer sentCount;
  /**
   * 发送失败的粉丝数
   */
  @XStreamAlias("ErrorCount")
  private Integer errorCount;

  /**
   * 扫描信息
   */
  @XStreamAlias("ScanCodeInfo")
  private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();

  /**
   * 发送的图片信息
   */
  @XStreamAlias("SendPicsInfo")
  private SendPicsInfo sendPicsInfo = new SendPicsInfo();

  /**
   * 发送的位置信息
   */
  @XStreamAlias("SendLocationInfo")
  private SendLocationInfo sendLocationInfo = new SendLocationInfo();

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
   * <pre>
   * 当接受用户消息时，可能会获得以下值：
   * {@link WxConsts#XML_MSG_TEXT}
   * {@link WxConsts#XML_MSG_IMAGE}
   * {@link WxConsts#XML_MSG_VOICE}
   * {@link WxConsts#XML_MSG_VIDEO}
   * {@link WxConsts#XML_MSG_LOCATION}
   * {@link WxConsts#XML_MSG_LINK}
   * {@link WxConsts#XML_MSG_EVENT}
   * </pre>
   *
   * @return 返回String类型变量
   */
  public String getMsgType() {
    return msgType;
  }

  /**
   * <pre>
   * 当发送消息的时候使用：
   * {@link WxConsts#XML_MSG_TEXT}
   * {@link WxConsts#XML_MSG_IMAGE}
   * {@link WxConsts#XML_MSG_VOICE}
   * {@link WxConsts#XML_MSG_VIDEO}
   * {@link WxConsts#XML_MSG_NEWS}
   * {@link WxConsts#XML_MSG_MUSIC}
   * </pre>
   *
   * @param msgType 消息类型
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }
  /**
   * 这个方法的作用是获取内容
   * @return 返回String类型变量
   */
  public String getContent() {
    return content;
  }
  /**
   * 这个方法的作用是设置内容
   * @param content 内容
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * 这个方法的作用是获取消息id
   * @return 返回Long类型变量
   */
  public Long getMsgId() {
    return msgId;
  }
  /**
   * 这个方法的作用是设置消息id
   * @param msgId 消息id
   */
  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }
  /**
   * 这个方法的作用是获取图片链接
   * @return 返回String类型变量
   */
  public String getPicUrl() {
    return picUrl;
  }
  /**
   * 这个方法的作用是设置图片链接
   * @param picUrl 图片链接
   */
  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }
  /**
   * 这个方法的作用是获取文件id
   * @return 返回String类型变量
   */
  public String getMediaId() {
    return mediaId;
  }
  /**
   * 这个方法的作用是设置文件id
   * @param mediaId 文件id
   */
  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }
  /**
   * 这个方法的作用是获取语音格式
   * @return 返回String类型变量
   */
  public String getFormat() {
    return format;
  }
  /**
   * 这个方法的作用是设置语音格式
   * @param format 语音格式
   */
  public void setFormat(String format) {
    this.format = format;
  }
  /**
   * 这个方法的作用是获取视频消息缩略图的媒体id
   * @return 返回String类型变量
   */
  public String getThumbMediaId() {
    return thumbMediaId;
  }
  /**
   * 这个方法的作用是设置视频消息缩略图的媒体id
   * @param thumbMediaId 视频消息缩略图的媒体id
   */
  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }
  /**
   * 这个方法的作用是获取地理位置纬度
   * @return 返回Double类型变量
   */
  public Double getLocationX() {
    return locationX;
  }
  /**
   * 这个方法的作用是设置地理位置纬度
   * @param locationX 地理位置纬度
   */
  public void setLocationX(Double locationX) {
    this.locationX = locationX;
  }
  /**
   * 这个方法的作用是获取地理位置经度
   * @return 返回Double类型变量
   */
  public Double getLocationY() {
    return locationY;
  }
  /**
   * 这个方法的作用是设置地理位置经度
   * @param locationY 地理位置经度
   */
  public void setLocationY(Double locationY) {
    this.locationY = locationY;
  }
  /**
   * 这个方法的作用是获取地图缩放大小
   * @return 返回Double类型变量
   */
  public Double getScale() {
    return scale;
  }
  /**
   * 这个方法的作用是设置地图缩放大小
   * @param scale 地图缩放大小
   */
  public void setScale(Double scale) {
    this.scale = scale;
  }
  /**
   * 这个方法的作用是获取地理位置信息
   * @return 返回String类型变量
   */
  public String getLabel() {
    return label;
  }
  /**
   * 这个方法的作用是设置地理位置信息
   * @param label 地理位置信息
   */
  public void setLabel(String label) {
    this.label = label;
  }
  /**
   * 这个方法的作用是获取title
   * @return 返回String类型变量
   */
  public String getTitle() {
    return title;
  }
  /**
   * 这个方法的作用是设置标题
   * @param title 标题
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * 这个方法的作用是获取描述
   * @return 返回String类型变量
   */
  public String getDescription() {
    return description;
  }
  /**
   * 这个方法的作用是设置描述
   * @param description 描述
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * 这个方法的作用是获取图片url
   * @return 返回String类型变量
   */
  public String getUrl() {
    return url;
  }
  /**
   * 这个方法的作用是设置图片url
   * @param url 图片url
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /**
   * 这个方法的作用是获取事件类型
   * @return 返回String类型变量
   */
  public String getEvent() {
    return event;
  }
  /**
   * 这个方法的作用是设置事件类型
   * @param event 事件类型
   */
  public void setEvent(String event) {
    this.event = event;
  }
  /**
   * 这个方法的作用是获取事件KEY值
   * @return 返回String类型变量
   */
  public String getEventKey() {
    return eventKey;
  }
  /**
   * 这个方法的作用是设置事件KEY值
   * @param eventKey 企业应用的id
   */
  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }
  /**
   * 这个方法的作用是获取二维码的ticket
   * @return 返回String类型变量
   */
  public String getTicket() {
    return ticket;
  }
  /**
   * 这个方法的作用是设置二维码的ticket
   * @param ticket 二维码的ticket
   */
  public void setTicket(String ticket) {
    this.ticket = ticket;
  }
  /**
   * 这个方法的作用是获取地理位置纬度
   * @return 返回Double类型变量
   */
  public Double getLatitude() {
    return latitude;
  }
  /**
   * 这个方法的作用是设置地理位置纬度
   * @param latitude 地理位置纬度
   */
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }
  /**
   * 这个方法的作用是获取地理位置经度
   * @return 返回Double类型变量
   */
  public Double getLongitude() {
    return longitude;
  }
  /**
   * 这个方法的作用是设置地理位置经度
   * @param longitude 地理位置经度
   */
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }
  /**
   * 这个方法的作用是获取地理位置精度
   * @return 返回Double类型变量
   */
  public Double getPrecision() {
    return precision;
  }
  /**
   * 这个方法的作用是设置地理位置精度
   * @param precision 地理位置精度
   */
  public void setPrecision(Double precision) {
    this.precision = precision;
  }
  /**
   * 这个方法的作用是获取语音识别
   * @return 返回String类型变量
   */
  public String getRecognition() {
    return recognition;
  }
  /**
   * 这个方法的作用是设置语音识别
   * @param recognition 语音识别
   */
  public void setRecognition(String recognition) {
    this.recognition = recognition;
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
   * 这个方法的作用是将xml格式的消息转化成WxCpXmlMessage类对象
   * @param xml xml格式的消息
   * @return WxCpXmlMessage类对象
   */
  public static WxMpXmlMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxMpXmlMessage.class, xml);
  }
  /**
   * 这个方法的作用是将输入流转化成WxCpXmlMessage类对象
   * @param is 输入流
   * @return WxCpXmlMessage类对象
   */
  public static WxMpXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxMpXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml 加密的xml格式的消息
   * @param wxMpConfigStorage 微信客户端配置存储
   * @param timestamp 时间戳
   * @param nonce 当前时间
   * @param msgSignature 消息签名
   * @return WxCpXmlMessage类对象
   */
  public static WxMpXmlMessage fromEncryptedXml(
      String encryptedXml,
      WxMpConfigStorage wxMpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    WxMpCryptUtil cryptUtil = new WxMpCryptUtil(wxMpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }
  /**
   *
   * @param is 输入流
   * @param wxMpConfigStorage 微信客户端配置存储
   * @param timestamp 时间戳
   * @param nonce 当前时间
   * @param msgSignature 消息签名
   * @return WxCpXmlMessage类对象
   */
  public static WxMpXmlMessage fromEncryptedXml(
      InputStream is,
      WxMpConfigStorage wxMpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxMpConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * 这个方法的作用是获取群发的结果
   * @return 返回String类型变量
   */
  public String getStatus() {
    return status;
  }
  /**
   * 这个方法的作用是设置群发的结果
   * @param status 群发的结果
   */
  public void setStatus(String status) {
    this.status = status;
  }
  /**
   * 这个方法的作用是获取group_id下粉丝数；或者openid_list中的粉丝数
   * @return 返回Integer类型变量
   */
  public Integer getTotalCount() {
    return totalCount;
  }
  /**
   * 这个方法的作用是设置group_id下粉丝数；或者openid_list中的粉丝数
   * @param totalCount group_id下粉丝数；或者openid_list中的粉丝数
   */
  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }
  /**
   * 这个方法的作用是获取过滤后准备发送的粉丝
   * @return 返回Integer类型变量
   */
  public Integer getFilterCount() {
    return filterCount;
  }
  /**
   * 这个方法的作用是设置过滤后准备发送的粉丝
   * @param filterCount 过滤后准备发送的粉丝
   */
  public void setFilterCount(Integer filterCount) {
    this.filterCount = filterCount;
  }
  /**
   * 这个方法的作用是获取发送成功的粉丝数
   * @return 返回Integer类型变量
   */
  public Integer getSentCount() {
    return sentCount;
  }
  /**
   * 这个方法的作用是设置发送成功的粉丝数
   * @param sentCount 发送成功的粉丝数
   */
  public void setSentCount(Integer sentCount) {
    this.sentCount = sentCount;
  }
  /**
   * 这个方法的作用是获取发送失败的粉丝数
   * @return 返回Integer类型变量
   */
  public Integer getErrorCount() {
    return errorCount;
  }
  /**
   * 这个方法的作用是设置发送失败的粉丝数
   * @param errorCount 发送失败的粉丝数
   */
  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }
  /**
   * 这个方法的作用是获取扫描信息
   * @return 返回扫描信息
   */
  public WxMpXmlMessage.ScanCodeInfo getScanCodeInfo() {
    return scanCodeInfo;
  }
  /**
   * 这个方法的作用是设置扫描信息
   * @param scanCodeInfo 扫描信息
   */
  public void setScanCodeInfo(WxMpXmlMessage.ScanCodeInfo scanCodeInfo) {
    this.scanCodeInfo = scanCodeInfo;
  }
  /**
   * 这个方法的作用是获取发送的图片信息
   * @return 返回发送的图片信息
   */
  public WxMpXmlMessage.SendPicsInfo getSendPicsInfo() {
    return sendPicsInfo;
  }
  /**
   * 这个方法的作用是设置发送的图片信息
   * @param sendPicsInfo 发送的图片信息
   */
  public void setSendPicsInfo(WxMpXmlMessage.SendPicsInfo sendPicsInfo) {
    this.sendPicsInfo = sendPicsInfo;
  }
  /**
   * 这个方法的作用是获取发送的位置信息
   * @return 返回发送的位置信息
   */
  public WxMpXmlMessage.SendLocationInfo getSendLocationInfo() {
    return sendLocationInfo;
  }
  /**
   * 这个方法的作用是设置发送的位置信息
   * @param sendLocationInfo 发送的位置信息
   */
  public void setSendLocationInfo(WxMpXmlMessage.SendLocationInfo sendLocationInfo) {
    this.sendLocationInfo = sendLocationInfo;
  }
  /**
   * 扫码信息类.
   */
  @XStreamAlias("ScanCodeInfo")
  public static class ScanCodeInfo {
    /**
     * 扫码类型
     */
    @XStreamAlias("ScanType")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scanType;
    /**
     * 扫码结果
     */
    @XStreamAlias("ScanResult")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scanResult;

    /**
     * 扫描类型，一般是qrcode
     * @return 返回String类型变量
     */
    public String getScanType() {

      return scanType;
    }
    /**
     * 这个方法的作用是设置扫码类型
     * @param scanType 扫码类型
     */
    public void setScanType(String scanType) {
      this.scanType = scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     * @return 返回String类型变量
     */
    public String getScanResult() {
      return scanResult;
    }
    /**
     * 这个方法的作用是设置扫码结果
     * @param scanResult 扫码结果
     */
    public void setScanResult(String scanResult) {
      this.scanResult = scanResult;
    }

  }
  /**
   * 发送图片信息类.
   */
  @XStreamAlias("SendPicsInfo")
  public static class SendPicsInfo {
    /**
     * 素材的数量
     */
    @XStreamAlias("Count")
    private Long count;
    /**
     * 图片列表
     */
    @XStreamAlias("PicList")
    protected final List<Item> picList = new ArrayList<Item>();
    /**
     * 这个方法的作用是获取素材的数量
     * @return 返回Long类型素材的数量
     */
    public Long getCount() {
      return count;
    }
    /**
     * 这个方法的作用是设置素材的数量
     * @param count 素材的数量
     */
    public void setCount(Long count) {
      this.count = count;
    }
    /**
     * 这个方法的作用是图片列表
     * @return 返回图片列表
     */
    public List<Item> getPicList() {
      return picList;
    }
    /**
     * 图片类型素材类.
     */
    @XStreamAlias("item")
    public static class Item {
      /**
       * 图片类型素材类.
       */
      @XStreamAlias("PicMd5Sum")
      @XStreamConverter(value=XStreamCDataConverter.class)
      private String picMd5Sum;
      /**
       * 这个方法的作用是获取图片加密
       * @return  返回加密的图片
       */
      public String getPicMd5Sum() {
        return picMd5Sum;
      }
      /**
       * 这个方法的作用是设置图片加密
       * @param picMd5Sum 图片加密
       */
      public void setPicMd5Sum(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
      }
    }
  }
  /**
   * 发送的位置信息类.
   */
  @XStreamAlias("SendLocationInfo")
  public static class SendLocationInfo {
    /**
     * 地理位置纬度
     */
    @XStreamAlias("Location_X")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String locationX;
    /**
     * 地理位置经度
     */
    @XStreamAlias("Location_Y")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String locationY;
    /**
     * 地图缩放大小
     */
    @XStreamAlias("Scale")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scale;
    /**
     * 地理位置信息
     */
    @XStreamAlias("Label")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String label;
    /**
     * <pre>
     * POI名称
     * <a href="http://wiki.open.qq.com/wiki/lbs/get_poi#1_.2Flbs.2Fget_poi">详情参考</>
     * </pre>
     */
    @XStreamAlias("Poiname")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String poiname;
    /**
     * 这个方法的作用是获取地理位置纬度
     * @return 返回String类型变量
     */
    public String getLocationX() {
      return locationX;
    }
    /**
     * 这个方法的作用是设置地理位置纬度
     * @param locationX 地理位置纬度
     */
    public void setLocationX(String locationX) {
      this.locationX = locationX;
    }
    /**
     * 这个方法的作用是获取地理位置经度
     * @return 返回String类型变量
     */
    public String getLocationY() {
      return locationY;
    }
    /**
     * 这个方法的作用是设置地理位置经度
     * @param locationY 地理位置经度
     */
    public void setLocationY(String locationY) {
      this.locationY = locationY;
    }
    /**
     * 这个方法的作用是获取地图缩放大小
     * @return 返回String类型变量
     */
    public String getScale() {
      return scale;
    }
    /**
     * 这个方法的作用是设置地图缩放大小
     * @param scale 地图缩放大小
     */
    public void setScale(String scale) {
      this.scale = scale;
    }
    /**
     * 这个方法的作用是获取地理位置信息
     * @return 返回String类型变量
     */
    public String getLabel() {
      return label;
    }
    /**
     * 这个方法的作用是设置地理位置信息
     * @param label 地理位置信息
     */
    public void setLabel(String label) {
      this.label = label;
    }
    /**
     * 这个方法的作用是获取POI名称
     * @return 返回String类型变量
     */
    public String getPoiname() {
      return poiname;
    }
    /**
     * 这个方法的作用是设置POI名称
     * @param poiname POI名称
     */
    public void setPoiname(String poiname) {
      this.poiname = poiname;
    }
  }

  @Override
  public String toString() {
    return "WxMpXmlMessage{" +
        "toUserName='" + toUserName + '\'' +
        ", fromUserName='" + fromUserName + '\'' +
        ", createTime=" + createTime +
        ", msgType='" + msgType + '\'' +
        ", content='" + content + '\'' +
        ", msgId=" + msgId +
        ", picUrl='" + picUrl + '\'' +
        ", mediaId='" + mediaId + '\'' +
        ", format='" + format + '\'' +
        ", thumbMediaId='" + thumbMediaId + '\'' +
        ", locationX=" + locationX +
        ", locationY=" + locationY +
        ", scale=" + scale +
        ", label='" + label + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", url='" + url + '\'' +
        ", event='" + event + '\'' +
        ", eventKey='" + eventKey + '\'' +
        ", ticket='" + ticket + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", precision=" + precision +
        ", recognition='" + recognition + '\'' +
        ", status='" + status + '\'' +
        ", totalCount=" + totalCount +
        ", filterCount=" + filterCount +
        ", sentCount=" + sentCount +
        ", errorCount=" + errorCount +
        ", scanCodeInfo=" + scanCodeInfo +
        ", sendPicsInfo=" + sendPicsInfo +
        ", sendLocationInfo=" + sendLocationInfo +
        '}';
  }
}
