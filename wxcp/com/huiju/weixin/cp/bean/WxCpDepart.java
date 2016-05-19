package com.huiju.weixin.cp.bean;

import java.io.Serializable;

import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;

  /**
   * 微信部门
   *
   * @author Daniel Qian
   */
public class WxCpDepart implements Serializable {
	/** 
	 * 部门id，整型。指定时必须大于1，不指定时则自动生成
	 */
  private Integer id;
  /**
   * 部门名称,长度限制为1~64个字节，字符不能包括\:*?"<>｜
   */
  private String name;
  /**
   * 父部门id,根部门id为1
   */
  private Integer parentId;
  /**
   * 在父部门中的次序值。order值小的排序靠前。
   */
  private Integer order;

  /**
   * 这个方法的作用是获取部门ID
   * @return 返回Integer类型变量
   */
  public Integer getId() {
    return id;
  }

  /**
    * 这个方法的作用是设置部门ID
    * @param id 部门id，整型。指定时必须大于1，不指定时则自动生成
    */
  public void setId(Integer id) {
    this.id = id;
  }

    /**
     * 这个方法的作用是获取部门名称
     * @return 返回String类型变量
       */
  public String getName() {
    return name;
  }

    /**
     * 这个方法的作用是设置部门名称
     * @param name 部门名称
       */
  public void setName(String name) {
    this.name = name;
  }

    /**
     * 这个方法的作用是获取父部门ID
     * @return 返回Integer类型变量
       */
  public Integer getParentId() {
    return parentId;
  }

    /**
     * 这个方法的作用是设置父部门ID
     * @param parentId 父部门ID
       */
  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

    /**
     * 这个方法的作用是获取在父部门中的次序值
     * @return 返回Integer类型变量
       */
  public Integer getOrder() {
    return order;
  }

    /**
     * 这个方法的作用是设置在父部门中的次序值
     * @param order 在父部门中的次序值
       */
  public void setOrder(Integer order) {
    this.order = order;
  }

    /**
     * 这个方法的作用是将Json格式数据转化为WxCpDepart类对象
     * @param json 格式数据
       * @return 返回WxCpDepart类对象
       */
  public static WxCpDepart fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDepart.class);
  }

    /**
     * 这个方法的作用是将对象转化成JSON格式
     * @return Json格式数据
       */
  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  @Override
  public String toString() {
    return "WxCpDepart{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", parentId=" + parentId +
        ", order=" + order +
        '}';
  }
}
