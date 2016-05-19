package com.huiju.weixin.mp.bean;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 微信智能接口，语义理解接口
 * 语义理解查询用对象
 *
 * http://mp.weixin.qq.com/wiki/index.php?title=语义理解
 *
 * @author Daniel Qian
 */
public class WxMpSemanticQuery implements Serializable {

  /**
   * 输入文本串
   */
  private String query;
  /**
   * 需要使用的服务类型，多个用“，”隔开，不能为空
   */
  private String category;
  /**
   * 纬度坐标，与经度同时传入；与城市二选一传入
   */
  private Float latitude;
  /**
   * 经度坐标，与纬度同时传入；与城市二选一传入
   */
  private Float longitude;
  /**
   * 城市名称，与经纬度二选一传入
   */
  private String city;
  /**
   * 区域名称，在城市存在的情况下可省；与经纬度二选一传入
   */
  private String region;
  /**
   * 公众号唯一标识，用于区分公众号开发者
   */
  private String appid;
  /**
   * 用户唯一id（非开发者id），用户区分公众号下的不同用户（建议填入用户openid），如果为空，则无法使用上下文理解功能。
   * appid和uid同时存在的情况下，才可以使用上下文理解功能。
   */
  private String uid;

  /**
   * 这个方法的作用是获取输入文本串
   * @return 返回string类型变量
     */
  public String getQuery() {
    return query;
  }

  /**
   * 这个方法的作用是设置输入文本串
   * @param query 输入文本串
     */
  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * 这个方法的作用是获取需要使用的服务类型
   * @return 返回string类型变量
     */
  public String getCategory() {
    return category;
  }

  /**
   * 这个方法的作用是设置需要使用的服务类型
   * @param category 需要使用的服务类型
     */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * 这个方法的作用是获取纬度坐标
   * @return 返回Float类型变量
     */
  public Float getLatitude() {
    return latitude;
  }

  /**
   * 这个方法的作用是设置纬度坐标
   * @param latitude 纬度坐标
     */
  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  /**
   * 这个方法的作用是获取经度坐标
   * @return 返回Float类型变量
     */
  public Float getLongitude() {
    return longitude;
  }

  /**
   * 这个方法的作用是设置经度坐标
   * @param longitude 经度坐标
     */
  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  /**
   * 这个方法的作用是获取城市名称
   * @return 返回string类型变量
     */
  public String getCity() {
    return city;
  }

  /**
   * 这个方法的作用是设置城市名称
   * @param city 城市名称
     */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   *
   * @return 返回string类型变量
     */
  public String getRegion() {
    return region;
  }

  /**
   * 这个方法的作用是获取区域名称
   * @param region 区域名称
     */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * 这个方法的作用是设置区域名称
   * @return 返回string类型变量
     */
  public String getAppid() {
    return appid;
  }

  /**
   * 这个方法的作用是获取公众号唯一标识
   * @param appid 公众号唯一标识
     */
  public void setAppid(String appid) {
    this.appid = appid;
  }

  /**
   * 这个方法的作用是获取用户唯一id
   * @return 返回string类型变量
     */
  public String getUid() {
    return uid;
  }

  /**
   * 这个方法的作用是设置用户唯一id
   * @param uid 用户唯一id（非开发者id）
     */
  public void setUid(String uid) {
    this.uid = uid;
  }

  /**
   * 这个方法的作用是将数据转化成Json格式数据
   * @return 返回string类型数据
     */
  public String toJson() {
    return WxMpGsonBuilder.create().toJson(this);
  }

}
