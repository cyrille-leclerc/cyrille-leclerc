<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.reflect.Method"%>
<%@ page import="javax.naming.InitialContext"%>

<%
                try {
                out.write("Date: " + new java.sql.Timestamp(System.currentTimeMillis()).toString()
                        + "<br>");
                String providerUrl = "corbaloc:iiop:mspvp533:2822,iiop:mspvp534:2822/";
                String jndiPath = "cell/clusters/PRDENAVAUTENACLU01/ejb/com/osa/mdsp/csp/srv/svinf/enabler/ejb/SrvAuthorizationEnabler";
%>
Connect ...
<br>
<%
                out.write("providerUrl: '" + providerUrl + "'<br>");
                out.write("Jndi Path: " + jndiPath + "<br>");

                out.flush();

                Properties props = new Properties();
                props.put(InitialContext.PROVIDER_URL, providerUrl);

                props.put("com.ibm.CORBA.SendingContextRuntimeSupported", "true");
                InitialContext initialContext = new InitialContext(props);
                Object object = initialContext.lookup(jndiPath);
                out.write("Looked up object: <br>" + object);
                if (object != null) {
                    out.write("<p>Methods<br><ul>");
                    Method[] methods = object.getClass().getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        Method method = methods[i];
                        out.write("<li>" + method.toString() + "</li>");
                    }
                    out.write("</ul></p>");
                }

                // As servlet engine may not display the full stack trace, catch Exception and do it
            } catch (Throwable e) {
%>
<pre>
<%
                PrintWriter printWriter = new PrintWriter(out);
                e.printStackTrace(printWriter);
%>
</pre>
<%
            printWriter.close();
            }
%>
