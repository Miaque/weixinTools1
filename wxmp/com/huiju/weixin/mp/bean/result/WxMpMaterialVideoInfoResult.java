package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 视频素材结果
 * @author Administrator
 */
public class WxMpMaterialVideoInfoResult implements Serializable {

  private String title;
  private String description;
  private String downUrl;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDownUrl() {
    return downUrl;
  }

  public void setDownUrl(String downUrl) {
    this.downUrl = downUrl;
  }


  /**
   * 返回结果转换
   * @param json
   * @return
   */
  public static WxMpMaterialVideoInfoResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialVideoInfoResult.class);
  }

  @Override
  public String toString() {
    return "WxMpMaterialVideoInfoResult [title=" + title + ", description=" + description + ", downUrl=" + downUrl + "]";
  }

}
