package com.fg.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class ToolsUtils {

	public static String projectName = null;

	/**
	 * 获取项目名
	 * 
	 * @return
	 */
	public static String getProjectName() {
		String projectname = System.getProperty("user.dir");
		String pn = projectname.substring(projectname.lastIndexOf('\\') + 1, projectname.length());
		return pn;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            判断的对象
	 * @return 如果为空返回true，如果不为空返回false
	 */
	public static boolean checkIsNull(Object obj) {
		return obj == null || "".equals(obj.toString()) || "null".equals(obj.toString()) ? true : false;
	}

	/**
	 * 获取当前函数的函数名
	 * 
	 * @return
	 */
	public static String getCurrentMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		String methodName = e.getMethodName();
		return methodName;
	}

	/**
	 * 将日期转为指定格式字符串
	 * 
	 * @param format
	 * @param oriDate
	 * @return
	 * @throws ParseException
	 */
	public static String getDateStringByFormat(Object oriDate, String oriFormat, String newFormat)
			throws ParseException {
		oriFormat = oriFormat == null || "".equals(oriFormat) ? "yyyy-MM-dd HH:mm:ss" : oriFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(oriFormat);
		String result = sdf.format(oriDate);
		if (newFormat != null && !"".equals(newFormat)) {
			sdf = new SimpleDateFormat(newFormat);
			result = sdf.format(sdf.parse(result));
		}

		sdf = null;
		return result;
	}



	public static Timestamp getCurrentDateTimeStamp() {
		return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * 
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * 
	 * @param t
	 *            复制到的新文件
	 */

	public static void copyFile(File s, File t) {

		FileInputStream fi = null;

		FileOutputStream fo = null;

		FileChannel in = null;

		FileChannel out = null;

		try {

			fi = new FileInputStream(s);

			fo = new FileOutputStream(t);

			in = fi.getChannel();// 得到对应的文件通道

			out = fo.getChannel();// 得到对应的文件通道

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				fi.close();

				in.close();

				fo.close();

				out.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	/**
	 * 日期比较大小
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	
	
	
	public static List<String> printDay(String beginDate, String endDate) throws ParseException {
		
		List<String> days = new ArrayList<>();
		days.add(beginDate);
		DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

		Calendar startDay = Calendar.getInstance();
		startDay.setTime(FORMATTER.parse(beginDate));
		Calendar endDay = Calendar.getInstance();
		endDay.setTime(FORMATTER.parse(endDate));
		// 给出的日期开始日比终了日大则不执行打印
		if (startDay.compareTo(endDay) >= 0) {
			return days;
		}
		// 现在打印中的日期
		Calendar currentPrintDay = startDay;
		
		while (true) {
			
			// 日期加一
			currentPrintDay.add(Calendar.DATE, 1);
			// 日期加一后判断是否达到终了日，达到则终止打印
			if (currentPrintDay.compareTo(endDay) >= 0) {
				break;
			}
			// 打印日期
			// System.out.println(FORMATTER.format(currentPrintDay.getTime()));
			days.add(FORMATTER.format(currentPrintDay.getTime()));
		}

		
		if(!beginDate.equals(endDate)){
			days.add(endDate);
		}
		
		return days;
	}
	
	
	
	/**
	 * 获取UTF8字符串
	 * @param oriStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getUTF8String(String oriStr) throws UnsupportedEncodingException{
		return new String(oriStr.getBytes("ISO-8859-1"),"UTF-8");
	}
	
}
