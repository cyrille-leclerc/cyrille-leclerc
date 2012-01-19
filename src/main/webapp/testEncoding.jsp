<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%
            String myXml = "<root><child>יטא</child></root>";
            String myEncodedXml = URLEncoder.encode(myXml, "ISO-8859-1");

%>

<%@page import="java.nio.charset.Charset"%>
<html>

<body>

<form action="parameters.jsp" method="post" enctype="application/x-www-form-urlencoded"><br />

<input type="text" name="myEncodedXml" value="<%= myEncodedXml %>" /> </br>
<input type="text" name="myXmlAsString" value="<%= myXml %>" /> </br>
<input type="submit" name="submit" value="submit" /><br />

</form>
</body>

</html>
