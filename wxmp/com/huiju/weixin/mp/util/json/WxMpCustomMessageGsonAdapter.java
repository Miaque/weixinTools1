/*
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2013. All rights reserved.
 *
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 */
package com.huiju.weixin.mp.util.json;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;

public class WxMpCustomMessageGsonAdapter implements JsonSerializer<WxMpCustomMessage> {

  /**
   * 数据类型转换：WxMpCustomMessage --> JsonElement
   */
  public JsonElement serialize(WxMpCustomMessage message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject messageJson = new JsonObject();
    messageJson.addProperty("touser", message.getToUser());
    messageJson.addProperty("msgtype", message.getMsgType());
    
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

    if (WxConsts.CUSTOM_MSG_VOICE.equals(message.getMsgType())) {
      JsonObject voice = new JsonObject();
      voice.addProperty("media_id", message.getMediaId());
      messageJson.add("voice", voice);
    }

    if (WxConsts.CUSTOM_MSG_VIDEO.equals(message.getMsgType())) {
      JsonObject video = new JsonObject();
      video.addProperty("media_id", message.getMediaId());
      video.addProperty("thumb_media_id", message.getThumbMediaId());
      video.addProperty("title", message.getTitle());
      video.addProperty("description", message.getDescription());
      messageJson.add("video", video);
    }

    if (WxConsts.CUSTOM_MSG_MUSIC.equals(message.getMsgType())) {
      JsonObject music = new JsonObject();
      music.addProperty("title", message.getTitle());
      music.addProperty("description", message.getDescription());
      music.addProperty("thumb_media_id", message.getThumbMediaId());
      music.addProperty("musicurl", message.getMusicUrl());
      music.addProperty("hqmusicurl", message.getHqMusicUrl());
      messageJson.add("music", music);
    }
    
    if (WxConsts.CUSTOM_MSG_NEWS.equals(message.getMsgType())) {
      JsonObject newsJsonObject = new JsonObject();
      JsonArray articleJsonArray = new JsonArray();
      for (WxMpCustomMessage.WxArticle article : message.getArticles()) {
        JsonObject articleJson = new JsonObject();
        articleJson.addProperty("title", article.getTitle());
        articleJson.addProperty("description", article.getDescription());
        articleJson.addProperty("url", article.getUrl());
        articleJson.addProperty("picurl", article.getPicUrl());
        articleJsonArray.add(articleJson);
      }
      newsJsonObject.add("articles", articleJsonArray);
      messageJson.add("news", newsJsonObject);
    }
    
    return messageJson;
  }

}
