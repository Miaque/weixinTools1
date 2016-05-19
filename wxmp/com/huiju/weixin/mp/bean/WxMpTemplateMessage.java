package com.huiju.weixin.mp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 模板消息类
 */
public class WxMpTemplateMessage implements Serializable {

  /**
   * 接受者
   */
  private String toUser;
  /**
   * 模板ID
   */
  private String templateId;
  /**
   * 链接
   */
  private String url;
  /**
   * 顶部颜色
   */
  private String topColor;
  /**
   * 消息模板数据集
   */
  private List<WxMpTemplateData> datas = new ArrayList<WxMpTemplateData>();

  /**
   * 这个方法的作用是获取接受者
   * @return 返回string类型变量
     */
  public String getToUser() {
    return toUser;
  }

  /**
   * 这个方法的作用是设置接受者
   * @param toUser 接受者
     */
  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  /**
   * 这个方法的作用是获取模板ID
   * @return 返回string类型变量
     */
  public String getTemplateId() {
    return templateId;
  }

  /**
   * 这个方法的作用是设置模板ID
   * @param templateId 模板ID
     */
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  /**
   * 这个方法的作用是获取链接
   * @return 返回string类型变量
     */
  public String getUrl() {
    return url;
  }

  /**
   * 这个方法的作用是设置链接
   * @param url 链接
     */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 这个方法的作用是获取顶部颜色
   * @return 返回string类型变量
     */
  public String getTopColor() {
    return topColor;
  }

  /**
   * 这个方法的作用是设置顶部颜色
   * @param topColor 顶部颜色
     */
  public void setTopColor(String topColor) {
    this.topColor = topColor;
  }

  /**
   * 这个方法的作用是获取消息模板数据集
   * @return 返回消息模板数据集
     */
  public List<WxMpTemplateData> getDatas() {
    return datas;
  }

  /**
   * 这个方法的作用是设置消息模板数据集
   * @param datas 消息模板数据集
     */
  public void setDatas(List<WxMpTemplateData> datas) {
    this.datas = datas;
  }

  /**
   * 这个方法的作用是将数据转化成Json数据格式
   * @return 返回Sting类型数据
     */
  public String toJson() {
    return WxMpGsonBuilder.INSTANCE.create().toJson(this);
  }
}
