package com.huiju.weixin.mp.bean;

import com.huiju.weixin.common.api.WxConsts;
import com.huiju.weixin.common.util.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * 同步回复给用户的客服消息，xml格式
 */
@XStreamAlias("xml")
public class WxMpXmlOutTransferCustomerServiceMessage extends WxMpXmlOutMessage {
  @XStreamAlias("TransInfo")
  /**
   * 实例化客服
   */
  protected final TransInfo transInfo = new TransInfo();

  /**
   * 默认构造函数
   */
  public WxMpXmlOutTransferCustomerServiceMessage() {
    this.msgType = WxConsts.CUSTOM_MSG_TRANSFER_CUSTOMER_SERVICE;
  }

  /**
   * 这个方法的作用是获取客服账号
   * @return 返回String类型变量
     */
  public String getKfAccount() {
    return transInfo.getKfAccount();
  }
  /**
   * 这个方法的作用是设置客服账号
   * @param kfAccount 客服账号
   */
  public void setKfAccount(String kfAccount) {
    transInfo.setKfAccount(kfAccount);
  }

  /**
   * 客服
   * @author Administrator
   */
  @XStreamAlias("TransInfo")
  public static class TransInfo {

	/**
	 * 指定会话接入的客服账号
	 */
    @XStreamAlias("KfAccount")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String kfAccount;

    /**
     * 这个方法的作用是获取客服账号
     * @return 返回String类型变量
     */
    public String getKfAccount() {
      return kfAccount;
    }
    /**
     * 这个方法的作用是设置客服账号
     * @param kfAccount 客服账号
     */
    public void setKfAccount(String kfAccount) {
      this.kfAccount = kfAccount;
    }
  }
}
