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

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.huiju.weixin.common.util.json.GsonHelper;
import com.huiju.weixin.cp.bean.WxCpKfList;

/**
 * @author Daniel Qian
 */
public class WxCpKfListGsonAdapter implements JsonDeserializer<WxCpKfList>  {

  /**
   * 将 JsonElement json 转成  WxCpUser
   */
  public WxCpKfList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
	    JsonObject o = json.getAsJsonObject();
	    WxCpKfList kfList = new WxCpKfList();
	    kfList.setErrcode(GsonHelper.getString(o, "errcode"));
	    kfList.setErrmsg(GsonHelper.getString(o, "errmsg")); 
	
	    if (GsonHelper.isNotNull(o.get("internal"))) {
		      JsonArray userJsonElements = o.get("internal").getAsJsonObject().get("user").getAsJsonArray();
		      JsonArray partyJsonElements = o.get("internal").getAsJsonObject().get("party").getAsJsonArray();
		      JsonArray tagJsonElements = o.get("internal").getAsJsonObject().get("tag").getAsJsonArray();
		      String[] user = new String[userJsonElements.size()];
		      Integer[] party = new Integer[partyJsonElements.size()];
		      Integer[] tab = new Integer[tagJsonElements.size()]; 
		      
		      WxCpKfList.Internal itl = new WxCpKfList.Internal();
		      int i = 0; 
		      for (JsonElement userJsonElement : userJsonElements) {  
		    	  user[i++] = userJsonElement.getAsString();
		      }
		      itl.setUser(user);
		      
		      int j = 0;
		      for (JsonElement partyJsonElement : partyJsonElements) {    
		    	  party[j++] = partyJsonElement.getAsInt(); 
			  }
		      itl.setParty(party);
		      
		      int n = 0;
		      for (JsonElement tagJsonElement : tagJsonElements) {   
		    	  tab[n++] = tagJsonElement.getAsInt(); 
			  }
		      itl.setTag(tab);  
		      kfList.setInternal(itl);
	    }
	    
	    if (GsonHelper.isNotNull(o.get("external"))) {
		      JsonArray userJsonElements = o.get("external").getAsJsonObject().get("user").getAsJsonArray();
		      JsonArray partyJsonElements = o.get("external").getAsJsonObject().get("party").getAsJsonArray();
		      JsonArray tagJsonElements = o.get("external").getAsJsonObject().get("tag").getAsJsonArray();
		      String[] user = new String[userJsonElements.size()];
		      Integer[] party = new Integer[partyJsonElements.size()];
		      Integer[] tab = new Integer[tagJsonElements.size()]; 
		      
		      WxCpKfList.Internal itl = new WxCpKfList.Internal();
		      int i = 0; 
		      for (JsonElement userJsonElement : userJsonElements) {  
		    	  user[i++] = userJsonElement.getAsString();
		      }
		      itl.setUser(user);
		      
		      int j = 0;
		      for (JsonElement partyJsonElement : partyJsonElements) {    
		    	  party[j++] = partyJsonElement.getAsInt(); 
			  }
		      itl.setParty(party);
		      
		      int n = 0;
		      for (JsonElement tagJsonElement : tagJsonElements) {   
		    	  tab[n++] = tagJsonElement.getAsInt(); 
			  }
		      itl.setTag(tab);   
		      kfList.setExternal(itl);
	  }
	    
    return kfList;
  }
 

}
