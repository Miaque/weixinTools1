package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 新增永久素材的结果
 * @author Administrator
 */
public class WxMpMaterialUploadResult implements Serializable {

  private String mediaId;//新增的永久素材的media_id
  private String url;//新增的图片素材的图片URL（仅新增图片素材时会返回该字段）

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 返回结果转换
   * @param json
   * @return
   */
  public static WxMpMaterialUploadResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpMaterialUploadResult.class);
  }

  @Override
  public String toString() {
    return "WxMpMaterialUploadResult [media_id=" + mediaId + ", url=" + url + "]";
  }

}

