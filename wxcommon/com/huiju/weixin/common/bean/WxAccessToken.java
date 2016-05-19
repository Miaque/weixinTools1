package com.huiju.weixin.common.bean;

import java.io.Serializable;

import com.huiju.weixin.common.util.json.WxGsonBuilder;

/**
 * AccessToken类.
 * <p>AccessToken是企业号的全局唯一票据，调用接口时需携带AccessToken。<br>
 * @author zhengjf
 * @since 2016年5月15日 下午5:45:28
 *
 */
public class WxAccessToken implements Serializable {

	private static final long serialVersionUID = -7210899633107949748L;
  
	/**
	* 全局唯一票据，其值为 accessToken.
	*/ 
	private String accessToken;

	/**
	* 凭证的有效时间（秒）,其值为 expiresIn
	*/ 
	private int expiresIn = -1;

	/**
	* 该方法是用来获取凭证
	* @return 返回String类型变量
	*/ 
	public String getAccessToken() {
		return accessToken;
	}

	/**
	* 该方法是用来设置AccessToken
	* @param accessToken 设置凭证
	*/ 
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	* 该方法是用来获得凭证有效时间
	* @return 返回int类型变量
	*/ 
	public int getExpiresIn() {
		return expiresIn;
	}

	/**
	 * 该方法是用来设置凭证有效时间
	 * @param expiresIn 凭证有效时间
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	/**
	* 该方法是用来把获取到的信息转为WxAccessToken对象
	* @param json 获取到的信息 为json格式
	* @return WxAccessToken 返回类型 票据对象
	*/ 
	public static WxAccessToken fromJson(String json) {
		return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
	}

}
