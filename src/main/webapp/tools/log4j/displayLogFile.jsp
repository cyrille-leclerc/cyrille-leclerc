<%@ page import="java.io.*, java.util.*" %><%
try {
	String filePath = request.getParameter("filePath");
	if(filePath == null || filePath.trim().length() == 0){
		out.write("Select a file, parameter: filePath");
	} else {
		response.setContentType("text/plain");
		InputStream in;
		if(filePath.endsWith("log4j.properties")){
			String propertiesPath = "/com/mycompany/properties/log4j.properties";
			in = getClass().getResourceAsStream(propertiesPath);
		} else {
			in = new FileInputStream(filePath);
		}
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[512];
		int length;
		while ((length = in.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
	}
// As servlet engine may not display the full stack trace, catch Exception and do it
} catch(Exception e){
	%><pre><%
	PrintWriter printWriter = new PrintWriter(out);
	e.printStackTrace(printWriter);
	%></pre><%
	printWriter.close();
}
%>