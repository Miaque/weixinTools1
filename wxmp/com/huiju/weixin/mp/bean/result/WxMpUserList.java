package com.huiju.weixin.mp.bean.result;

import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 关注者列表/获取用户列表 结果
 * @author chanjarster
 *
 */
public class WxMpUserList {

  protected int total = -1;//关注该公众账号的总用户数
  protected int count = -1;//拉取的OPENID个数，最大值为10000
  protected List<String> openIds = new ArrayList<String>();
  protected String nextOpenId;//拉取列表的最后一个用户的OPENID
  
  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public List<String> getOpenIds() {
    return openIds;
  }
  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }
  public String getNextOpenId() {
    return nextOpenId;
  }
  public void setNextOpenId(String nextOpenId) {
    this.nextOpenId = nextOpenId;
  }
  
  /**
   * 返回结果数据转换
   * @param json
   * @return
   */
  public static WxMpUserList fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpUserList.class);
  }
}
