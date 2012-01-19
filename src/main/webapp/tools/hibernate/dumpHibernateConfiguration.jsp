
<%@page import="org.hibernate.engine.SessionFactoryImplementor"%>
<%@page import="org.hibernate.cfg.Settings"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="java.beans.Introspector"%>
<%@page import="java.beans.BeanInfo"%>
<%@page import="java.beans.PropertyDescriptor"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.hibernate.engine.SessionImplementor"%>
<%@page import="java.io.PrintWriter"%><html>
<head>
<title>Hibernate Configuration</title>
</head>
<body>
<h1>Hibernate Configuration</h1>
<%
    try {
        Enumeration<String> servletContextAttributeNames = application.getAttributeNames();
        
        while (servletContextAttributeNames.hasMoreElements()) {
            String servletContextAttributeName = servletContextAttributeNames.nextElement();
            Object servletContextAttribute = application.getAttribute(servletContextAttributeName);
            if (servletContextAttribute instanceof ApplicationContext) {
                
                ApplicationContext applicationContext = (ApplicationContext)servletContextAttribute;
                
                out.println("<h1>Spring Application context : " + applicationContext.getDisplayName() + "</h1>");
                
                out.println("<p>ServletContext.attribute : " + servletContextAttributeName + "</p>");
                out.println("<p>ApplicationContext : " + applicationContext + "</p>");
                
                String[] sessionImplementorNames = applicationContext.getBeanNamesForType(SessionFactoryImplementor.class);
                for (String sessionImplementorName : sessionImplementorNames) {
                    
                    SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)applicationContext
                        .getBean(sessionImplementorName, SessionFactoryImplementor.class);

                    out.println("<h2>SessionFactory : " + sessionImplementorName + "</h2>");
                    
                    out.println("<h3>SessionFactory Settings</h3>");
                    out.println("<table border='1'>");
                    Settings settings = sessionFactoryImplementor.getSettings();
                    
                    BeanInfo beanInfo = Introspector.getBeanInfo(Settings.class);
                    for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        if (readMethod == null) {
                            out.println("<tr><td><strong>" + propertyDescriptor.getName() + "</strong></td><td>#no readable#</td></tr>");
                        } else {
                            out.println("<tr><th>" + propertyDescriptor.getName() + "</th><td>" + readMethod.invoke(settings)
                                        + "</td></tr>");
                        }
                    }
                    out.println("</table>");
                }
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