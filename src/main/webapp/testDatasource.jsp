<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<html>
<head>
<title>DataSource via jndi</title>
</head>
<body>
<h1>DataSource via jndi</h1>
<%= new java.sql.Timestamp(System.currentTimeMillis()) %>
<!-- <%= System.currentTimeMillis() %> -->
<%
            String jndiPath = request.getParameter("jndiPath");
            String sql = request.getParameter("sql");
            String dataSourceAsString = null;
            String connectionAsString = null;
            String errorAsString = "";
            if (jndiPath == null || jndiPath.length() == 0) {
            	errorAsString = "request parameter jndiPath is empty";
                jndiPath = "";
            } else {
                try {
                    Properties properties = new Properties();
                    InitialContext context = new InitialContext(properties);
                    DataSource dataSource = (DataSource) context.lookup(jndiPath);
                    dataSourceAsString = dataSource.toString();
                    Connection connection = dataSource.getConnection();
                    connectionAsString = connection.toString();
                    ResultSet rst = connection.createStatement().executeQuery(sql);
                } catch (Throwable e) {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter writer = new PrintWriter(stringWriter);
                    writer.println("<code><pre>");
                    e.printStackTrace(writer);
                    e.printStackTrace();
                    writer.println();
                    writer.println("</pre></code>");
                    writer.close();
                    errorAsString = stringWriter.toString();
                }
            }
%>

<form method="GET" action="">
<table>
	<tr>
		<th>JNDI path</th>
		<td><input type="text" name="jndiPath" value="<%= jndiPath %>"></td>
	</tr>
	<tr>
		<th>dataSourceAsString</th>
		<td><%= dataSourceAsString %></td>
	</tr>
	<tr>
		<th>connectionAsString</th>
		<td><%= connectionAsString %></td>
	</tr>
	<tr>
		<th>SQL</th>
		<td><input type="text" name="sql" value="<%= sql %>" size="100" /></td>
	</tr>
	<tr>
		<th>Erreur</th>
		<td><%= errorAsString %></td>
	</tr>
</table>
<input type="submit" value="Submit" /></form>
</body>
</html>
