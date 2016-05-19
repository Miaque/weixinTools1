package com.huiju.weixin.mp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huiju.weixin.mp.bean.WxMpMassNews;
import com.huiju.weixin.mp.bean.WxMpCustomMessage;
import com.huiju.weixin.mp.bean.WxMpGroup;
import com.huiju.weixin.mp.bean.WxMpMassGroupMessage;
import com.huiju.weixin.mp.bean.WxMpMassOpenIdsMessage;
import com.huiju.weixin.mp.bean.WxMpMassVideo;
import com.huiju.weixin.mp.bean.WxMpMaterialArticleUpdate;
import com.huiju.weixin.mp.bean.WxMpMaterialNews;
import com.huiju.weixin.mp.bean.WxMpTemplateMessage;
import com.huiju.weixin.mp.bean.result.WxMpMassSendResult;
import com.huiju.weixin.mp.bean.result.WxMpMassUploadResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialCountResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialFileBatchGetResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialNewsBatchGetResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialUploadResult;
import com.huiju.weixin.mp.bean.result.WxMpMaterialVideoInfoResult;
import com.huiju.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import com.huiju.weixin.mp.bean.result.WxMpQrCodeTicket;
import com.huiju.weixin.mp.bean.result.WxMpSemanticQueryResult;
import com.huiju.weixin.mp.bean.result.WxMpUser;
import com.huiju.weixin.mp.bean.result.WxMpUserCumulate;
import com.huiju.weixin.mp.bean.result.WxMpUserList;
import com.huiju.weixin.mp.bean.result.WxMpUserSummary;

public class WxMpGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxMpCustomMessage.class, new WxMpCustomMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassNews.class, new WxMpMassNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassGroupMessage.class, new WxMpMassGroupMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassOpenIdsMessage.class, new WxMpMassOpenIdsMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpGroup.class, new WxMpGroupGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUser.class, new WxMpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserList.class, new WxUserListGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassVideo.class, new WxMpMassVideoAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassSendResult.class, new WxMpMassSendResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassUploadResult.class, new WxMpMassUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpQrCodeTicket.class, new WxQrCodeTicketAdapter());
    INSTANCE.registerTypeAdapter(WxMpTemplateMessage.class, new WxMpTemplateMessageGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpSemanticQueryResult.class, new WxMpSemanticQueryResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpOAuth2AccessToken.class, new WxMpOAuth2AccessTokenAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserSummary.class, new WxMpUserSummaryGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpUserCumulate.class, new WxMpUserCumulateGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialUploadResult.class, new WxMpMaterialUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialVideoInfoResult.class, new WxMpMaterialVideoInfoResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMassNews.WxMpMassNewsArticle.class, new WxMpMassNewsArticleGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialArticleUpdate.class, new WxMpMaterialArticleUpdateGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialCountResult.class, new WxMpMaterialCountResultAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNews.class, new WxMpMaterialNewsGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNews.WxMpMaterialNewsArticle.class, new WxMpMaterialNewsArticleGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.class, new WxMpMaterialNewsBatchGetGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialNewsBatchGetResult.WxMaterialNewsBatchGetNewsItem.class, new WxMpMaterialNewsBatchGetGsonItemAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.class, new WxMpMaterialFileBatchGetGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMpMaterialFileBatchGetResult.WxMaterialFileBatchGetNewsItem.class, new WxMpMaterialFileBatchGetGsonItemAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
