package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;

import com.huiju.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 语义理解查询结果对象
 *
 * http://mp.weixin.qq.com/wiki/index.php?title=语义理解
 *
 * @author Daniel Qian
 */
public class WxMpSemanticQueryResult implements Serializable {

  private String query;//用户的输入字符串
  private String type;//服务的全局类型id，详见协议文档中垂直服务协议定义
  private String semantic;//语义理解后的结构化标识，各服务不同
  private String result;//部分类别的结果
  private String answer;//部分类别的结果html5展示，目前不支持
  private String text;//特殊回复说明

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSemantic() {
    return semantic;
  }

  public void setSemantic(String semantic) {
    this.semantic = semantic;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  /**
   * 数据返回结果 转换
   * @param json
   * @return
   */
  public static WxMpSemanticQueryResult fromJson(String json) {
    return WxMpGsonBuilder.create().fromJson(json, WxMpSemanticQueryResult.class);
  }

}
