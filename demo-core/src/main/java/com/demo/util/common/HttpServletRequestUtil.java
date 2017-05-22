package com.demo.util.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.demo.util.springmvc.DateConverter;

/**
 * HttpServletRequest相关工具类
 *
 */
public class HttpServletRequestUtil {
	
	/**
	 * 获取下载文件名
	 * @param name
	 * @param request
	 * @return
	 */
	public static String getAttachment(final String name, final HttpServletRequest request){
		try {
			final String userAgent = request.getHeader("User-Agent");
			if((userAgent!=null) && (userAgent.toLowerCase().indexOf("firefox")>1)){
				//火狐
				return new String(name.getBytes("GB2312"), "ISO-8859-1");
			}else{
				//非火狐
				return URLEncoder.encode(name, "UTF-8");
			}
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 参数自动转换成对象
	 * @param beanClass
	 * @param request
	 * @return
	 */
	public static <T> T toBean(final Class<T> clazz, final HttpServletRequest request) {
		try {
			final T obj = clazz.newInstance();
			final Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
			for (final Field field : fields) {
				final String fieldName = field.getName();
				final String parm = request.getParameter(field.getName());
				if(request.getParameter(field.getName()) != null){
					final Method method = ReflectionUtil.getAccessibleMethod(obj, "set" + StringUtils.capitalize(fieldName), field.getType());
					final Object convertObj = ReflectionUtil.convertType(parm, field.getType());
					method.invoke(obj, convertObj);
				}
			}
			return obj;
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	/**
	 * 参数自动转换成对象，并忽略前缀
	 * @param beanClass
	 * @param request
	 * @return
	 */
	public static <T> T toBean(final Class<T> clazz, final HttpServletRequest request, final String pre) {
		try {
			if(StringUtils.isBlank(pre)){
				throw new NullPointerException("Parameters prefix can not be empty");
			}
			final T obj = clazz.newInstance(); 
			final Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
			for (final Field field : fields) {
				final String fieldName = field.getName();
				final String parmName = pre + "." + fieldName;
				final String parm = request.getParameter(parmName);
				if(request.getParameter(field.getName()) != null){
					final Method method = ReflectionUtil.getAccessibleMethod(obj, "set" + StringUtils.capitalize(fieldName), field.getType());
					final Object convertObj = ReflectionUtil.convertType(parm, field.getType());
					method.invoke(obj, convertObj);
				}
			}
			return obj;
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
	/**
	 * 参数自动转换成对象序列
	 * @param beanClass
	 * @param request
	 * @return
	 */
	public static <T> List<T> toBeanList(final Class<T> clazz, final HttpServletRequest request) {
		try {
			final List<T> list = new ArrayList<T>();
			//Init list
			final Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
			for (final Field field : fields) {
				final String fieldName = field.getName();
				final String[] parmArr = request.getParameterValues(fieldName);
				if(parmArr != null){
					final int size = parmArr.length;
					for (int i = 0; i < size; i++) {
						final T obj = clazz.newInstance();
						list.add(obj);
					}
					break;
				}
			}
			//Set value
			for (final Field field : fields) {
				final String fieldName = field.getName();
				final String[] parmArr = request.getParameterValues(fieldName);
				if(parmArr != null){
					final int size = parmArr.length;
					for (int i=0; i<size; i++) {
						final String parm = parmArr[i];
						final Method method = ReflectionUtil.getAccessibleMethod(list.get(i), "set" + StringUtils.capitalize(fieldName), field.getType());
						if(StringUtils.isBlank(parm)){
							continue;
						}
						if (field.getType() == Date.class) {
							final Date date = new DateConverter().convert(parm);
							method.invoke(list.get(i), date);
						}else {
							final Object obj = ReflectionUtil.convertType(parm, field.getType());
							method.invoke(list.get(i), obj);
						}
					}
				}
			}
			return list;
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	static Comparator<String> comparator = new Comparator<String>(){  
		public int compare(final String s1, final String s2) {  
			 return s1.compareTo(s2);
		}  
    };
	
	/**
	 * 参数自动转换成对象序列，并忽略前缀
	 * @param clazz
	 * @param request
	 * @return
	 */
	public static <T> List<T> toBeanListIgnoreFix(final Class<T> clazz, final HttpServletRequest request) {
		try {
			final List<T> list = new ArrayList<T>();
			//Init list
			Enumeration<String> enumeration;
			final Field[] fields = concat(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields());
			//找到与参数匹配的属性
			for (final Field field : fields) {
				if(list.size() > 0){
					break;
				}
				enumeration = request.getParameterNames();
				final String fieldName = field.getName();
				for(final Enumeration e=enumeration; e.hasMoreElements();){
					final String reqName = e.nextElement().toString();
					final String[] arrs = splitParm(reqName);
					//找到request中和对象中相匹配的参数
					if((arrs.length==2) && fieldName.equals(arrs[1])){
						//统计request中以fieldName结尾的参数次数
						enumeration = request.getParameterNames();
						for(final Enumeration e1=enumeration; e1.hasMoreElements();){
							final String reqName1 = e1.nextElement().toString();
							final String[] arrs1 = splitParm(reqName1);
							if((arrs1.length==2) && reqName1.endsWith(fieldName)){
								final T obj = clazz.newInstance();
								list.add(obj);
							}
						}
						break;
					}
				}
			}
			//根据属性获得此属性在request中出现几次
			//获取参数  <参数名称, 去掉前缀后的值>
			enumeration = request.getParameterNames();
			final Map<String, String> parmMap = new LinkedHashMap();
			final List<String> mapKey = new ArrayList<String>();
			for(final Enumeration e=enumeration; e.hasMoreElements();){
				final String reqName = e.nextElement().toString();
				final String[] arrs = reqName.split("\\.");
				if(arrs.length == 2){
					parmMap.put(reqName, arrs[1]);
					mapKey.add(reqName);
				}
			}
			//排序
			Collections.sort(mapKey, comparator); 
			//遍历成员变量
			for (final Field field : fields) {
				final String fieldName = field.getName();
				final int listSize = list.size();
				//遍历List
				final Iterator<String> iterator = mapKey.iterator();
				for (int i = 0; i < listSize; i++) {
					while (iterator.hasNext()) {
						final String key = iterator.next();		
						final String val = parmMap.get(key);
						if(fieldName.equals(val)){
							final Method method = ReflectionUtil.getAccessibleMethod(list.get(i), "set" + StringUtils.capitalize(fieldName), field.getType());
							final String parmVal = request.getParameter(key);
							if(!StringUtils.isBlank(parmVal)){
								if (field.getType() == Date.class) {
									final Date date = new DateConverter().convert(parmVal);
									method.invoke(list.get(i), date);
								}else {
									final Object obj = ReflectionUtil.convertType(parmVal, field.getType());
									method.invoke(list.get(i), obj);
								}
							}
							iterator.remove();
							break;
						}
					}
				}
			}
			return list;
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
			
			
	}
	
	private static Field[] concat(final Field[] a, final Field[] b){
		final Field[] c = new Field[a.length+b.length];  
	    System.arraycopy(a, 0, c, 0, a.length);  
	    System.arraycopy(b, 0, c, a.length, b.length);  
	    return c;
	}
	
	private static String[] splitParm(final String parm){
		final String[] arrs = parm.split("\\.");
		return arrs;
	}
	
}
