package com.huiju.weixin.mp.bean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 其他类型永久素材类.
 */
public class WxMpMaterial {
  /**
   * 素材名
   */
  private String name;
  /**
   * 素材文件对象
   */
  private File file;
  /**
   * 视频类素材标题
   */
  private String videoTitle;
  /**
   * 视频类素材简介
   */
  private String videoIntroduction;

  /**
   * 默认构造函数
   */
  public WxMpMaterial() {
  }

  /**
   *
   * @param name 素材名
   * @param file 素材文件对象
   * @param videoTitle 视频类素材标题
   * @param videoIntroduction 视频类素材简介
     */
  public WxMpMaterial(String name, File file, String videoTitle, String videoIntroduction) {
    this.name = name;
    this.file = file;
    this.videoTitle = videoTitle;
    this.videoIntroduction = videoIntroduction;
  }

  /**
   * 这个方法的作用是获取表单
   * @return 返回表单
     */
  public Map<String, String> getForm() {
    Map<String, String> form = new HashMap<String, String>();
    form.put("title", videoTitle);
    form.put("introduction", videoIntroduction);
    return form;
  }

  /**
   * 这个方法的作用是获取视频类素材标题
   * @return 返回String类型变量
     */
  public String getVideoTitle() {
    return videoTitle;
  }

  /**
   *
   * @param videoTitle 视频类素材标题
     */
  public void setVideoTitle(String videoTitle) {
    this.videoTitle = videoTitle;
  }

  /**
   * 这个方法的作用是获取视频类素材简介
   * @return 返回String类型变量
     */
  public String getVideoIntroduction() {
    return videoIntroduction;
  }

  /**
   * 这个方法的作用是设置视频类素材简介
   * @param videoIntroduction 视频类素材简介
     */
  public void setVideoIntroduction(String videoIntroduction) {
    this.videoIntroduction = videoIntroduction;
  }

  /**
   * 这个方法的作用是获取素材名
   * @return 返回String类型变量
     */
  public String getName() {
    return name;
  }

  /**
   * 这个方法的设置是设置素材名
   * @param name 素材名
     */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 这个方法的作用是获取素材文件
   * @return 返回素材文件
     */
  public File getFile() {
    return file;
  }

  /**
   * 这个方法的设置是获取素材文件
   * @param file 素材文件
     */
  public void setFile(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return "WxMpMaterial [" + "name=" + name + ", file=" + file + ", videoTitle=" + videoTitle + ", videoIntroduction=" + videoIntroduction + "]";
  }
}
