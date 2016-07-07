<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>导航页</title>
<script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/js/jquery.form.js"></script>
</head>
<script>
	$(function() {
		$(".request1").click(function() {
			var value = $(".common").attr("value");
			$("#value1").val(value);
			$("#fm1").submit();
		});

		$("#request2").click(function() {
			var type = $(".value2").attr("type");//获取当前元素的第一个属性值
			var value = $(".value2").val();//获取当前元素的当前值
			alert(value);
			alert(type);
			$("#fm2").ajaxSubmit({				
				url : "/ajaxRequest",
				type : "post",
				data: {"common2":$(".common2").attr("value"),"value2":$(".value2").val()},
				dataType : "text",
				success : function(json) {
					//成功后刷新

					var obj = new Function("return" + json)();//转换后的JSON对象
					$("#value2").val(obj.name);
					//window.location.reload(true);
				}
			});
		});
	});
</script>
<body>
	<form action="${pageContext.request.contextPath}/insetInfo" method="post">
		姓名：<input type="text" id="name" name="name"><br> 
		年龄：<input type="text" id="age" name="age"><br> 
		<input type="submit" value="添加">
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/queryOne" method="post">
		查询的编号:<input type="text" id="id" name="id"><br> 
		<input type="submit" value="查询">
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/queryList" method="post">
		查询全部信息：<input type="submit" value="查询">
	</form>
	<br>
	<form action="${pageContext.request.contextPath}/requestCookie" method="post">
		保存cookie数据：<input type="submit" value="保存cookie">
	</form>
	<br>
	<form id="fm1" action="${pageContext.request.contextPath}/commonRequest" method="post">
		普通的js请求：<input type="text" class= "common" name="common" value="普通的js请求"/>
		<input type="text" id="value1" name="value1" value=""/>
		<input type="button" class= "request1" value="请求"/>
	</form>
	<br>
	<form id="fm2" method="post">
		Ajax的请求：
		<input type="text" id="common2" class= "common2" name="common2" value="Ajax的请求"/>
		<input type="text" id="value2" class= "value2" name="value2" value=""/>
		<input type="button" id= "request2" value="请求"/>
	</form>
	<br>
</body>
</html>
