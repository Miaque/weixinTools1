package com.huiju.weixin.common.result;

import java.io.Serializable;

import com.huiju.weixin.common.util.json.WxGsonBuilder;

/**
 * 微信错误码说明
 * http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 * @author Daniel Qian
 *
 */
public class WxError implements Serializable {

  private static final long serialVersionUID = 2468929393124957421L;
  /**
   * 错误编码
   */
  private int errorCode;
  /**
   * 错误信息
   */
  private String errorMsg;
  /**
   * Json格式数据
   */
  private String json;
  /**
   * 这个方法的作用是获取错误编码
   * @return 返回int类型变量
   */
  public int getErrorCode() {
    return errorCode;
  }
  /**
   * 这个方法的作用是设置错误编码
   * @param errorCode 错误编码
   */
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
  /**
   * 这个方法的作用是获取错误信息
   * @return 返回String类型变量
   */
  public String getErrorMsg() {
    return errorMsg;
  }
  /**
   * 这个方法的作用是设置错误信息
   * @param errorMsg 错误信息
   */
  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
  /**
   * 这个方法的作用是获取Json数据包
   * @return 返回String类型变量
   */
  public String getJson() {
    return json;
  }
  /**
   * 这个方法的作用是定义Json数据包
   * @param json Json数据包
   */
  public void setJson(String json) {
    this.json = json;
  }
  /**
   * 将Json格式数据转换成WxError对象类
   * @param json 格式数据
   * @return 返回WxError类型对象
   */
  public static WxError fromJson(String json) {
    WxError error = WxGsonBuilder.create().fromJson(json, WxError.class);
    return error;
  }

  @Override
  public String toString() {
    return "微信错误: errcode=" + errorCode + ", errmsg=" + errorMsg + "\njson:" + json;
  }

}
