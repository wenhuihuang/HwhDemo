package com.fg.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fg.utils.ToolsUtils;
/**
 * Servlet implementation class FGServlet
 */
//@WebServlet(displayName="FGServlet",name="FGServlet",urlPatterns = { "/FGS/*" })
@WebServlet("/FGServlet/*")
public abstract class FGServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected final String JSPTYPE = "JSPTYPE";
	private String prefix = "";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FGServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			ToolsUtils.projectName = request.getContextPath().replace("/", "");
			try {
				getCurrentRequestMethod(this, getPrefix(), request, response);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServletException(e);
			}
		
	}
	
	
	
	/**
	 * 获取清除的前缀
	 * @return
	 */
	protected String getPrefix(){
		WebServlet wsAnnotation = this.getClass().getAnnotation(WebServlet.class);
		
		String[] values = wsAnnotation.value();
		if(values!=null){
			return values[0].substring(0, values[0].lastIndexOf("/*"));
		}else{
			return "";
		}
			
		
	}
	

	
	
	
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
	protected void getCurrentRequestMethod(Object obj,String prefix, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//默认Url
		String defaultUrl = request.getRequestURI();

		//排除js
		if(defaultUrl.lastIndexOf(".js")==defaultUrl.length()-3){
			return;
		}
		
		//函数名
		String methodName = "";
		
		//截取字符串获得方法名
		if(ToolsUtils.checkIsNull(prefix)==false){
			methodName = defaultUrl.replace(this.getServletContext().getContextPath()+prefix, "");
		}
		
		//如果有.do则也截取
		if(methodName.indexOf(".do")>-1){
			methodName = methodName.substring(1, methodName.indexOf(".do"));
		}
		
		//System.out.println("调用函数："+methodName);
		
		Class clz = obj.getClass();
		
		//利用反射调用方法
		Method method = clz.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
		method.setAccessible(true);
		method.invoke(obj, request,response);
	}
	
	
	
	

	
	/**
	 * 根据参数名获取参数值
	 * @param req
	 * @return 
	 */
	protected String getParameterByName(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
	
	
	/**
	 * 从参数中提取对象
	 * @param request 请求
	 * @param clz  返回对象的Class
	 * @param encoding  原始字符编码   转码后字符编码 默认是 ISO-8859-1  转   UTF-8
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws ParseException 
	 */
	protected <T> T getObjectByParameter(HttpServletRequest request,Class<T> clz,String ... encoding) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException, NoSuchFieldException, SecurityException, ParseException{
		T result = null;
		
		Enumeration<String> names = request.getParameterNames();
		
		result = clz.newInstance();
		
		String oriEncoding = encoding==null||encoding.length<=0?"ISO-8859-1":encoding[0];
		String endEncoding = encoding==null||encoding.length<=1?"UTF-8":encoding[1];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			
			
			Field field =null; 
			
			try {
				//没有该属性
				field = clz.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				// TODO: handle exception
				continue;
			}
			
			
			field.setAccessible(true);
			Class<?> valueType = field.getType();
			String typeName = valueType.getName();
			
			
			String valueStr = request.getParameter(name);

			Object value = null;
			
			if("java.lang.String".equals(typeName)){
				//value = valueStr==null?null:new String(valueStr.getBytes(oriEncoding),endEncoding);
				value= valueStr;
			}else if("int".equals(typeName)||"java.lang.Integer".equals(typeName)){
				value = valueStr==null||"".equals(valueStr)?0:Integer.parseInt(valueStr);
			}else if("double".equals(typeName)||"java.lang.Double".equals(typeName)){
				value = valueStr==null||"".equals(valueStr)?0:Double.parseDouble(valueStr);
			}else if("java.sql.Timestamp".equals(typeName)){
				value = valueStr==null||"".equals(valueStr)?null: Timestamp.valueOf(valueStr);
			}else if("java.math.BigDecimal".equals(typeName)){
				value = valueStr==null||"".equals(valueStr)?0: new BigDecimal(valueStr);
			}else if("java.util.Date".equals(typeName)){
				value = valueStr==null||"".equals(valueStr)?null: sdf.parse(valueStr);
			}
			field.set(result, value);
			
		}
		
		return result;
	}
	
	/**
	 * 请求转发
	 * @param path
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected void forwardDispatcher(String path, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher(path).forward(request, response);
	}
	/**
	 * 重定向
	 * @param path
	 * @param response
	 * @throws IOException
	 */
	protected void sendRedirect(String path,HttpServletResponse response) throws IOException{
		response.sendRedirect(path);
	}
	
	
	
	
}
