
<%@page import="java.io.IOException"%><%@page
	import="javax.naming.InitialContext"%>
<%@page import="java.util.Properties"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="javax.naming.Binding"%>
<%@page import="javax.naming.NamingEnumeration"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="javax.naming.Context"%><%!private int MAX_DEPTH = 8;
    
    /**
     * Recursive method to dump a JNDI tree
     * 
     * @param cx
     * @param indent
     *            for display
     */
    private void dumpContext(Context cx, String indent, int depth, JspWriter out) throws IOException {
        NamingEnumeration<Binding> enu;
        try {
            enu = cx.listBindings("");
        } catch (NamingException e) {
            
            e.printStackTrace(new PrintWriter(out));
            return;
        }
        while (enu.hasMoreElements()) {
            Binding binding = (Binding)enu.nextElement();
            if (binding != null) {
                Object o = binding.getObject();
                if (o instanceof Context) {
                    if (depth > this.MAX_DEPTH && !("jdbc".equals(binding.getName()))) {
                        // Websphere 4.0.4 recursive context work around
                        out.println(indent + "+- " + binding.getName() + "- recursive context");
                    } else {
                        out.println(indent + "+- " + binding.getName());
                        dumpContext((Context)o, indent + "|   ", ++depth, out);
                    }
                    
                } else {
                    out.println(indent + "+- " + binding.getName() + "=" + (o == null ? "null" : o.getClass().getName()) + "\t" + o);
                }
            } else {
                out.println(indent + "+- #null binding#");
            }
        }
    }%>
<%
    response.setContentType("text/plain");
    try {
        Context context = (Context)new InitialContext(new Properties()).lookup("java:comp/env");
        dumpContext(context, "", 0, out);
    } catch (Throwable e) {
        out.println("<pre>");
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.println("</pre>");
        printWriter.flush();
    }
%>