package com.huiju.weixin.mp.bean.result;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 用户增减数据接口的返回JSON数据包
 * http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html
 * </pre>
 */
public class WxMpUserSummary implements Serializable {

  private Date refDate;//数据的日期

  private Integer userSource;//用户的渠道，数值代表的含义如下：0代表其他（包括带参数二维码） 3代表扫二维码 17代表名片分享 35代表搜号码（即微信添加朋友页的搜索） 39代表查询微信公众帐号 43代表图文页右上角菜单

  private Integer newUser;//新增的用户数量

  private Integer cancelUser;//取消关注的用户数量，new_user减去cancel_user即为净增用户数量

  public Date getRefDate() {
    return refDate;
  }

  public void setRefDate(Date refDate) {
    this.refDate = refDate;
  }

  public Integer getUserSource() {
    return userSource;
  }

  public void setUserSource(Integer userSource) {
    this.userSource = userSource;
  }

  public Integer getNewUser() {
    return newUser;
  }

  public void setNewUser(Integer newUser) {
    this.newUser = newUser;
  }

  public Integer getCancelUser() {
    return cancelUser;
  }

  public void setCancelUser(Integer cancelUser) {
    this.cancelUser = cancelUser;
  }

  @Override
  public String toString() {
    return "WxMpUserSummary{" +
        "refDate=" + refDate +
        ", userSource=" + userSource +
        ", newUser=" + newUser +
        ", cancelUser=" + cancelUser +
        '}';
  }
}
