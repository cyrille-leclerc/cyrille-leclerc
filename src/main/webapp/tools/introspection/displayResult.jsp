<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.security.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@page import="java.security.ProtectionDomain"%>
<%@page import="java.security.CodeSource"%>
<%@page import="java.lang.annotation.Annotation"%>
<%!/**
     * get the location of a class
     * @param clazz
     * @return the jar file or path where a class was found
     */
    String getLocation(Class clazz) throws Exception {
        
        try {
            CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
            if (codeSource == null) {
                return "clazz.getProtectionDomain().getCodeSource() not found";
            }
            java.net.URL url = codeSource.getLocation();
            String location = url.toString();
            
            if (location.startsWith("jar")) {
                url = ((java.net.JarURLConnection)url.openConnection()).getJarFileURL();
                location = url.toString();
            }
            
            if (location.startsWith("file")) {
                java.io.File file = new java.io.File(url.getFile());
                return file.getAbsolutePath();
            } else {
                return url.toString();
            }
        } catch (Throwable t) {
            StringWriter stringWriter = new StringWriter();
            t.printStackTrace(new PrintWriter(stringWriter));
            return "<pre>" + stringWriter.toString() + "</pre>";
        }
    }%>
<%
    try {
        String className = request.getParameter("className");
        if (className == null || className.trim().length() == 0) {
            out.write("Select a class name : " + request.getRequestURL() + "?className=");
        } else {
            Class clazz = Class.forName(className);
            out.println("<h1>" + clazz.toString() + "</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Location</th><td>" + getLocation(clazz) + "</td></tr>");
            out.println("<tr><th>GenericSuperClass</th><td>" + clazz.getGenericSuperclass() + "</td></tr>");
            out.println("<tr><th>DeclaringClass</th><td>" + clazz.getDeclaringClass() + "</td></tr>");
            out.println("<tr><th>EnclosingClass</th><td>" + clazz.getEnclosingClass() + "</td></tr>");
            out.print("<tr><th>Annotations</th><td>" );
            for (Annotation annotation : clazz.getAnnotations()) {
                out.print(annotation.toString() + "<br/>");
            }
            out.println("</td></tr>");

            out.println("<tr><th>Generic Interfaces</th><td>" );
            for (Type genericInterface : clazz.getGenericInterfaces()) {
                out.print(genericInterface.toString() + "<br/>");
            }
            out.println("</td></tr>");
            out.println("</table>");
            
%>
<h1>Declared Constructors</h1>
<table border='1'>
	<tr>
		<th>Declared Constructors</th>
		<th>Annotations</th>
	</tr>
	<%
	    for (Constructor constructor : clazz.getDeclaredConstructors()) {
	                out.println("<tr>");
	                out.print("<td>" + constructor.toString() + "</td>");
	                out.print("<td>");
	                for (Annotation annotation : constructor.getAnnotations()) {
	                    out.print(annotation + "<br/>");
	                }
	                out.print("</td>");
	                out.print("</tr>");
	            }
	%>
</table>
<h1>Declared Fields</h1>
<table border='1'>
	<tr>
		<th>Declared Field</th>
		<th>Annotation</th>
	</tr>
	<%
	    for (Field field : clazz.getDeclaredFields()) {
	                out.println("<tr>");
	                out.print("<td>" + field.toString() + "</td>");
	                out.print("<td>");
	                for (Annotation annotation : field.getAnnotations()) {
	                    out.print(annotation + "<br/>");
	                }
	                out.print("</td>");
	                out.print("</tr>");
	            }
	%>
</table>
<table border='1'>
	<tr>
		<th>Field</th>
		<th>Annotation</th>
	</tr>
	<%
	    for (Field field : clazz.getDeclaredFields()) {
	                out.println("<tr>");
	                out.print("<td>" + field.toString() + "</td>");
	                out.print("<td>");
	                for (Annotation annotation : field.getAnnotations()) {
	                    out.print(annotation + "<br/>");
	                }
	                out.print("</td>");
	                out.print("</tr>");
	            }
	%>
</table>
<h1>Declared Methods</h1>
<table border='1'>
	<tr>
		<th>Declared Methods</th>
		<th>Annotations</th>
	</tr>
	<%
	    for (Method method : clazz.getDeclaredMethods()) {
	                out.println("<tr>");
	                out.println("<td>" + method.toString() + "</td>");
	                out.println("<td>");
	                for (Annotation annotation : method.getAnnotations()) {
	                    out.println(annotation + "<br/>");
	                }
	                out.println("</td>");
	                out.println("</tr>");
	            }
	%>
</table>
<h1>Methods</h1>
<table border='1'>
	<tr>
		<th>Methods</th>
		<th>Annotations</th>
	</tr>
	<%
	    for (Method method : clazz.getMethods()) {
	                out.println("<tr>");
	                out.println("<td>" + method.toString() + "</td>");
	                out.println("<td>");
	                for (Annotation annotation : method.getAnnotations()) {
	                    out.println(annotation + "<br/>");
	                }
	                out.println("</td>");
	                out.println("</tr>");
	            }
	%>
</table>
<%
    }
    } catch (Exception e) {
        // As servlet engine may not display the full stack trace, catch Exception and do it
        out.println("<pre>");
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.println("</pre>");
        printWriter.flush();
    }
%>