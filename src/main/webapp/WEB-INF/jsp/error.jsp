<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
<style type="text/css">
.pp {
	color: red;
}

.p {
	margin-left: 200;
	color: green;
}

a {
	margin-left: 800;
	text-decoration: none;
}
</style>

</head>

<body>
	<font color="red" size="12px" class="pp">操作出现错误！</font>
	<br>
	<font class="p">错误详情:&nbsp;&nbsp; ${message}</font>
	<br>
	<a href="index.jsp">返回首页</a>
</body>
</html>
