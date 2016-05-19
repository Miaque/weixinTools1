package com.huiju.weixin.common.api;

import com.huiju.weixin.common.bean.exception.WxErrorException;

/**
 * Wx异常处理接口.
 * WxErrorException处理器
 * @author Neu
 */
public interface WxErrorExceptionHandler {

  public void handle(WxErrorException e);

}
