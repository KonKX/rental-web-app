<%@page import="java.util.*, bean.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<title>Results</title>
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
	<br>
	<%!@SuppressWarnings("unchecked")%>
	<%
		List<Listing> list = (List<Listing>) request.getAttribute("list");
	%>

	<table id="example" class="display">
		<thead>
			<tr>
				<th>Image</th>
				<th>Price</th>
				<th>Room type</th>
				<th>Beds</th>
				<th>Reviews</th>
				<th>Score</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th>Image</th>
				<th>Price</th>
				<th>Room type</th>
				<th>Beds</th>
				<th>Reviews</th>
				<th>Score</th>
			</tr>
		</tfoot>
		<tbody>
			<%
				for (Listing listing : list) {
			%>
			<tr class="clickable-row"
				data-href="/TED_alpha/Controller?action=getcharacteristics&id=<%=listing.getId()%>">
				<td><img src=<%=listing.getThumbnailUrl()%>></td>
				<td><%=listing.getPrice()%></td>
				<td><%=listing.getRoomType()%></td>
				<td><%=listing.getBeds()%></td>
				<td><%=listing.getNumberOfReviews()%></td>
				<%
					int stars = listing.getReviewScoresRating() / 20;
						String rank = "";
						for (int i = 1; i <= stars; i++)
							rank += "*";
				%>
				<td><%=rank%></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
<script>
	$(document).ready(function() {
		$('#example').DataTable({
			"pagingType" : "full_numbers"
		});
	});
</script>
<script>
	$(document).ready(function() {
		$(".clickable-row").click(function() {
			window.location = $(this).data("href");
		});
	});
</script>
</html>