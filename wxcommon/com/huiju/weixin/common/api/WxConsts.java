package com.huiju.weixin.common.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Wx API类.
 *
 * @author Neu
 */
public class WxConsts {

  ///////////////////////
  // 微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型
  ///////////////////////
  /**
   * 文本
   */
  public static final String XML_MSG_TEXT = "text";
  /**
   * 图片
   */
  public static final String XML_MSG_IMAGE = "image";
  /**
   * 语音
   */
  public static final String XML_MSG_VOICE = "voice";
  /**
   * 视频
   */
  public static final String XML_MSG_VIDEO = "video";
  /**
   * 消息
   */
  public static final String XML_MSG_NEWS = "news";
  /**
   * 音乐
   */
  public static final String XML_MSG_MUSIC = "music";
  /**
   * 地理位置
   */
  public static final String XML_MSG_LOCATION = "location";
  /**
   * 链接
   */
  public static final String XML_MSG_LINK = "link";
  /**
   * 事件
   */
  public static final String XML_MSG_EVENT = "event";
  /**
   * 客服系统
   */
  public static final String XML_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
  
  ///////////////////////
  // 主动发送消息的消息类型
  ///////////////////////
  /**
   * 文本
   */
  public static final String CUSTOM_MSG_TEXT = "text";
  /**
   * 图片
   */
  public static final String CUSTOM_MSG_IMAGE = "image";
  /**
   * 语音
   */
  public static final String CUSTOM_MSG_VOICE = "voice";
  /**
   * 视频
   */
  public static final String CUSTOM_MSG_VIDEO = "video";
  /**
   * 音乐
   */
  public static final String CUSTOM_MSG_MUSIC = "music";
  /**
   * 消息
   */
  public static final String CUSTOM_MSG_NEWS = "news";
  /**
   * 文件
   */
  public static final String CUSTOM_MSG_FILE = "file";
  /**
   * 客服系统
   */
  public static final String CUSTOM_MSG_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
  /**
   * 表示是否是保密消息，0表示否，1表示是，默认0
   */
  public static final String CUSTOM_MSG_SAFE_NO = "0";
  /**
   * 表示是否是保密消息，0表示否，1表示是，默认0
   */
  public static final String CUSTOM_MSG_SAFE_YES = "1";
  
  ///////////////////////
  // 群发消息的消息类型
  ///////////////////////
  /**
   * mpnews消息与news消息类似，不同的是图文消息内容存储在微信后台，并且支持保密选项。每个应用每天最多可以发送100次。
   */
  public static final String MASS_MSG_NEWS = "mpnews";
  /**
   * 文本
   */
  public static final String MASS_MSG_TEXT = "text";
  /**
   * 语音
   */
  public static final String MASS_MSG_VOICE = "voice";
  /**
   * 图片
   */
  public static final String MASS_MSG_IMAGE = "image";
  /**
   * 多人视频
   */
  public static final String MASS_MSG_VIDEO = "mpvideo";
  
  ///////////////////////
  // 客服消息用户类型类型
  /////////////////////// 
  /**
   * 客服
   */
  public static final String MASS_KF_NEWS = "kf";
  /**
   * 客户，企业员工userid
   */
  public static final String MASS_KF_TEXT = "userid";
  /**
   * 客户，公众号openid
   */
  public static final String MASS_KF_VOICE = "openid"; 
  
  ///////////////////////
  // 群发消息后微信端推送给服务器的反馈消息
  ///////////////////////
  /**
   * 发送成功
   */
  public static final String MASS_ST_SUCCESS = "send success";
  /**
   * 发送失败
   */
  public static final String MASS_ST_FAIL = "send fail";
  /**
   * 失败信息（涉嫌广告）
   */
  public static final String MASS_ST_涉嫌广告 = "err(10001)";
  /**
   * 失败信息（涉嫌政治）
   */
  public static final String MASS_ST_涉嫌政治 = "err(20001)";
  /**
   * 失败信息（涉嫌社会）
   */
  public static final String MASS_ST_涉嫌社会 = "err(20004)";
  /**
   * 失败信息（涉嫌色情）
   */
  public static final String MASS_ST_涉嫌色情 = "err(20002)";
  /**
   * 失败信息（涉嫌违法犯罪）
   */
  public static final String MASS_ST_涉嫌违法犯罪 = "err(20006)";
  /**
   * 失败信息（涉嫌欺诈）
   */
  public static final String MASS_ST_涉嫌欺诈 = "err(20008)";
  /**
   * 失败信息（涉嫌版权）
   */
  public static final String MASS_ST_涉嫌版权 = "err(20013)";
  /**
   * 失败信息（涉嫌互推_互相宣传）
   */
  public static final String MASS_ST_涉嫌互推_互相宣传 = "err(22000)";
  /**
   * 失败信息（涉嫌其他）
   */
  public static final String MASS_ST_涉嫌其他 = "err(21000)";
  
