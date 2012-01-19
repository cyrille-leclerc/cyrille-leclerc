<%
System.out.println("hello");
Cookie cookie = new Cookie("wine", "vin de bordeaux");
cookie.setDomain(".wine.com");
cookie.setPath("/");
response.addCookie(cookie);
%>