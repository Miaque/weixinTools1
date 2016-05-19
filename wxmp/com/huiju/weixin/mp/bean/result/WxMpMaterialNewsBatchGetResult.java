package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.huiju.weixin.mp.bean.WxMpMaterialNews;

/**
 * 图文素材返回结果
 * @author Administrator
 */
public class WxMpMaterialNewsBatchGetResult implements Serializable {

  private int totalCount;//该类型的素材的总数
  private int itemCount;//本次调用获取的素材的数量
  private List<WxMaterialNewsBatchGetNewsItem> items;

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

  public List<WxMaterialNewsBatchGetNewsItem> getItems() {
    return items;
  }

  public void setItems(List<WxMaterialNewsBatchGetNewsItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "WxMpMaterialNewsBatchGetResult [" + "totalCount=" + totalCount + ", itemCount=" + itemCount + ", items=" + items + "]";
  }

  /**
   * 图文实体
   * @author Administrator
   */
  public static class WxMaterialNewsBatchGetNewsItem {
    private String mediaId;//文件ID
    private Date updateTime;//本次调用获取的素材的数量
    private WxMpMaterialNews content;//图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS

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

    public WxMpMaterialNews getContent() {
      return content;
    }

    public void setContent(WxMpMaterialNews content) {
      this.content = content;
    }

    @Override
    public String toString() {
      return "WxMaterialNewsBatchGetNewsItem [" + "mediaId=" + mediaId + ", updateTime=" + updateTime + ", content=" + content + "]";
    }
  }
}
