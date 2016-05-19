package com.huiju.weixin.cp.bean;

import java.io.Serializable;

import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 标签类
 * Created by Daniel Qian
 */
public class WxCpTag implements Serializable {

  /**
   * 标签id，整型，指定此参数时新增的标签会生成对应的标签id，不指定时则以目前最大的id自增
   */
  private String id;
  /**
   * 标签名称，长度为1~64个字节，标签名不可与其他标签重名
   */
  private String name;

  /**
   * 默认构造函数
   */
  public WxCpTag() {
    super();
  }

  /**
   * 重载构造函数
   * @param id 标签id
   * @param name 标签名称
     */
  public WxCpTag(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  /**
   * 这个方法的作用是获取标签名称
   * @return 返回String类型变量
     */
  public String getName() {
    return name;
  }

  /**
   * 这个方法的作用是设置标签名称
   * @param name 标签名称
     */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 这个方法的作用是获取标签ID
   * @return 返回String类型变量
     */
  public String getId() {
    return id;
  }

  /**
   * 这个方法的作用是设置标签ID
   * @param id 标签ID
     */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 这个方法的作用是将Json格式数据转化为WxCpTag类对象
   * @param json 格式数据
   * @return WxCpTag类对象
     */
  public static WxCpTag fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTag.class);
  }

  /**
   * 将对象转化为Json格式数据
   * @return 返回String类型变量
     */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
