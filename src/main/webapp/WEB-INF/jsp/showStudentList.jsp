<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
</head>

<body>
	<table border="1" align="center">
		<tr>
			<td>编号</td>
			<td>姓名</td>
			<td>年龄</td>
		</tr>
		<c:forEach items="${student}" var="stu">
			<tr>
				<td>${stu.id}</td>
				<td>${stu.name}</td>
				<td>${stu.age}</td>
			</tr>
		</c:forEach>
	</table>
<br>
	<a href="index.jsp">返回首页</a>
</body>
</html>
