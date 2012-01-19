<%
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 1L);
response.setHeader("Cache-Control", "no-cache");
response.addHeader("Cache-Control", "no-store");
%>

Generation time : <%= new java.util.Date() %>