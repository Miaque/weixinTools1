package com.huiju.weixin.mp.bean;

import java.io.Serializable;

/**
 * 消息模板实体
 * @author Daniel Qian
 */
public class WxMpTemplateData implements Serializable {
  /**
   * 对应名称
   */
  private String name;
  /**
   * 对应字段值
   */
  private String value;
  /**
   * 对应值value的颜色
   */
  private String color;

  /**
   * 默认构造函数
   */
  public WxMpTemplateData() {
  }

  /**
   * 重载构造函数
   * @param name 对应名称
   * @param value 对应字段值
     */
  public WxMpTemplateData(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * 重载构造函数
   * @param name 对应名称
   * @param value 对应字段值
     * @param color 对应值value的颜色
     */
  public WxMpTemplateData(String name, String value, String color) {
    this.name = name;
    this.value = value;
    this.color = color;
  }

  /**
   * 这个方法的作用是获取对应名称
   * @return 返回String类型变量
     */
  public String getName() {
    return name;
  }

  /**
   * 这个方法的作用是设置对应名称
   * @param name 对应名称
     */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 这个方法的作用是获取对应字段值
   * @return 返回String类型变量
     */
  public String getValue() {
    return value;
  }

  /**
   * 这个方法的作用是设置对应字段值
   * @param value 对应字段值
     */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 这个方法的作用是获取对应值value的颜色
   * @return 返回String类型变量
     */
  public String getColor() {
    return color;
  }

  /**
   * 这个方法的作用是设置对应值value的颜色
   * @param color 对应值value的颜色
     */
  public void setColor(String color) {
    this.color = color;
  }

}
