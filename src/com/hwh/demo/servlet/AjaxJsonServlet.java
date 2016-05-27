package com.hwh.demo.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fg.servlet.FGServlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AjaxJsonServlet
 */
@WebServlet("/AjaxJsonServlet/*")
public class AjaxJsonServlet extends FGServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxJsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}
	
	/**
	 * 获取传过来的json数组字符串
	 * @param request
	 * @param response
	 */
	protected void jsonArray(HttpServletRequest request,HttpServletResponse response){
		String jsonStr = request.getParameter("data");//获取传过来的json数组字符串
		//System.out.println(jsonStr);
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);//转成json数组
		//System.out.println(jsonArray);
		for(int i = 0; i < jsonArray.size();i++){
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));//将json数组里面的每个对象字符串转成对象
			//System.out.println(jsonObj);
			//System.out.println(jsonObj.get("id"));//通过对象的键key来获取对应的值
		}
	}
	
	/**
	 * 获取传过来的json对象字符串
	 * @param request
	 * @param response
	 */
	protected void jsonObject(HttpServletRequest request,HttpServletResponse response){
		String jsonStr = request.getParameter("data");//获取传过来的json对象字符串
		//System.out.println(jsonStr);
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);//json字符串转成json对象
		//System.out.println(jsonObj);
		//System.out.println(jsonObj.get("name"));//通过get(key)来获取对象对应的值
	}
	
	/**
	 * 获取普通传过来的数据 
	 * @param request
	 * @param response
	 */
	protected void simple(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		//System.out.println(name);
		//System.out.println(id);
	}
	
	/**
	 * 接收传过来的对象里面对应的值
	 * @param request
	 * @param response
	 */
	protected void objSimple(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		//System.out.println(name);
		//System.out.println(id);
	}
	
	protected void formSerialize(HttpServletRequest request,HttpServletResponse response){
		Enumeration en = request.getParameterNames();  
		while(en.hasMoreElements()){  
		    String el = en.nextElement().toString();  
		    System.out.println("||||"+el+"="+request.getParameter(el));  
		} 
	}

}
