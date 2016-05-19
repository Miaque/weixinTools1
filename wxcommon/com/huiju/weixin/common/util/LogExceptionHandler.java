package com.huiju.weixin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huiju.weixin.common.api.WxErrorExceptionHandler;
import com.huiju.weixin.common.bean.exception.WxErrorException;


public class LogExceptionHandler implements WxErrorExceptionHandler {

  private Logger log = LoggerFactory.getLogger(WxErrorExceptionHandler.class);

  @Override
  public void handle(WxErrorException e) {

    log.error("Error happens", e);

  }

}
