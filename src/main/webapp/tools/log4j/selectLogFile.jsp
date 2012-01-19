<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%
try {
	String propertiesPath = "/com/osa/mdsp/properties/log4j.properties";
	URL log4jPropertiesUrl = Thread.currentThread().getContextClassLoader().getResource(propertiesPath);
	if(log4jPropertiesUrl == null){
		log4jPropertiesUrl = getClass().getResource(propertiesPath);
	}
	%>
	<form name="fileChooser" action="displayLogFile.jsp" target="displayLogFile">
		<span title="<%= log4jPropertiesUrl %>">Mouse over for log4j.properties</span>
		<select name="filePath" onChange="return document.fileChooser.submit();">
			<option value="">-- Select a file --</option>
			<option value="<%= log4jPropertiesUrl.getFile() %>">log4j.properties</option>
			<%
			Properties properties = new Properties();
			properties.load(log4jPropertiesUrl.openStream());
			Set keys = properties.keySet();
			for (Iterator itKeys = keys.iterator(); itKeys.hasNext();) {
				String key = (String) itKeys.next();
				if(key.startsWith("log4j.appender.") && key.endsWith(".File")){
					%><option><%= properties.getProperty(key) %></option><%
				}
			}
			%>
		</select><input type="submit" value="display">
	</form>
<%
// As servlet engine may not display the full stack trace, catch Exception and do it
} catch(Exception e){
	%><pre><%
	PrintWriter printWriter = new PrintWriter(out);
	e.printStackTrace(printWriter);
	%></pre><%
	printWriter.close();
}
%>