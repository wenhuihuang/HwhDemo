package com.fg.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletUtils {
	
	/**
	 * 请求URL要去掉的前缀
	 * @param request
	 * @param prefix
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void getCurrentRequestMethod(Object obj,String prefix, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//默认Url
		String defaultUrl = request.getRequestURI();
		
		//函数名
		String methodName = "";
		
		if(!ToolsUtils.checkIsNull(prefix)){
			methodName = defaultUrl.replace(prefix, "");
		}
		
		if(methodName.indexOf(".do")>-1){
			methodName = methodName.substring(0, methodName.indexOf(".do"));
		}
		
		Class clz = obj.getClass();
		Method method = clz.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletRequest.class);
		method.invoke(obj, request,response);
	}
	
	public String getLocalPath() {
		String serverPath = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		serverPath = serverPath.substring(1, serverPath.indexOf("WEB-INF"));
		return serverPath;
	}

	public String getServerPath(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}

	
}
