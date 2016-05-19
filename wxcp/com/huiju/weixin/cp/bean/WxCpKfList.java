package com.huiju.weixin.cp.bean;

import java.io.Serializable;

import com.huiju.weixin.cp.util.json.WxCpGsonBuilder;

/**
 * 客服列表类
 *
 * @author Daniel Qian
 */
public class WxCpKfList implements Serializable {

  /**
   * 错误代码
   */
  private String errcode;
  /**
   * 错误信息
   */
  private String errmsg;  
  /**
   *  内部对象
   */
  private Internal internal = new Internal();
  /**
   *  外部对象
   */
  private Internal external = new Internal ();

	/**
	 * 这个方法的作用是将对象转化成Json格式数据
	 * @return 返回String类型数据
     */
  public String toJson() {
    return WxCpGsonBuilder.INSTANCE.create().toJson(this);
  }

	/**
	 * 这个方法的作用是将Json格式数据转化成WxCpKfList类对象
	 * @param json 格式数据
	 * @return WxCpKfList类对象
     */
  public static WxCpKfList fromJson(String json) {
    return WxCpGsonBuilder.INSTANCE.create().fromJson(json, WxCpKfList.class);
  }

    /**
	 * 这个方法的作用是获取错误代码
	 * @return 返回String类型变量
	 */
    public String getErrcode() {
	   return errcode;
    }

	/**
	 * 这个方法的作用是设置错误代码
	 * @param errcode 错误代码
     */
    public void setErrcode(String errcode) {
	   this.errcode = errcode;
    }

	/**
	 * 这个方法的作用是获取错误信息
	 * @return 返回String类型变量
     */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * 这个方法的作用是设置错误信息
	 * @param errmsg 错误信息
     */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * 这个方法的作用是获取内部对象
	 * @return 返回Internal类型对象
     */
	public Internal getInternal() {
		return internal;
	}

	/**
	 * 这个方法的作用是设置内部对象
	 * @param internal 内部对象
     */
	public void setInternal(Internal internal) {
		this.internal = internal;
	}

	/**
	 * 这个方法的作用是获取外部对象
	 * @return 返回Internal类型对象
     */
	public Internal getExternal() {
		return external;
	}

	/**
	 * 这个方法的作用是设置外部对象
	 * @param external 外部对象
     */
	public void setExternal(Internal external) {
		this.external = external;
	}

	/**
	 * 内部对象类.
	 */
	public static class Internal {
		/**
		 * 内部用户集合
		 */
	    private String[] user;
		/**
		 * 内部组织集合
		 */
	    private Integer[] party;
		/**
		 * 内部标签集合
		 */
	    private Integer[] tag;

		/**
		 * 重载构造函数
		 * @param user 内部用户集合
		 * @param party 内部组织集合
         * @param tag 内部标签集合
         */
	    public Internal(String[] user, Integer[] party,Integer[] tag) {
	      this.user = user;
	      this.party = party;
	      this.tag = tag;
	    }

		/**
		 * 默认构造函数
		 */
	    public Internal() { 
	    }

		/**
		 * 这个方法的作用是获取用户集合
		 * @return 返回String类型数组
         */
		public String[] getUser() {
			return user;
		}

		/**
		 * 这个方法的作用是设置内部用户集合
		 * @param user 内部用户
         */
		public void setUser(String[] user) {
			this.user = user;
		}

		/**
		 * 这个方法的作用是获取内部组织
		 * @return 返回Integer类型数组
         */
		public Integer[] getParty() {
			return party;
		}

		/**
		 * 这个方法的作用是设置内部组织集合
		 * @param party 内部组织
         */
		public void setParty(Integer[] party) {
			this.party = party;
		}

		/**
		 * 这个方法的作用是获取内部标签
		 * @return 返回Integer类型数组
         */
		public Integer[] getTag() {
			return tag;
		}

		/**
		 * 这个方法的作用是设置内部标签集合
		 * @param tag 内部标签
         */
		public void setTag(Integer[] tag) {
			this.tag = tag;
		}
	      
	  }
   
}
