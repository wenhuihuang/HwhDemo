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
<title>Insert title here</title>
<script type="text/javascript" src="lib/js/jquery-1.11.3.js"></script>
<script type="text/javascript">
	$.ajax({
		type:'POST',
		url:'TestServlet/aa.do',
		success:function(msg){
			console.log(msg);
		},
		error:function(){
			alert("出错了！")
		}
	})
</script>
</head>
<body>
说到底
</body>
</html>