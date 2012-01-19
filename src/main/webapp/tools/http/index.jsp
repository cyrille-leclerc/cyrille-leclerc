<%@ page import="java.io.*,java.net.*,java.util.*,javax.net.ssl.*,java.security.cert.*,java.sql.Timestamp"%>
<%
    String urlAsString = request.getParameter("url");
    
    if (urlAsString == null || urlAsString.trim().length() == 0) {
%>
<form>
<p>URL : <input type="text" name="url" value="" /> Proxy : Host <input type="text" name="proxyHost" /> Port <input type="text"
   name="proxyPort" /></p>
<table>
   <tr>
      <th>Header</th>
      <th>Value</th>
   </tr>
   <tr>
      <td><input name="headerName" type="text" /></td>
      <td><input name="headerValue" type="text" /></td>
   </tr>
   <tr>
      <td><input name="headerName" type="text" /></td>
      <td><input name="headerValue" type="text" /></td>
   </tr>
   <tr>
      <td><input name="headerName" type="text" /></td>
      <td><input name="headerValue" type="text" /></td>
   </tr>
</table>
<p>Body : <textarea name="body" cols="80"></textarea></p>
<input type="submit" /></form>
<%
    } else {
        String proxyHost = request.getParameter("proxyHost");
        String proxyPortAsString = request.getParameter("proxyPort");
        String body = request.getParameter("body");
        String[] headerNames = request.getParameterValues("headerName");
        String[] headerValues = request.getParameterValues("headerValue");
        
        final List<String> messages = new ArrayList<String>();
        
        try {
            int connectTimeoutInMillis = 10000;
            int readTimeOutInMillis = 10000;
            response.setContentType("text/plain");
            URL url = new URL(urlAsString);
            HttpURLConnection connection;
            if (proxyHost == null || proxyHost.length() == 0) {
                
                connection = (HttpURLConnection)url.openConnection();
                URI uri = new URI(urlAsString);
                out.println("No proxy specified, default jvm proxies list will be used " + "( ProxySelector.getDefault().select('"
                            + uri + "') ) : " + ProxySelector.getDefault().select(uri));
            } else {
                int proxyPort;
                try {
                    proxyPort = Integer.parseInt(proxyPortAsString);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Exception parsing proxyPort '" + proxyPortAsString + "' :" + e.getMessage());
                }
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                connection = (HttpURLConnection)url.openConnection(proxy);
                out.println("Use specified proxy, " + proxy);
            }
            
            connection.setConnectTimeout(connectTimeoutInMillis);
            connection.setReadTimeout(readTimeOutInMillis);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // METHOD
            String method = (body == null || body.length() == 0) ? "GET" : "POST";
            connection.setRequestMethod(method);
            out.println();
            out.println("==================================================================================");
            out.println();
            out.println(connection.getRequestMethod() + " " + url.getPath());
            // HEADERS
            if (headerNames != null && headerValues != null) {
                for (int i = 0; i < headerNames.length; i++) {
                    String headerName = headerNames[i];
                    String headerValue = headerValues.length > i ? headerValues[i] : null;
                    if (headerName != null && headerName.length() > 0 && headerValue != null && headerValue.length() > 0) {
                        out.println("Header " + headerName + ": " + headerValue);
                        connection.addRequestProperty(headerName, headerValue);
                    }
                }
            }
            // BODY
            if (body != null && body.length() > 0) {
                out.println("Body : " + body);
                OutputStream connectionOutputStream = connection.getOutputStream();
                connectionOutputStream.write(body.getBytes("UTF-8"));
            }
            
            long startTime = System.currentTimeMillis();
            out.println();
            out.println(new Timestamp(System.currentTimeMillis()) + " connect to " + url + "...");
            out.flush();
            
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection)connection;
                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        boolean result = HttpsURLConnection.getDefaultHostnameVerifier().verify(hostname, session);
                        if (result == false) {
                            String peerPrincipal;
                            try {
                                peerPrincipal = "" + session.getPeerPrincipal();
                            } catch (SSLPeerUnverifiedException e) {
                                peerPrincipal = "" + e;
                            }
                            
                            messages.add("Rejected HostnameVerifier.verify(hostname=" + hostname + ", sslSession.peerHost="
                                         + session.getPeerHost() + ", sslSession.peerPort=" + session.getPeerPort()
                                         + ", sslSession.peerPrincipal=" + peerPrincipal + ") session=" + session);
                        }
                        return result;
                    }
                };
                httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            }
            connection.connect();

            out.println(new Timestamp(System.currentTimeMillis()) + " request duration : "
                        + (System.currentTimeMillis() - startTime) + "ms");
            out.println();
            out.println("==================================================================================");
            out.println();
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (entry.getKey() != null) {
                    out.print(entry.getKey() + "\t: ");
                }
                for (Iterator<String> itValues = entry.getValue().iterator(); itValues.hasNext();) {
                    String value = itValues.next();
                    out.print(value);
                    if (itValues.hasNext()) {
                        out.print(", ");
                    }
                }
                
                out.println();
            }
            
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection sslConnection = (HttpsURLConnection)connection;
                out.println("SSL CERTIFICATES");
                
                Certificate[] serverCertificates = sslConnection.getServerCertificates();
                for (Certificate certificate : serverCertificates) {
                    if(certificate instanceof X509Certificate) {
                        X509Certificate x509Certificate = (X509Certificate) certificate;
                        out.println("Expires on: " + x509Certificate.getNotAfter());
                        out.println("Issuer: " + x509Certificate.getIssuerX500Principal());
                        out.println("Subject: " + x509Certificate.getSubjectX500Principal());
                    } else {
                       out.println(certificate);
                    }
                }
            }
            
            out.println();
            InputStream in = connection.getInputStream();
            
            byte[] buffer = new byte[512];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(new String(buffer, 0, length));
            }
            out.println();
            out.println();
            out.println();
            out.println("TOTAL DURATION : " + (System.currentTimeMillis() - startTime) + "ms");
            // As servlet engine may not display the full stack trace, catch Exception and do it
        } catch (Throwable e) {
            
            if (!messages.isEmpty()) {
                out.println("Error Messages");
                for (String message : messages) {
                    out.println(message);
                }
                out.println();
            }
            
            PrintWriter printWriter = new PrintWriter(out);
            e.printStackTrace(printWriter);
            out.println();
            printWriter.flush();
        }
    }
%>