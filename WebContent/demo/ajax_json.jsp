<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajax提交json和json数组到后台接收</title>
<script type="text/javascript" src="lib/js/jquery-1.11.3.js"></script>
<script>
	/* 提交json数组 */
	var arr = [{"id":1,"name":"张三","age":21},{"id":2,"name":"王五","age":32},{"id":3,"name":"老王","age":54}];
	$.ajax({
		type:'POST',
		url:'AjaxJsonServlet/jsonArray.do',
		async:true,
		cache:false,
		//dataType:'json'	,
		data:{data:JSON.stringify(arr)},//如果数组不转成json字符串，后台获取到的是null
		//processData: false,//设置 processData 选项为 false，防止自动转换数据格式。
		success:function(msg){
			console.log(msg);
		},
		error:function(){
			alert("网络出错！")
		}
		
	})
	/*提交json对象字符串*/
	var jsonObject = {"id":1,"name":"你好吗"};
	$.ajax({
		type:'POST',
		url:'AjaxJsonServlet/jsonObject.do',
		data:{data:JSON.stringify(jsonObject)},
		async:true,
		success:function(msg){
			console.log(msg);
		},
		error:function(){
			alert("网络出错了！")
		}
	})
	/*普通的传参到后台*/
	$.ajax({
		type:'POST',
		url:'AjaxJsonServlet/simple.do',
		async:true,
		data:'name=张三&id=12',//这里的值不用引号了的
		success:function(msg){
			console.log(msg);
		},
		error:function(){
			alert("网络出错了！");
		}
	})
	
	/*传对象到后台*/
	$.ajax({
		type:'POST',
		url:'AjaxJsonServlet/objSimple.do',
		data:{"id":13,"name":"你好啊"},
		success:function(){

		},
		error:function(){
			alert("网络出错 ！");
		}
	
	})
	$(function(){
		
		$("#sub").click(function(){
			var form = $("#form").serialize();
			console.log(form)
			$.ajax({
				type:'POST',
				url:'AjaxJsonServlet/formSerialize.do',
				data:form,
				success:function(){

				},
				error:function(){
					alert("网络出错！")
				}
			})
		})
	})
</script>
</head>
<body>
ajax提交json和json数组到后台接收
<form id="form">
	<p><input type="text" name="name" placeholder="请输入名称"></p>
	<p><input type="text" name="id" placeholder="请输入id"></p>
	<p><input type="radio" name="sex" value="男">男</p>
	<p><input type="radio" name="sex" value="女">女</p>
	<p><input type='checkbox' name="wan" value="足球">足球</p>
	<p><input type='checkbox' name="wan" value="篮球">篮球</p>
	<p><input type='checkbox' name="wan" value="乒乓球">乒乓球</p>
	<p><a href="javascript:;" id="sub">提交</a></p>
</form>
</body>
</html>