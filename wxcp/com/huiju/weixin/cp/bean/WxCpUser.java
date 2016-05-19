package com.huiju.weixin.cp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 微信用户信息
 *
 * @author Daniel Qian
 */
public class WxCpUser implements Serializable {

  /**
   * 成员UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字节
   */
  private String userId;
  /**
   * 成员名称。长度为1~64个字节
   */
  private String name;
  /**
   * 成员所属部门id列表
   */
  private Integer[] departIds;
  /**
   * 职位信息。长度为0~64个字节
   */
  private String position;
  /**
   * 手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
   */
  private String mobile;
  /**
   * 性别。1表示男性，2表示女性
   */
  private String gender;
  /**
   * 电话
   */
  private String tel;
  /**
   * 邮箱。长度为0~64个字节。企业内必须唯一
   */
  private String email;
  /**
   * 微信号。企业内必须唯一。（注意：是微信号，不是微信的名字）
   */
  private String weiXinId;
  /**
   * 头像url。注：如果要获取小图将url最后的"/0"改成"/64"即可
   */
  private String avatar;
  /**
   * 关注状态: 0=获取全部成员， 1=已关注，2=已禁用，4=未关注；status可叠加，未填默认为4
   */
  private Integer status;
  /**
   * 是否可用
   */
  private Integer enable;
  /**
   * 扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值
   */
  private final List<Attr> extAttrs = new ArrayList<Attr>();

  /**
   * 这个方法的作用是获取成员UserID
   * @return 返回String类型变量
     */
  public String getUserId() {
    return userId;
  }

  /**
   * 这个方法的作用是设置成员UserID
   * @param userId 成员UserID
     */
  public void setUserId(String userId) {
    this.userId = userId;
  }
  /**
   * 这个方法的作用是获取成员名称
   * @return 返回String类型变量
   */
  public String getName() {
    return name;
  }
  /**
   * 这个方法的作用是设置成员名称
   * @param name 成员名称
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * 这个方法的作用是获取成员所属部门id列表
   * @return 返回Integer类型数组
   */
  public Integer[] getDepartIds() {
    return departIds;
  }
  /**
   * 这个方法的作用是设置成员所属部门id列表
   * @param departIds 成员所属部门id列表
   */
  public void setDepartIds(Integer[] departIds) {
    this.departIds = departIds;
  }
  /**
   * 这个方法的作用是获取性别
   * @return 返回String类型变量
   */
  public String getGender() {
    return gender;
  }
  /**
   * 这个方法的作用是设置性别
   * @param gender 性别
   */
  public void setGender(String gender) {
    this.gender = gender;
  }
  /**
   * 这个方法的作用是获取职位信息
   * @return 返回String类型变量
   */
  public String getPosition() {
    return position;
  }
  /**
   * 这个方法的作用是设置职位信息
   * @param position 职位信息
   */
  public void setPosition(String position) {
    this.position = position;
  }
  /**
   * 这个方法的作用是获取成员手机号码
   * @return 返回String类型变量
   */
  public String getMobile() {
    return mobile;
  }
  /**
   * 这个方法的作用是设置成员手机号码
   * @param mobile 手机号码
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  /**
   * 这个方法的作用是获取成员电话
   * @return 返回String类型变量
   */
  public String getTel() {
    return tel;
  }
  /**
   * 这个方法的作用是设置成员电话
   * @param tel 成员电话
   */
  public void setTel(String tel) {
    this.tel = tel;
  }
  /**
   * 这个方法的作用是获取成员邮箱
   * @return 返回String类型变量
   */
  public String getEmail() {
    return email;
  }
  /**
   * 这个方法的作用是设置成员邮箱
   * @param email 成员邮箱
   */
  public void setEmail(String email) {
    this.email = email;
  }
  /**
   * 这个方法的作用是获取微信ID
   * @return 返回String类型变量
   */
  public String getWeiXinId() {
    return weiXinId;
  }
  /**
   * 这个方法的作用是设置微信ID
   * @param weiXinId 微信ID
   */
  public void setWeiXinId(String weiXinId) {
    this.weiXinId = weiXinId;
  }
  /**
   * 这个方法的作用是获取头像url
   * @return 返回String类型变量
   */
  public String getAvatar() {
    return avatar;
  }
  /**
   * 这个方法的作用是设置头像url
   * @param avatar 头像url
   */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  /**
   * 这个方法的作用是获取关注状态
   * @return 返回Integer类型变量
   */
  public Integer getStatus() {
    return status;
  }
  /**
   * 这个方法的作用是设置关注状态
   * @param status 关注状态
   */
  public void setStatus(Integer status) {
    this.status = status;
  }
  /**
   * 这个方法的作用是获取是否可用
   * @return 返回Integer类型变量
   */
  public Integer getEnable() {
	return enable;
  }
  /**
   * 这个方法的作用是设置是否可用
   * @param enable 是否可用
   */
  public void setEnable(Integer enable) {
	this.enable = enable;
  }

  /**
   * 这个方法的作用是添加扩展属性
   * @param name 名称
   * @param value 值
     */
  public void addExtAttr(String name, String value) {
    this.extAttrs.add(new Attr(name, value));
  }
  /**
   * 这个方法的作用是获取扩展属性列表
   * @return 返回扩展属性列表
   */
  public List<Attr> getExtAttrs() {
    return extAttrs;
  }

  /**
   * 这个方法的作用是将对象转化成Json格式数据
   * @return Json格式数据
     */
  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

  /**
   * 这个方法的作用是将Json格式数据转化成WxCpUser类对象
   * @param json Json格式数据
   * @return WxCpUser类对象
     */
  public static WxCpUser fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpUser.class);
  }

  /**
   * 扩展属性类
   */
  public static class Attr {
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

    /**
     * 构造函数
     * @param name 名称
     * @param value 值
       */
    public Attr(String name, String value) {
      this.name = name;
      this.value = value;
    }
    /**
     * 这个方法的作用是获取扩展属性名称
     * @return 返回String类型变量
     */
    public String getName() {
      return name;
    }
    /**
     * 这个方法的作用是设置扩展属性名称
     * @param name 扩展属性名称
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     * 这个方法的作用是获取扩展属性值
     * @return 返回String类型变量
     */
    public String getValue() {
      return value;
    }
    /**
     * 这个方法的作用是设置扩展属性值
     * @param value 扩展属性值
     */
    public void setValue(String value) {
      this.value = value;
    }

  }

}
