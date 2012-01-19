<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<!-- <%= new java.sql.Timestamp(System.currentTimeMillis()) %> -->
<!-- <%= System.currentTimeMillis() %> -->
<%
            String jndiPath = request.getParameter("jndiPath");
            String sql = "select CURRENT SCHEMA from SYSIBM.SYSDUMMY1";
            String schemaName;
            if (jndiPath == null || jndiPath.length() == 0) {
                schemaName = "request parameter jndiPath is empty";
                jndiPath = "";
            } else {
                try {
                    Properties properties = new Properties();
                    properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
                    properties.put(Context.PROVIDER_URL, "corbaloc:iiop:localhost:2809");
                    InitialContext context = new InitialContext(properties);
                    DataSource dataSource = (DataSource) context.lookup(jndiPath);
                    Connection connection = dataSource.getConnection();
                    ResultSet rst = connection.createStatement().executeQuery(sql);
                    rst.next();
                    schemaName = rst.getString(1);
                } catch (Throwable e) {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter writer = new PrintWriter(stringWriter);
                    writer.println("<code><pre>");
                    e.printStackTrace(writer);
                    writer.println();
                    writer.println("</pre></code>");
                    writer.close();
                    schemaName = stringWriter.toString();
                }
            }
%>
<html>
<head>
<title>DB2 DataSource current schema</title>
</head>
<body>
<h1>DB2 DataSource current schema</h1>
<form method="GET" action="">
<table>
	<tr>
		<th>JNDI path</th>
		<td><input type="text" name="jndiPath" value="<%= jndiPath %>"></td>
	</tr>
	<tr>
		<th>SQL</th>
		<td><%=sql%></td>
	</tr>
	<tr>
		<th>Current DB2 Schema</th>
		<td><%=schemaName%></td>
	</tr>
</table>
<input type="submit" value="Submit"/>
</form>
</body>
</html>
