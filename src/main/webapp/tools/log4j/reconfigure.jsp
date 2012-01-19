<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.*" %>
<%@ page import="org.apache.log4j.xml.*" %>
<html>
	<head><title>Reconfigure Log4j</title></head>
<body>
<h1>Reconfigure Log4j</h1>
<%
try {
	String configurationAsString = request.getParameter("configuration");
	String checkXmlRadioButton = "";
	String checkPropertiesRadioButton = "";

	if(configurationAsString != null && configurationAsString.length() > 0){
		InputStream configurationAsInputStream = new ByteArrayInputStream(configurationAsString.getBytes());

		String type = request.getParameter("type");

		if("xml".equals(type)){
			// as there is no static method with an inputstream as parameter, instantiate DOMConfigurator
			LogManager.resetConfiguration();
			new DOMConfigurator().doConfigure(configurationAsInputStream, LogManager.getLoggerRepository());
			checkXmlRadioButton = "checked=\"checked\"";
		} else{
			Properties properties = new Properties();
			properties.load(configurationAsInputStream);
			LogManager.resetConfiguration();
			PropertyConfigurator.configure(properties);
			checkPropertiesRadioButton = "checked=\"checked\"";
		}
		%><h2>Log4j is reconfigured</h2><%
	} else {
		configurationAsString = "";
		checkPropertiesRadioButton = "checked=\"checked\"";
	}
	%>
	<form name="configurationEditor" action="reconfigure.jsp" method="post">
		<input type="radio" name="type" <%= checkPropertiesRadioButton %> value="properties"> properties file 
		<input type="radio" name="type" <%= checkXmlRadioButton %> value="xml"> xml file<br>
		<textarea name="configuration" cols="90" rows="20"><%= configurationAsString %></textarea><br>
		<input type="submit" value="Submit">
	</form>
<%
// As servlet engine may not display the full stack trace, catch Exception and do it
} catch(Throwable e){
	%><pre><%
	PrintWriter printWriter = new PrintWriter(out);
	e.printStackTrace(printWriter);
	%></pre><%
	printWriter.close();
}
%>
</body>
</html>