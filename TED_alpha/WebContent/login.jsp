<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login</title>
<style>
input[type=text], input[type=password] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

button {
	background-color: #87CEEB;
	color: white;
	padding: 14px 20px;
	margin: auto;
	border: none;
	cursor: pointer;
	display: block;
	width: 30%;
	font-size:16px;
}
</style>
</head>
<body>

	<%
		String msg = (String) request.getAttribute("message");
	%>

	<h2 style="text-align: center">Login</h2>
	<form action="/TED_alpha/Controller?action=login" method="post">
		<fieldset>
			Username<br> <input type="text" name="username" required><br>
			Password<br> <input type="password" name="password" required><br>
		</fieldset>
		<br><br>
		<button type="submit">Login</button>
	</form>

	<p style="color: red">
		<%
			if (msg != null)
				out.println(msg);
		%>
	</p>
</body>
</html>