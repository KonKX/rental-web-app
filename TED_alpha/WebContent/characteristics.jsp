<%@page import="java.util.*, bean.*, database.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Room Characteristics</title>
<style>
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
		String role = null;
		String checkin = null;
		String checkout = null;
		String people = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				switch (cookie.getName()) {
					case ("user") :
						username = cookie.getValue();
					case ("role") :
						role = cookie.getValue();
					case ("checkin") :
						checkin = cookie.getValue();
					case ("checkout") :
						checkout = cookie.getValue();
					case ("people") :
						people = cookie.getValue();
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
	<br>
	<h2 style="text-align: center">Room Characteristics</h2>
	<br>
	<%
		request.setCharacterEncoding("utf-8");
		Listing listing = (Listing) request.getAttribute("listing");
	%>
	<p>
		<img src=<%=listing.getPictureUrl()%>
			style="display: block; margin: 0 auto">
	</p>
	<%
		if (role.equals("Tenant") || role.equals("Both")){
	%>
	<form action="/TED_alpha/Controller?action=book" method="post">
		<input type="hidden" name="username" value=<%= username %>>
		<input type="hidden" name="checkin" value=<%= checkin %>>
		<input type="hidden" name="checkout" value=<%= checkout %>>
		<input type="hidden" name="people" value= <%= people %>>
		<input type="hidden" name="id" value=<%=listing.getId() %>>
		<button type="submit">Book now!</button>
	</form>
	<%
		}
	%>
	<p>
		<strong>Room type:</strong>
		<%=listing.getRoomType()%></p>
	<p>
		<strong>Number of beds:</strong>
		<%=listing.getBeds()%></p>
	<p>
		<strong>Number of bathrooms:</strong>
		<%=listing.getBathrooms()%></p>
	<p>
		<strong>Space (in s.f):</strong>
		<%=listing.getSquareFeet().equals("") ? "Not available" : listing.getSquareFeet()%></p>
	<p>
		<strong>Description:</strong>
		<%=listing.getDescription()%></p>
	<p>
		<strong>List of amenities:</strong>
		<%=listing.getAmenities()%></p>
	<p>
		<strong>Minimum nights:</strong>
		<%=listing.getMinimumNights()%></p>
	<p>
		<strong>Street:</strong>
		<%=listing.getStreet()%></p>
	<p>
		<strong>Located in:</strong>
		<%=listing.getNeighbourhoodCleansed()%></p>
	<p>
		<strong>Getting around:</strong><%=listing.getTransit()%></p>
	<div id="map" style="width: 400px; height: 400px"></div>
	<p>
		<strong>Hosted by </strong><%=listing.getHostName()%>
		<img src=<%=listing.getHostThumbnailUrl()%>>
	</p>
	<script>
		function myMap() {
			var myCenter = new google.maps.LatLng(
	<%=listing.getLatitude()%>
		,
	<%=listing.getLongitude()%>
		);
			var mapCanvas = document.getElementById("map");
			var mapOptions = {
				center : myCenter,
				zoom : 15
			};
			var map = new google.maps.Map(mapCanvas, mapOptions);
			var marker = new google.maps.Marker({
				position : myCenter
			});
			marker.setMap(map);
		}
	</script>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqqMim0JRZ8tku45K_2sx34ND6H2PYCYc&callback=myMap"></script>

</body>
</html>