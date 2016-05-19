package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 微信用户分组
 * @author chanjarster
 *
 */
public class WxMpGroup implements Serializable {

  private static final long serialVersionUID = 4833590082209071002L;
  /**
   * 分组id，由微信分配
   */
  private long id = -1;
  /**
   * 分组名字（30个字符以内）,UTF8编码
   */
  private String name;
  /**
   * 分组内用户数量
   */
  private long count;

  /**
   * 这个方法的作用是获取分组id
   * @return 返回Long类型变量
     */
  public long getId() {
    return id;
  }

  /**
   * 这个方法的作用是设置分组id
   * @param id 分组id
     */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * 这个方法的作用是获取分组分组名字
   * @return 返回String类型变量
     */
  public String getName() {
    return name;
  }

  /**
   * 这个方法的作用是设置分组名字
   * @param name 分组名字
     */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 这个方法的作用是获取分组内用户数量
   * @return 返回Long类型变量
     */
  public long getCount() {
    return count;
  }

  /**
   * 这个方法的作用是设置分组内用户数量
   * @param count
     */
  public void setCount(long count) {
    this.count = count;
  }

  /**
   * 这个方法的作用是将Json格式数据转化成WxMpGroup类对象
   * @param json 格式数据
   * @return 返回WxMpGroup类对象
     */
  public static WxMpGroup fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpGroup.class);
  }

  /**
   * 这个方法的作用是将数据转化成json格式
   *
   * @return
     */
  public String toJson() {
    return WxMpGsonBuilder.create().toJson(this);
  }

  @Override
  public String toString() {
    return "WxMpGroup [id=" + id + ", name=" + name + ", count=" + count + "]";
  }
  
}
