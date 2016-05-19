package com.huiju.weixin.mp.bean.outxmlbuilder;

import com.huiju.weixin.mp.bean.WxMpXmlOutTransferCustomerServiceMessage;

/**
 * 客服消息builder
 * <pre>
 * 用法: WxMpCustomMessage m = WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().content(...).toUser(...).build();
 * </pre>
 *
 * @author chanjarster
 */
public final class TransferCustomerServiceBuilder extends BaseBuilder<TransferCustomerServiceBuilder, WxMpXmlOutTransferCustomerServiceMessage> {
  private String kfAccount;

  /**
   * 指定会话接入的客服账号
   * @param kfAccount 客服账号
   * @return 返回TransferCustomerServiceBuilder
   */
  public TransferCustomerServiceBuilder kfAccount(String kfAccount) {
    this.kfAccount = kfAccount;
    return this;
  }

  /**
   * 消息创建
   */
  public WxMpXmlOutTransferCustomerServiceMessage build() {
    WxMpXmlOutTransferCustomerServiceMessage m = new WxMpXmlOutTransferCustomerServiceMessage();
    setCommon(m);
    m.setKfAccount(kfAccount);
    return m;
  }
}
