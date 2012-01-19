<%
	String id = request.getParameter("id");
	System.out.println("page1 id=" + id);
%>
<html>
	<body>
		<table>
			<% for (int i = 0; i < 10 ; i++){ %>
				<tr><td><a href="page2.jsp?id=<%= id + i %>">user<%= i %></a></td></tr>
			<% } %>
		</table>
	</body>
</html>