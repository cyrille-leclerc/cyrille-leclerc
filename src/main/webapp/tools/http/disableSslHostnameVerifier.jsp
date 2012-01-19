<%@ page
	import="java.io.*,java.net.*,java.util.*,javax.net.ssl.*,java.security.cert.*,java.sql.Timestamp"%>
<%
    HostnameVerifier disabledHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            
            if (!hostname.equals(session.getPeerHost())) {
                String peerPrincipal;
                try {
                    peerPrincipal = "" + session.getPeerPrincipal();
                } catch (SSLPeerUnverifiedException e) {
                    peerPrincipal = "" + e;
                }
                
                System.err.println("ERROR HttpsURLConnection.defaultHostnameVerifier"
                                   + " IGNORE illegal hostname mismatch hostname=" + hostname + ", sslSession.peerHost="
                                   + session.getPeerHost() + ", sslSession.peerPort=" + session.getPeerPort()
                                   + ", sslSession.peerPrincipal=" + peerPrincipal + ") session=" + session);
            }
            return true;
        }
    };
    HttpsURLConnection.setDefaultHostnameVerifier(disabledHostnameVerifier);
%>