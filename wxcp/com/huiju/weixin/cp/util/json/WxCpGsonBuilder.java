package com.huiju.weixin.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huiju.weixin.common.result.WxError;
import com.huiju.weixin.common.util.json.WxErrorAdapter;
import com.huiju.weixin.cp.bean.WxCpDepart;
import com.huiju.weixin.cp.bean.WxCpTag;
import com.huiju.weixin.cp.bean.WxCpUser;
import com.huiju.weixin.cp.bean.WxCpKfMessage;
import com.huiju.weixin.cp.bean.WxCpMessage;

public class WxCpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCpMessage.class, new WxCpMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpKfMessage.class, new WxCpKfListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
  }

  /**
   * 创建 GsonBuilder
   * @return
   */
  public static Gson create() {
    return INSTANCE.create();
  }

}
