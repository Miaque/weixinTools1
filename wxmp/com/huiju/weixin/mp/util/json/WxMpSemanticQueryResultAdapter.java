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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.huiju.weixin.common.util.json.GsonHelper;
import com.huiju.weixin.mp.bean.result.WxMpSemanticQueryResult;

/**
 * 
 * @author Daniel Qian
 *
 */
public class WxMpSemanticQueryResultAdapter implements JsonDeserializer<WxMpSemanticQueryResult> {

  /**
   * 数据类型转换：JsonElement --> WxMpSemanticQueryResult
   */
  public WxMpSemanticQueryResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    WxMpSemanticQueryResult result = new WxMpSemanticQueryResult();
    JsonObject resultJsonObject = json.getAsJsonObject();

    if (GsonHelper.getString(resultJsonObject, "query") != null) {
      result.setQuery(GsonHelper.getString(resultJsonObject, "query"));
    }
    if (GsonHelper.getString(resultJsonObject, "type") != null) {
      result.setType(GsonHelper.getString(resultJsonObject, "type"));
    }
    if (resultJsonObject.get("semantic") != null) {
      result.setSemantic(resultJsonObject.get("semantic").toString());
    }
    if (resultJsonObject.get("result") != null) {
      result.setResult(resultJsonObject.get("result").toString());
    }
    if (GsonHelper.getString(resultJsonObject, "answer") != null) {
      result.setAnswer(GsonHelper.getString(resultJsonObject, "answer"));
    }
    if (GsonHelper.getString(resultJsonObject, "text") != null) {
      result.setText(GsonHelper.getString(resultJsonObject, "text"));
    }
    return result;
  }
  
}
