<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Register</title>
<style>
input[type=text], input[type=password], input[type=email] {
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
		String msg1 = (String) request.getAttribute("umessage");
	%>
	<%
		String msg2 = (String) request.getAttribute("pmessage");
	%>

	<h2 style="text-align: center">Sign up</h2>

	<form action="/TED_alpha/Controller?action=new account" method="post">
		<fieldset>
			<legend>Account</legend>
			<strong>Username</strong> <span style="color: red"> <%
 	if (msg1 != null)
 		out.println(msg1);
 %></span><br> <input type="text" name="username" required><br>
			<strong>Password</strong> <span style="color: red"> <%
 	if (msg2 != null)
 		out.println(msg2);
 %></span><br> <input type="password" name="password" required><br>
			<strong>Repeat password</strong><br> <input type="password"
				name="password_repeat" required><br>
		</fieldset>
		<fieldset>
			<br>
			<legend>Personal</legend>
			<strong>Name</strong><br> <input type="text" name="name"
				required><br> <strong>Surname</strong><br> <input
				type="text" name="surname" required><br> <strong>Email</strong><br>
			<input type="email" name="email" required><br> <strong>Phone
				number</strong><br> <input type="text" name="phone"><br>
		</fieldset>
		<br> <strong>Role</strong> <select name="role">
			<option>Tenant</option>
			<option>Host</option>
			<option>Both</option>
		</select><br>

		<button type="submit">Sign Up</button>

	</form>
</body>
</html>