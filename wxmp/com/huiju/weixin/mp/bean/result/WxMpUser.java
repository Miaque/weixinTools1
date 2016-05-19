package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 获取微信用户信息结果
 * @author chanjarster
 *
 */
public class WxMpUser implements Serializable {

  protected Boolean subscribe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）。
  protected String openId;//用户的标识，对当前公众号唯一
  protected String nickname;//用户的昵称
  protected String sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
  protected String language;//用户的语言，简体中文为zh_CN
  protected String city;//用户所在城市
  protected String province;//用户所在省份
  protected String country;//用户所在省份
  protected String headImgUrl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
  protected Long subscribeTime;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
  protected String unionId;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
  protected Integer sexId;
  protected String remark;//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
  protected Integer groupId;//用户所在的分组ID

  public Boolean getSubscribe() {
    return subscribe;
  }
  public Boolean isSubscribe() {
    return subscribe;
  }
  public void setSubscribe(Boolean subscribe) {
    this.subscribe = subscribe;
  }
  public String getOpenId() {
    return openId;
  }
  public void setOpenId(String openId) {
    this.openId = openId;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public String getSex() {
    return sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getHeadImgUrl() {
    return headImgUrl;
  }
  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }
  public Long getSubscribeTime() {
    return subscribeTime;
  }
  public void setSubscribeTime(Long subscribeTime) {
    this.subscribeTime = subscribeTime;
  }
  public String getUnionId() {
    return unionId;
  }
  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }

  public Integer getSexId() {

    return sexId;
  }

  public void setSexId(Integer sexId) {
    this.sexId = sexId;
  }

  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public Integer getGroupId() {
    return groupId;
  }
  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  /**
   * 返回的数据结果数据转换
   * @param json
   * @return
   */
  public static WxMpUser fromJson(String json) {
    return WxMpGsonBuilder.INSTANCE.create().fromJson(json, WxMpUser.class);
  }

  @Override
  public String toString() {
    return "WxMpUser{" +
        "subscribe=" + subscribe +
        ", openId='" + openId + '\'' +
        ", nickname='" + nickname + '\'' +
        ", sex='" + sex + '\'' +
        ", language='" + language + '\'' +
        ", city='" + city + '\'' +
        ", province='" + province + '\'' +
        ", country='" + country + '\'' +
        ", headImgUrl='" + headImgUrl + '\'' +
        ", subscribeTime=" + subscribeTime +
        ", unionId='" + unionId + '\'' +
        ", remark='" + remark + '\'' +
        ", groupId='" + groupId + '\'' +
        '}';
  }
}
