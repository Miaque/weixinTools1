package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 获取素材列表的结果
 * @author Administrator
 */
public class WxMpMaterialFileBatchGetResult implements Serializable {

  /**
   * 该类型的素材的总数
   */
  private int totalCount;
  /**
   * 本次调用获取的素材的数量
   */
  private int itemCount;
  
  private List<WxMaterialFileBatchGetNewsItem> items;

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public List<WxMaterialFileBatchGetNewsItem> getItems() {
    return items;
  }

  public void setItems(List<WxMaterialFileBatchGetNewsItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "WxMpMaterialFileBatchGetResult [" + "totalCount=" + totalCount + ", itemCount=" + itemCount + ", items=" + items + "]";
  }

  /**
   * 文件素材实体
   * @author Administrator
   */
  public static class WxMaterialFileBatchGetNewsItem {
    private String mediaId;//文件ID
    private Date updateTime;//这篇图文消息素材的最后更新时间
    private String name;//文件名称
    private String url;//图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL

    public String getMediaId() {
      return mediaId;
    }

    public void setMediaId(String mediaId) {
      this.mediaId = mediaId;
    }

    public Date getUpdateTime() {
      return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    @Override
    public String toString() {
      return "WxMaterialFileBatchGetNewsItem [" + "mediaId=" + mediaId + ", updateTime=" + updateTime + ", name=" + name + ", url=" + url + "]";
    }
  }
}
