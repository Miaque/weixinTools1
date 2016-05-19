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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.huiju.weixin.common.util.json.GsonHelper;
import com.huiju.weixin.cp.bean.WxCpTag;

/**
 * @author Daniel Qian
 */
public class WxCpTagGsonAdapter implements JsonSerializer<WxCpTag>, JsonDeserializer<WxCpTag> {

	/**
	 * 序列化：将 WxCpTag tag 转成    JsonElement
	 */
  public JsonElement serialize(WxCpTag tag, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject o = new JsonObject();
    o.addProperty("tagid", tag.getId());
    o.addProperty("tagname", tag.getName());
    return o;
  }

  /**
   * 反序列化：将 JsonElement json 转成 WxCpTag 实体
   */
  public WxCpTag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    return new WxCpTag(GsonHelper.getString(jsonObject, "tagid"), GsonHelper.getString(jsonObject, "tagname"));
  }

}
