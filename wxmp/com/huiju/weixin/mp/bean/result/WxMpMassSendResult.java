package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * <pre>
 * 群发消息一发送就返回的结果
 * 
 * 真正的群发消息是否发送成功要看
 * http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C
 * 
 * </pre>
 * @author chanjarster
 *
 */
public class WxMpMassSendResult implements Serializable {

  /**
   * 错误码
   */
  private String errorCode;
  
  /**
   * 错误消息
   */
  private String errorMsg;
  
  /**
   * 消息发送任务的ID
   */
  private String msgId;

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getMsgId() {
    return msgId;
  }

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }
  
  /**
   * 返回结果数据转换  
   * @param json
   * @return
   */
  public static WxMpMassSendResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMassSendResult.class);
  }

  @Override
  public String toString() {
    return "WxMassSendResult [errcode=" + errorCode + ", errmsg=" + errorMsg + ", msg_id=" + msgId + "]";
  }
  
}
