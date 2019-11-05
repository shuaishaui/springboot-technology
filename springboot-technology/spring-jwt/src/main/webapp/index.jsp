<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">

function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	var params = "username="+username+"&password="+password;
	$.ajax({
		'url' : '${pageContext.request.contextPath }/login',
		'data' : params,
		'success' : function(data){
			if(data.code == 200){
				var token = data.token;
				// web storage的查看 - 在浏览器的开发者面板中的application中查看。
				// local storage - 本地存储的数据。 长期有效的。
				// session storage - 会话存储的数据。 一次会话有效。
				var localStorage = window.localStorage; // 浏览器提供的存储空间。 根据key-value存储数据。
				localStorage.token = token;
			}else{
				alert(data.msg);
			}
		}
	});
}

function setHeader(xhr){ // XmlHttpRequest
	xhr.setRequestHeader("Authorization",window.localStorage.token);
}

function testLocalStorage(){
	$.ajax({
		'url' : '${pageContext.request.contextPath}/testAll',
		'success' : function(data){
			if(data.code == 200){
				window.localStorage.token = data.token;
				alert(data.data);
			}else{
				alert(data.msg);
			}
		},
		'beforeSend' : setHeader
	});
}

</script>
</head>
<body >
	<center>
		<table>
			<caption>登录测试</caption>
			<tr>
				<td style="text-align: right; padding-right: 5px">
				登录名：
				</td>
				<td style="text-align: left; padding-left: 5px">
				<input type="text" name="username" id="username"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 5px">
				密码：
				</td>
				<td style="text-align: left; padding-left: 5px">
				<input type="text" name="password" id="password"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right; padding-right: 5px" colspan="2">
				<input type="button" value="登录" onclick="login();" />
				</td>
			</tr>
		</table>
	</center>
	<input type="button" value="testLocalStorage" onclick="testLocalStorage();"/>
</body>
</html>