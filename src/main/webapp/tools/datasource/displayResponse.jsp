<%@ page
	import="java.io.*,java.sql.*,java.util.*,javax.sql.*,javax.naming.*"%>
<html>
<body>
<h1>invocations to the given datasource executing "Select 1 from
dual" sql statement</h1>
<%
    try {
        String jndiPath = request.getParameter("jndiPath");
        
        if (jndiPath == null || jndiPath.trim().length() == 0) {
            out.write("Fill parameter jndiPath" + request.getRequestURL() + "?jndiPath=java/comp/env/...");
        } else {
            out.print("Connect ...<br>");
            out.flush();
            
            InitialContext initialContext = new InitialContext(new Properties());
            DataSource dataSource = (DataSource)initialContext.lookup(jndiPath);
            
            // gets driver info:
            Connection conn = dataSource.getConnection();
            try {
                DatabaseMetaData meta = conn.getMetaData();
%>
<h1>Connection Meta Data</h1>
<table border="1">
	<tr>
		<td>databaseProductName</td>
		<td><%=meta.getDatabaseProductName()%></td>
	</tr>
	<tr>
		<td>databaseProductVersion</td>
		<td><%=meta.getDatabaseProductVersion()%></td>
	</tr>
	<tr>
		<td>JdbcDriverName</td>
		<td><%=meta.getDriverName()%></td>
	</tr>
	<tr>
		<td>JdbcDriverVersion</td>
		<td><%=meta.getDriverVersion()%></td>
	</tr>
	<tr>
		<td>JdbcUrl</td>
		<td><%=meta.getURL()%></td>
	</tr>
	<tr>
		<td>DefaultTransactionIsolation</td>
		<td><%=meta.getDefaultTransactionIsolation()%></td>
	</tr>
	<tr>
		<td>MaxConnections</td>
		<td><%=meta.getMaxConnections()%></td>
	</tr>
	<tr>
		<td>ResultSetHoldability</td>
		<td><%=meta.getResultSetHoldability()%></td>
	</tr>
</table>

<h1>Connection Attributes</h1>
<table border="1">
	<tr>
		<td>Catalog</td>
		<td><%=conn.getCatalog()%></td>
	</tr>
	<tr>
		<td>AutoCommit</td>
		<td><%=conn.getAutoCommit()%></td>
	</tr>
	<tr>
		<td>Holdability</td>
		<td><%=conn.getHoldability()%></td>
	</tr>
	<tr>
		<td>TransactionIsolation</td>
		<td><%=conn.getTransactionIsolation()%></td>
	</tr>
</table>
<%
    Statement stmt = conn.createStatement();
                String sql;
                sql = "select 1 from dual";
                out.write(sql + " : ");
                ResultSet rst = stmt.executeQuery(sql);
                ResultSetMetaData metadata = rst.getMetaData();
                out.write("<table>");
                out.write("<tr>");
                for (int colIdx = 0; colIdx < metadata.getColumnCount(); colIdx++) {
                    out.write("<th>" + metadata.getColumnName(colIdx + 1) + "</th>");
                }
                out.write("</tr>");
                
                out.write("<tr>");
                while (rst.next()) {
                    for (int colIdx = 0; colIdx < metadata.getColumnCount(); colIdx++) {
                        out.write("<tr>" + rst.getString(colIdx + 1) + "</tr>");
                    }
                }
                out.write("</tr>");
                
                rst.close();
                stmt.close();
                out.write("</table>");

                out.print("sleep 1 s ...<br>");
                out.flush();
                Thread.sleep(1000);
                out.print("bye");

            } finally {
                conn.close();
            }
        }
    } catch (Exception e) {
        out.write("<pre>");
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.write("</pre>");
        printWriter.flush();
    }
%>
</body>
</html>