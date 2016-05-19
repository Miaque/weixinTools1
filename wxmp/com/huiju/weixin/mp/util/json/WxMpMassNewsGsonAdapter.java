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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.huiju.weixin.mp.bean.WxMpMassNews;

public class WxMpMassNewsGsonAdapter implements JsonSerializer<WxMpMassNews>, JsonDeserializer<WxMpMassNews> {

	  /**
	   * 数据类型转换：WxMpMassNews --> JsonElement
	   */
  public JsonElement serialize(WxMpMassNews message, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject newsJson = new JsonObject();

    JsonArray articleJsonArray = new JsonArray();
    for (WxMpMassNews.WxMpMassNewsArticle article : message.getArticles()) {
      JsonObject articleJson = WxMpGsonBuilder.create().toJsonTree(article, WxMpMassNews.WxMpMassNewsArticle.class).getAsJsonObject();
      articleJsonArray.add(articleJson);
    }
    newsJson.add("articles", articleJsonArray);

    return newsJson;
  }
  
  /**
   * 数据类型转换：JsonElement --> WxMpMassNews
   */
  public WxMpMassNews deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    WxMpMassNews wxMpMassNews = new WxMpMassNews();
    JsonObject json = jsonElement.getAsJsonObject();
    if (json.get("media_id") != null && !json.get("media_id").isJsonNull()) {
      JsonArray articles = json.getAsJsonArray("articles");
      for (JsonElement article1 : articles) {
        JsonObject articleInfo = article1.getAsJsonObject();
        WxMpMassNews.WxMpMassNewsArticle article = WxMpGsonBuilder.create().fromJson(articleInfo, WxMpMassNews.WxMpMassNewsArticle.class);
        wxMpMassNews.addArticle(article);
      }
    }
    return wxMpMassNews;
  }
}
