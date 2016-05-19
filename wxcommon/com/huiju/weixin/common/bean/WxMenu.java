package com.huiju.weixin.common.bean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.Charsets;

import com.huiju.weixin.common.util.json.WxGsonBuilder;

/**
 * 企业号菜单
 * <p>目前自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。<br>
 * @author Daniel Qian
 *
 */
public class WxMenu implements Serializable {

  private static final long serialVersionUID = 3181637363174585864L;
  /**
   * 一级菜单数组
   */
  private List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
  /**
   * 菜单匹配规则
   */
  private WxMenuRule matchRule;
  /**
   * 该方法的作用是获取自定义一级菜单
   * @return 返回List数组
   */
  public List<WxMenuButton> getButtons() {
    return buttons;
  }
  /**
   * 该方法的作用是定义一级菜单
   * @param buttons 自定义一级菜单
   */
  public void setButtons(List<WxMenuButton> buttons) {
    this.buttons = buttons;
  }
  /**
   * 该方法的作用是获取菜单匹配规则
   * @return 返回WxMenuRule类型变量
   */
  public WxMenuRule getMatchRule() {
    return matchRule;
  }
  /**
   * 该方法的作用是定义菜单匹配规则
   * @param matchRule 菜单匹配规则
   */
  public void setMatchRule(WxMenuRule matchRule) {
    this.matchRule = matchRule;
  }
  /**
   * 该方法的作用是将对象转化成Json格式数据
   * @return 返回String类型变量
   */
  public String toJson() {
    return WxGsonBuilder.create().toJson(this);
  }

  /**
   * 要用 http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html 格式来反序列化
   * 相比 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html 的格式，外层多套了一个menu
   * @param json 对象
   * @return 返回WxMenu类型变量
   */
  public static WxMenu fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxMenu.class);
  }

  /**
   * 要用 http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html 格式来反序列化
   * 相比 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html 的格式，外层多套了一个menu
   * @param is 输入流
   * @return 返回WxMenu类型变量
   */
  public static WxMenu fromJson(InputStream is) {
    return WxGsonBuilder.create().fromJson(new InputStreamReader(is, Charsets.UTF_8), WxMenu.class);
  }

  @Override
  public String toString() {
    return "WxMenu{" +
        "buttons=" + buttons +
        '}';
  }

  /**
   * 自定义菜单接口类.
   */
  public static class WxMenuButton {
    /**
     * 菜单的响应动作类型
     */
    private String type;
    /**
     * 菜单标题，不超过16个字节，子菜单不超过40个字节
     */
    private String name;
    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;
    /**
     * 网页链接，成员点击菜单可打开链接，不超过256字节
     */
    private String url;
    /**
     * 该方法的作用是定义二级菜单数组，个数应为1~5个
     */
    private List<WxMenuButton> subButtons = new ArrayList<WxMenuButton>();
    /**
     * 该方法的作用是获取菜单的响应动作类型
     * @return 返回String类型变量
     */
    public String getType() {
      return type;
    }
    /**
     * 该方法的作用是定义菜单的响应动作类型
     * @param type 菜单的响应动作类型
     */
    public void setType(String type) {
      this.type = type;
    }
    /**
     * 该方法的作用是获取菜单标题
     * @return 返回String类型变量
     */
    public String getName() {
      return name;
    }
    /**
     * 该方法的作用是定义菜单标题
     * @param name 菜单标题
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     * 该方法的作用是设置菜单KEY值，用于消息接口推送
     * @return 返回String类型变量
     */
    public String getKey() {
      return key;
    }
    /**
     * 该方法的作用是定义菜单KEY值
     * @param key 菜单KEY值
     */
    public void setKey(String key) {
      this.key = key;
    }
    /**
     * 该方法的作用是获取网页链接
     * @return 返回String类型变量
     */
    public String getUrl() {
      return url;
    }
    /**
     * 该方法的作用是设置网页链接
     * @param url 网页链接
     */
    public void setUrl(String url) {
      this.url = url;
    }
    /**
     * 该方法的作用是获取二级菜单
     * @return 返回List类型数组
     */
    public List<WxMenuButton> getSubButtons() {
      return subButtons;
    }

    /**
     * 该方法的作用是定义二级菜单
     * @param subButtons 二级菜单
     */
    public void setSubButtons(List<WxMenuButton> subButtons) {
      this.subButtons = subButtons;
    }

    @Override
    public String toString() {
      return "WxMenuButton{" +
          "type='" + type + '\'' +
          ", name='" + name + '\'' +
          ", key='" + key + '\'' +
          ", url='" + url + '\'' +
          ", subButtons=" + subButtons +
          '}';
    }
  }

  /**
   * 菜单匹配规则类.
   */
  public static class WxMenuRule {
    /**
     * 用户所在的分组ID
     */
    private String groupId;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 客户端平台类型
     */
    private String clientPlatformType;
    /**
     * 该方法的作用是获取用户所在的分组ID
     * @return 返回String类型变量
     */
    public String getGroupId() {
      return groupId;
    }
    /**
     * 该方法的作用是设置用户所在的分组ID
     * @param groupId 用户所在的分组ID
     */
    public void setGroupId(String groupId) {
      this.groupId = groupId;
    }
    /**
     * 该方法的作用是获取用户性别
     * @return 返回String类型变量
     */
    public String getSex() {
      return sex;
    }
    /**
     * 该方法的作用是设置用户性别
     * @param sex 用户性别
     */
    public void setSex(String sex) {
      this.sex = sex;
    }
    /**
     * 该方法的作用是获取用户所在国家
     * @return 返回String类型变量
     */
    public String getCountry() {
      return country;
    }
    /**
     * 该方法的作用是设置用户所在国家
     * @param country 用户所在国家
     */
    public void setCountry(String country) {
      this.country = country;
    }
    /**
     * 该方法的作用是获取用户所在省份
     * @return 返回String类型变量
     */
    public String getProvince() {
      return province;
    }
    /**
     * 该方法的作用是设置用户所在省份
     * @param province 用户所在省份
     */
    public void setProvince(String province) {
      this.province = province;
    }
    /**
     * 该方法的作用是获取用户所在城市
     * @return 返回String类型变量
     */
    public String getCity() {
      return city;
    }
    /**
     * 该方法的作用是设置用户所在城市
     * @param city 用户所在城市
     */
    public void setCity(String city) {
      this.city = city;
    }
    /**
     * 该方法的作用是获取客户端平台类型
     * @return 返回String客户端平台类型
     */
    public String getClientPlatformType() {
      return clientPlatformType;
    }
    /**
     * 该方法的作用是设置客户端平台类型
     * @param clientPlatformType 客户端平台类型
     */
    public void setClientPlatformType(String clientPlatformType) {
      this.clientPlatformType = clientPlatformType;
    }
	
    @Override
    public String toString() {
      return "matchrule:{" +
          "group_id='" + groupId + '\'' +
          ", sex='" + sex + '\'' +
          ", country" + country + '\'' +
          ", province" + province + '\'' +
          ", city" + city + '\'' +
          ", client_platform_type" + clientPlatformType + '\'' +
          "}";
    }
  }
	
}
