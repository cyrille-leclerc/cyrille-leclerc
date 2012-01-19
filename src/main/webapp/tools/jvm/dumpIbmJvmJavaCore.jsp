<%@ page import="java.io.*"%>

<%@page import="java.lang.reflect.Method"%><html>
<body>
<%
    try {
        if ("true".equalsIgnoreCase(request.getParameter("doJob"))) {
            out.print("Dump java core (invoke com.ibm.jvm.Dump#JavaDump())...");
            out.flush();
            Class dumpClass = Class.forName("com.ibm.jvm.Dump");
            Method javaDumpMethod = dumpClass.getDeclaredMethod("JavaDump");
            javaDumpMethod.invoke(null);
            out.print("Java core dumped");
        }
    } catch (Exception e) {
        out.write("<pre>");        
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.write("</pre>");        
        printWriter.close();
    }
%>
<form method="post" action=""><input type="hidden" name="doJob"
	value="true" /> <input type="submit" value="Dump Java Core" /></form>
</body>
</html>