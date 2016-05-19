/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.huiju.weixin.cp.util.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.cp.bean.WxCpKfMessage;

/**
 * 
 * @author zhengjf
 *
 */
public class WxCpKfMessageGsonAdapter implements JsonSerializer<WxCpKfMessage> {

  /**
   * 将数据  WxCpKfMessage message 转成  JsonElement
   */
  public JsonElement serialize(WxCpKfMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject(); 
    messageJson.addProperty("msgtype", message.getMsgType()); 
    JsonObject senderJson = new JsonObject();
    senderJson.addProperty("type", message.getSenderType());
    senderJson.addProperty("id", message.getSenderId()); 
    messageJson.add("sender", senderJson);
    
    JsonObject receiverJson = new JsonObject();
    receiverJson.addProperty("type", message.getReceiverType());
    receiverJson.addProperty("id", message.getReceiverId()); 
    messageJson.add("receiver", receiverJson);
     
    if (WxConsts.CUSTOM_MSG_TEXT.equals(message.getMsgType())) {
      JsonObject text = new JsonObject();
      text.addProperty("content", message.getContent());
      messageJson.add("text", text);
    }

    if (WxConsts.CUSTOM_MSG_IMAGE.equals(message.getMsgType())) {
      JsonObject image = new JsonObject();
      image.addProperty("media_id", message.getMediaId());
      messageJson.add("image", image);
    }

    if (WxConsts.CUSTOM_MSG_FILE.equals(message.getMsgType())) {
      JsonObject image = new JsonObject();
      image.addProperty("media_id", message.getMediaId());
      messageJson.add("file", image);
    }

    if (WxConsts.CUSTOM_MSG_VOICE.equals(message.getMsgType())) {
      JsonObject voice = new JsonObject();
      voice.addProperty("media_id", message.getMediaId());
      messageJson.add("voice", voice);
    }
 
    return messageJson;
  }

}
