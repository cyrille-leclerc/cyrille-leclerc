<%@ page import="java.io.*, java.sql.*, java.util.*, javax.sql.*, javax.naming.*" %>
<%@ page import="java.lang.reflect.Method" %>
<%
try {
	out.write("Date: " + new java.sql.Timestamp(System.currentTimeMillis()).toString() + "<br>");
	String jndiPath = request.getParameter("jndiPath");
	String providerUrl = request.getParameter("providerUrl");

	if(jndiPath == null || jndiPath.trim().length() == 0){
		out.write("Fill parameter jndiPath, providerUrl");
	} else {
		%>Connect ...<br><%
		out.flush();

		Properties props = new Properties();
		if(providerUrl != null && providerUrl.trim().length() > 0){
			out.write("providerUrl: '" + providerUrl + "'<br>");
			props.put(InitialContext.PROVIDER_URL, providerUrl);
		} else{
			out.write("no providerUrl set <br>");
		}
		props.put("com.ibm.CORBA.SendingContextRuntimeSupported", "true");
		InitialContext initialContext = new InitialContext(props);

		boolean displayInitialContextNameInNamespace = "true".equals(request.getParameter("displayInitialContextNameInNamespace"));
		if(displayInitialContextNameInNamespace){
			out.write("initialContext.nameInNamespace: " + initialContext.getNameInNamespace() + "<br>");
		}

		out.write("Jndi Path: " + jndiPath + "<br>");
		Object object = initialContext.lookup(jndiPath);
		out.write("Looked up object: <br>" + object);
		if(object != null){
			out.write("<p>Methods<br><ul>");
			Method[] methods = object.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				out.write("<li>" + method.toString() + "</li>");
			}
			out.write("</ul></p>");
		}
	}
// As servlet engine may not display the full stack trace, catch Exception and do it
} catch(Throwable e){
	out.println("<pre>");
	PrintWriter printWriter = new PrintWriter(out);
	e.printStackTrace(printWriter);
	out.println("</pre>");
	printWriter.flush();
}
%>