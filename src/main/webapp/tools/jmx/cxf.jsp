<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="javax.management.*"%>
<%@ page import="java.io.*,java.util.*"%>
<%@page import="java.net.InetAddress"%>
<%!public void dumpMbeans(Set<ObjectInstance> objectInstances, MBeanServer mbeanServer, JspWriter out) throws Exception {
        out.write("<table border='1'>");
        MBeanAttributeInfo[] beanAttributeInfos = null;
        for (ObjectInstance objectInstance : objectInstances) {
            ObjectName objectName = objectInstance.getObjectName();
            
            if (beanAttributeInfos == null) {
                beanAttributeInfos = mbeanServer.getMBeanInfo(objectName).getAttributes();
                out.write("<tr><th>Object Name</th>");
                for (MBeanAttributeInfo mbeanAttributeInfo : beanAttributeInfos) {
                    out.print("<th>" + mbeanAttributeInfo.getName() + "<br/>" + mbeanAttributeInfo.getDescription() + "</th>");
                }
                out.write("</tr>");
            }
            
            out.write("<tr><td>" + objectName + "</td>");
            for (MBeanAttributeInfo mbeanAttributeInfo : beanAttributeInfos) {
                out.write("<td>" + mbeanServer.getAttribute(objectName, mbeanAttributeInfo.getName()) + "</td>");
            }
            out.write("</tr>\r\n");
            out.flush();
        }
        
        out.write("</table>");
        
    }%>

<html>
<head>
<title>CXF Response time</title>
</head>
<body>
<h1>CXF Response time</h1>
<%
    try {
        long startime = (Long) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("java.lang:type=Runtime"), "StartTime");
        out.write("time: " + new java.sql.Timestamp(System.currentTimeMillis()).toString() + ", start-time: " + new java.sql.Timestamp(startime).toString() +"<br>");
        
        List<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(null);
        for (MBeanServer mbeanServer : mbeanServers) {
            
            out.println("<h1> MbeanServer domain = " + mbeanServer.getDefaultDomain() + "</h1>");
            {
                out.println("<h2>CXF Server Endpoints</h2>");
                Set<ObjectInstance> objectInstances = mbeanServer
                    .queryMBeans(new ObjectName("org.apache.cxf:type=Performance.Counter.Server,*"), null);
                dumpMbeans(objectInstances, mbeanServer, out);
            }
            
            {
                out.println("<h2>CXF Clients</h2>");
                Set<ObjectInstance> objectInstances = mbeanServer
                    .queryMBeans(new ObjectName("org.apache.cxf:type=Performance.Counter.Client,*"), null);
                dumpMbeans(objectInstances, mbeanServer, out);
            }
        }
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