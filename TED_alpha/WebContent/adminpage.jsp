<%@page import="java.util.*, bean.*"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Control Panel</title>
<style>
button.accordion {
	background-color: #eee;
	color: #444;
	cursor: pointer;
	padding: 18px;
	width: 100%;
	text-align: left;
	border: none;
	outline: none;
	transition: 0.4s;
}

button.accordion.active, button.accordion:hover {
	background-color: #ddd;
}

div.panel {
	padding: 0 18px;
	background-color: white;
	display: none;
}
</style>


</head>
<body>
	<div style="text-align: center">
		<h2>List of users</h2>
		<p>Click on each user to show detailed info.</p>
		<p>
			<a href="/TED_alpha/index.jsp">Home page</a>
		</p>
	</div>
	<%!@SuppressWarnings("unchecked")%>
	<%
		List<User> list = (List<User>) request.getAttribute("list");
		int count = 0;
		for (User user : list) {
	%>
	<button class="accordion"><%=++count%>. Username:
		<%=user.getUsername()%></button>
	<div class="panel">
		<p>
			Password:
			<%=user.getPassword()%></p>
		<p>
			Name:
			<%=user.getName()%></p>
		<p>
			Surname:
			<%=user.getSurname()%></p>
		<p>
			Email:
			<%=user.getEmail()%></p>
		<p>
			Phone:
			<%=user.getPhone()%></p>
		<p>
			Role:
			<%=user.getRole()%></p>
		<%
			if (user.getRole().equals("Pending")) {
		%>
		<form action="/TED_alpha/Controller?action=confirm" method="post">
			<input type="hidden" name="username" value=<%=user.getUsername()%>>
			<button type="submit">Confirm</button>
		</form>
		<br>
		<%
			} else {
		%>
		<form action="#" method="post">
			<button type="button" disabled>Confirm</button>
		</form>
		<br>
		<%
			}
		%>
	</div>
	<%
		}
	%>
	<script>
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
			acc[i].onclick = function() {
				this.classList.toggle("active");

				/* Toggle between hiding and showing the active panel */
				var panel = this.nextElementSibling;
				if (panel.style.display === "block") {
					panel.style.display = "none";
				} else {
					panel.style.display = "block";
				}
			}
		}
	</script>
</body>
</html>