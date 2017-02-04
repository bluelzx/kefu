package com.ukefu.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;


public class UKTools {
	private static MD5 md5 = new MD5();
	/**
	 * 当前时间+已过随机生成的 长整形数字
	 * @return
	 */
	public static String genID(){
		return Base62.encode(getUUID()).toLowerCase() ;
	}
	
	public static String genIDByKey(String key){
		return Base62.encode(key).toLowerCase() ;
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "") ;
	}
	
	public static String getContextID(String session){
		return session.replaceAll("-", "") ;
	}
	
	public static String md5(String str) {
		return md5.getMD5ofStr(md5.getMD5ofStr(str));
	}

	public static void copyProperties(Object source, Object target,String... ignoreProperties)  
	        throws BeansException {  
	  
	    Assert.notNull(source, "Source must not be null");  
	    Assert.notNull(target, "Target must not be null");  
	  
	    Class<?> actualEditable = target.getClass();  
	    PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);  
	    List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;  
	  
	    for (PropertyDescriptor targetPd : targetPds) {  
	        Method writeMethod = targetPd.getWriteMethod();  
	        if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {  
	            PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());  
	            if (sourcePd != null) {  
	                Method readMethod = sourcePd.getReadMethod();  
	                if (readMethod != null &&  
	                        ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {  
	                    try {  
	                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {  
	                            readMethod.setAccessible(true);  
	                        }  
	                        Object value = readMethod.invoke(source);  
	                        if(value != null){  //只拷贝不为null的属性 by zhao  
	                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {  
	                                writeMethod.setAccessible(true);  
	                            }  
	                            writeMethod.invoke(target, value);  
	                        }  
	                    }  
	                    catch (Throwable ex) {  
	                        throw new FatalBeanException(  
	                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);  
	                    }  
	                }  
	            }  
	        }  
	    }  
	}  
}
