<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home Rentals</title>
<style>
input[type=text] {
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
			if (username != null) {
				out.println("Logged as: " + username);
		%>
		<br> <a
			href="/TED_alpha/Controller?action=profile&user=<%=username%>">Edit
			profile</a>
		<%
			}
		%>
	</div>

	<div style="text-align: center">
		<h1>Airbnb Application</h1>
		<br> <a href="/TED_alpha/Controller?action=login">Login</a> | <a
			href="/TED_alpha/Controller?action=register">Sign Up</a>
	</div>
	<form action="/TED_alpha/Controller?action=getresults" method="post">
		<br> Location<br> <input type="text" name="location"
			placeholder="Enter location..."><br> Check In<br> <input
			type="text" name="checkin" placeholder="Enter check in date..."><br>
		Check Out<br> <input type="text" name="checkout"
			placeholder="Enter check out date..."><br> Guests<br>
		<input type="text" name="people"
			placeholder="Enter number of guests..."><br> <br> <br>
		<button type="submit">Search</button>
	</form>

</body>
</html>