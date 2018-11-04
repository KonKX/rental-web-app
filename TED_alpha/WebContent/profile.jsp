<%@page import="java.util.*, bean.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Profile</title>
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
	background-color: #000;
	color: white;
	padding: 14px 20px;
	margin: auto;
	border: none;
	cursor: pointer;
	display: block;
	width: 30%;
}
</style>
</head>
<body>
	<%
		String username = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("user".equals(cookie.getName())) {
					username = cookie.getValue();
					break;
				}
			}
		}
	%>
	<div style="text-align: right">
		<%
			if (username != null)
				out.println("Logged as: " + username);
		%>
	</div>
	<h2 style="text-align: center">Profile Page</h2>
<% User user= (User)request.getAttribute("user"); %>
	<form action="/TED_alpha/Controller?action=updateprofile" method="post">
		<fieldset>
			<fieldset>
			<strong>Username:</strong> <%= user.getUsername()%><br> <input type="hidden" name="username" value=<%= user.getUsername()%>>
			<strong>Role:</strong> <%= user.getRole() %><br></fieldset><br>
			<strong>Password:</strong> <%= user.getPassword().replaceAll(".","*") %><br> <input type="password" name="password" placeholder="Enter a new password..."><br>
			<strong>Name:</strong> <%= user.getName()%><br> <input type="text" name="name" placeholder="Enter a new name..."><br>
			<strong>Surname:</strong> <%= user.getSurname()%><br> <input type="text" name="surname" placeholder="Enter a new surname..."><br>
			<strong>Email:</strong> <%= user.getEmail()%><br> <input type="text" name="email" placeholder="Enter a new email..."><br>
			<strong>Phone number:</strong> <%= user.getPhone()%><br> <input type="text" name="phone" placeholder="Enter a new phone number..."><br>
		</fieldset>
		<br><br>
		<% %><button type="submit">Update</button>
	</form>
</body>
</html>