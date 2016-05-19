package com.huiju.weixin.common.util;

import javax.naming.InitialContext;

public class EjbUtil {
	public static <T> Object lookup(Class<T> beanClass) {
		Object o = null;
	    String remoteClassName = beanClass.getSimpleName();
        int remoteIndex = remoteClassName.lastIndexOf("Remote");
        String beanName = remoteClassName.substring(0, remoteIndex) + "Bean";
        try {
            InitialContext context = new InitialContext();
            Object bean = context.lookup(beanName + "#" + beanClass.getName());
            return  (T)bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return o;
	}
	
	public void init(){
		EjbUtil.lookup(EjbUtil.class);
	}
}
