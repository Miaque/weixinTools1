package com.huiju.weixin.common.bean.exception;

import com.huiju.weixin.common.result.WxError;

/**
 * 微信抛出异常类，继承于Exception.
 */
public class WxErrorException extends Exception {

  private static final long serialVersionUID = -6357149550353160810L;
  /**
   * 错误说明
   */
  private WxError error;

  /**
   * 默认构造函数
   * @param error 错误说明
   */
  public WxErrorException(WxError error) {
    super(error.toString());
    this.error = error;
  }

  /**
   * 这个方法的作用是获取错误
   * @return 返回WxError类型变量
   */
  public WxError getError() {
    return error;
  }

 
}
