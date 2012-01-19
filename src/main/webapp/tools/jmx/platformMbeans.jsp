<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="javax.management.*"%>
<%@ page import="java.io.*,java.util.*"%>
<%@page import="java.net.InetAddress"%>
<%!public void dumpMbean(ObjectName objectName, MBeanServer mbeanServer, JspWriter out) throws Exception {
        MBeanInfo mbeanInfo = mbeanServer.getMBeanInfo(objectName);
        MBeanAttributeInfo[] attributeInfos = mbeanInfo.getAttributes();
        Arrays.sort(attributeInfos, new Comparator<MBeanAttributeInfo>() {
            public int compare(MBeanAttributeInfo o1, MBeanAttributeInfo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        out.println("<h1>" + objectName + "</h1>");
        out.println("<table border='1'>");
        
        out.println("<tr><th>Name</th><td>Value</td><td>Type</td><td>Description</td></tr>");
        for (MBeanAttributeInfo attributeInfo : attributeInfos) {
            out.println("<tr>");
            out.println("<th>" + attributeInfo.getName() + "</th>");
            out.println("<td>");
            if (attributeInfo.isReadable()) {
                Object value = mbeanServer.getAttribute(objectName, attributeInfo.getName());
                if (value == null) {
                    out.println("#NULL#");
                } else if (value.getClass().isArray() && !value.getClass().getComponentType().isPrimitive()) {
                    dumpArray((Object[])value, out);
                } else if (value instanceof TabularDataSupport) {
                    dumpTabularDataSupport((TabularDataSupport)value, out);
                } else if (value instanceof CompositeDataSupport) {
                    out.println("<table border='1'><tr>");
                    dumpCompositeDataSupport((CompositeDataSupport)value, out);
                    out.println("</tr></table>");
                } else {
                    out.println(value);
                }
            } else {
                out.println("#NOT_READABLE#");
            }
            out.println("</td>");
            out.println("<td>" + attributeInfo.getType() + "</td>");
            out.println("<td>" + attributeInfo.getDescription() + "</td>");
            out.println("</tr>");
        }
        out.flush();
        out.println("</table>");
    }
    
    public void dumpArray(Object[] values, JspWriter out) throws Exception {
        for (Iterator it = Arrays.asList(values).iterator(); it.hasNext();) {
            out.print(it.next());
            if (it.hasNext()) {
                out.print("<br/>");
            }
        }
    }
    
    public void dumpTabularDataSupport(TabularDataSupport tabularDataSupport, JspWriter out) throws Exception {
        out.println("<table border='1'>");
        for (Map.Entry<Object, Object> entry : tabularDataSupport.entrySet()) {
            out.println("<tr>");
            CompositeDataSupport compositeDataSupport = (CompositeDataSupport)entry.getValue();
            dumpCompositeDataSupport(compositeDataSupport, out);
            out.println("</tr>\r\n");
        }
        out.println("</table>");
        
    }
    
    public void dumpCompositeDataSupport(CompositeDataSupport compositeDataSupport, JspWriter out) throws Exception {
        for (Object val : compositeDataSupport.values()) {
            out.print("<td>");
            if (val.getClass().isArray() && !val.getClass().getComponentType().isPrimitive()) {
                dumpArray((Object[])val, out);
            } else {
                out.println(val);
            }
            out.print("</td>");
        }
    }%>

<%@page import="javax.management.openmbean.TabularDataSupport"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="javax.management.openmbean.CompositeDataSupport"%>
<%@page import="org.apache.commons.lang.builder.ToStringBuilder"%><html>
<head>
<title>MBean</title>
</head>
<body>
<h1>MBean</h1>
<%
    try {
        out.write("Server: " + InetAddress.getLocalHost() + ", date: "
                  + new java.sql.Timestamp(System.currentTimeMillis()).toString() + "<br>");
        
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        
        dumpMbean(new ObjectName("java.lang:type=Runtime"), mbeanServer, out);
        
        dumpMbean(new ObjectName("java.lang:type=OperatingSystem"), mbeanServer, out);
        dumpMbean(new ObjectName("java.lang:type=Threading"), mbeanServer, out);
        dumpMbean(new ObjectName("java.lang:type=Memory"), mbeanServer, out);
        
    } catch (Throwable e) {
        out.println("<pre>");
        PrintWriter printWriter = new PrintWriter(out);
        e.printStackTrace(printWriter);
        out.println("</pre>");
        printWriter.flush();
    }
%>
</body>
</html>