  /**
   * 群发反馈消息代码所对应的文字描述
   */
  public static final Map<String, String> MASS_ST_2_DESC = new HashMap<String, String>();
  static {
    MASS_ST_2_DESC.put(MASS_ST_SUCCESS, "发送成功");
    MASS_ST_2_DESC.put(MASS_ST_FAIL, "发送失败");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌广告, "涉嫌广告");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌政治, "涉嫌政治");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌社会, "涉嫌社会");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌色情, "涉嫌色情");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌违法犯罪, "涉嫌违法犯罪");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌欺诈, "涉嫌欺诈");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌版权, "涉嫌版权");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌互推_互相宣传, "涉嫌互推_互相宣传");
    MASS_ST_2_DESC.put(MASS_ST_涉嫌其他, "涉嫌其他");
  }
  
  ///////////////////////
  // 微信端推送过来的事件类型
  ///////////////////////
  /**
   * 关注
   */
  public static final String EVT_SUBSCRIBE = "subscribe";
  /**
   * 取消关注
   */
  public static final String EVT_UNSUBSCRIBE = "unsubscribe";
  /**
   * 扫码
   */
  public static final String EVT_SCAN = "SCAN";
  /**
   * 地理位置
   */
  public static final String EVT_LOCATION = "LOCATION";
  /**
   * 成员点击自定义菜单后，微信会把点击事件推送给开发者
   */
  public static final String EVT_CLICK = "CLICK";
  /**
   * 点击菜单跳转链接
   */
  public static final String EVT_VIEW = "VIEW";
  /**
   * 大量推送工作完成
   */
  public static final String EVT_MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
  /**
   * 扫码推事件
   */
  public static final String EVT_SCANCODE_PUSH = "scancode_push";
  /**
   * 扫码推事件且弹出“消息接收中”提示框
   */
  public static final String EVT_SCANCODE_WAITMSG = "scancode_waitmsg";
  /**
   * 弹出系统拍照发图
   */
  public static final String EVT_PIC_SYSPHOTO = "pic_sysphoto";
  /**
   * 弹出拍照或者相册发图
   */
  public static final String EVT_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
  /**
   * 弹出微信相册发图器
   */
  public static final String EVT_PIC_WEIXIN = "pic_weixin";
  /**
   * 弹出地理位置选择器
   */
  public static final String EVT_LOCATION_SELECT = "location_select";
  /**
   * 模版消息发送任务完成
   */
  public static final String EVT_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
  /**
   * 成员进入应用
   */
  public static final String EVT_ENTER_AGENT = "enter_agent";

  ///////////////////////
  // 上传多媒体文件的类型
  ///////////////////////
  /**
   * 图片
   */
  public static final String MEDIA_IMAGE = "image";
  /**
   * 语音
   */
  public static final String MEDIA_VOICE = "voice";
  /**
   * 视频
   */
  public static final String MEDIA_VIDEO = "video";
  /**
   * 相册
   */
  public static final String MEDIA_THUMB = "thumb";
  /**
   * 文件
   */
  public static final String MEDIA_FILE = "file";
  
  ///////////////////////
  // 文件类型
  ///////////////////////
  /**
   * jpeg格式
   */
  public static final String FILE_JPG = "jpeg";
  /**
   * mp3格式
   */
  public static final String FILE_MP3 = "mp3";
  /**
   * amr格式
   */
  public static final String FILE_AMR = "amr";
  /**
   * mp4格式
   */
  public static final String FILE_MP4 = "mp4";


  ///////////////////////
  // 自定义菜单的按钮类型
  ///////////////////////
  /** 点击推事件 */
  public static final String BUTTON_CLICK = "click";
  /** 跳转URL */
  public static final String BUTTON_VIEW = "view";
  /** 扫码推事件 */
  public static final String BUTTON_SCANCODE_PUSH = "scancode_push";
  /** 扫码推事件且弹出“消息接收中”提示框 */
  public static final String BUTTON_SCANCODE_WAITMSG = "scancode_waitmsg";
  /** 弹出系统拍照发图 */
  public static final String PIC_SYSPHOTO = "pic_sysphoto";
  /** 弹出拍照或者相册发图 */
  public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
  /** 弹出微信相册发图器 */
  public static final String PIC_WEIXIN = "pic_weixin";
  /** 弹出地理位置选择器 */
  public static final String LOCATION_SELECT = "location_select";

  ///////////////////////
  // oauth2网页授权的scope
  ///////////////////////
  /** 不弹出授权页面，直接跳转，只能获取用户openid */
  public static final String OAUTH2_SCOPE_BASE = "snsapi_base";
  /** 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息 */
  public static final String OAUTH2_SCOPE_USER_INFO = "snsapi_userinfo";

  ///////////////////////
  // 永久素材类型
  ///////////////////////
  /**
   * 消息
   */
  public static final String MATERIAL_NEWS = "news";
  /**
   * 语音
   */
  public static final String MATERIAL_VOICE = "voice";
  /**
   * 图片
   */
  public static final String MATERIAL_IMAGE = "image";
  /**
   * 视频
   */
  public static final String MATERIAL_VIDEO = "video";
}
