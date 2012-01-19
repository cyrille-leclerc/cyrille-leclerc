<%@ page import="java.io.*"%>
<%@page import="java.lang.reflect.Method"%><html>
<body>
<%
    try {
        if ("true".equalsIgnoreCase(request.getParameter("doJob"))) {
            out.println("Dump java core (invoke com.ibm.jvm.Dump#HeapDump())...");
            out.flush();
            Class dumpClass = Class.forName("com.ibm.jvm.Dump");
            Method javaDumpMethod = dumpClass.getDeclaredMethod("HeapDump");
            javaDumpMethod.invoke(null);
            out.println("Java core dumped");
        }
    } catch (Exception e) {
        out.write("<pre>");
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.write("</pre>");
        printWriter.flush();
    }
%>
<form method="post" action=""><input type="hidden" name="doJob"
	value="true" /> <input type="submit" value="Dump Java Core" /></form>
</body>
</html>