<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>用户管理</h3>
	<form action="api/myresource/user2" method="post" >
		<p>姓名:<input name="name" /></p>
		<p>年龄:<input name="age" /></p>
		<p>地址:<input name="address" /></p>
		<p><input type="submit" value="保存"/></p>
	</form>
</body>
</html>