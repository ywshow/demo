package com.demo.util.springmvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
 
/**
 * SpringMVC date converter
 *
 */
public class DateConverter implements Converter<String, Date> {
	 private static final List<String> formarts = new ArrayList<String>(4);
	    static{
	        formarts.add("yyyy-MM");
	        formarts.add("yyyy-MM-dd");
	        formarts.add("yyyy-MM-dd HH:mm");
	        formarts.add("yyyy-MM-dd HH:mm:ss");
	    }
	    public Date convert(final String source) {
	        final String value = source.trim();
	        if ("".equals(value)) {
	            return null;
	        }
	        if(source.matches("^\\d{4}-\\d{1,2}$")){ 
	            return parseDate(source, formarts.get(0));
	        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
	            return parseDate(source, formarts.get(1));
	        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
	            return parseDate(source, formarts.get(2));
	        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
	            return parseDate(source, formarts.get(3));
	        }else {
	            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
	        }
	    }
	 
	    /**
	     * 功能描述：格式化日期
	     * 
	     * @param dateStr
	     *            String 字符型日期
	     * @param format
	     *            String 格式
	     * @return Date 日期
	     */
	    public Date parseDate(final String dateStr, final String format) {
	        Date date=null;
	        try {
	            final DateFormat dateFormat = new SimpleDateFormat(format);
	            date = dateFormat.parse(dateStr);
	        } catch (final Exception e) {
	        }
	        return date;
	    }
	    public static void main(final String[] args) {
	        System.err.println(new DateConverter().convert("2014-04"));
	    }
}

