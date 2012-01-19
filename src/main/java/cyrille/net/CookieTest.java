package cyrille.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.Validate;
import org.junit.Test;
import org.springframework.util.Assert;

public class CookieTest {
    
    @Test
    public void test() throws Exception {
        
        String hostName = "www.sfr.fr";
        String user = "0603097854";
        String password = "1111";
        String target = "http://" + hostName + "/cas/private/tools/parameters.jsp"; // "https://" + hostName + "/cas/private/resume-login";
        
        CookieManager cookieManager = new CookieManager() {
            @Override
            public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
                Map<String, List<String>> result = super.get(uri, requestHeaders);
                new Throwable().printStackTrace();
                System.out.println("get(uri=" + uri + ", requestHeaders=" + requestHeaders + ")" + result);
                return result;
            }
            
            @Override
            public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
                System.out.println("put(uri=" + uri + ", requestHeaders=" + responseHeaders + ")");
                new Throwable().printStackTrace();
                super.put(uri, responseHeaders);
            }
        };
        
        CookieManager.setDefault(cookieManager);
        
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
        // socketAddress = new InetSocketAddress("213.223.38.98", 8080);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        
        // Login.fcc
        {
            URL url = new URL("http://" + hostName + "/cas/private/login.fcc");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            String data = "user=" + URLEncoder.encode(user, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&target="
                          + URLEncoder.encode(target, "UTF-8");
            writer.write(data);
            writer.flush();
            
            // connection.setInstanceFollowRedirects(false);
            
            connection.connect();
            dumpConnection(connection);
            
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_MOVED_TEMP && responseCode != HttpURLConnection.HTTP_MOVED_PERM) {
                throw new IllegalStateException("Unexpected response code " + responseCode + ", expected "
                                                + HttpURLConnection.HTTP_MOVED_TEMP);
            }
            
            String location = connection.getHeaderField("Location");
            if (!target.equals(location)) {
                throw new IllegalStateException("Unexpected response redirection to '" + location + ", expected " + target);
            }
        }
        
        // parameters.jsp
        {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
            connection.setInstanceFollowRedirects(false);
            connection.connect();
            dumpConnection(connection);
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                String location = connection.getHeaderField("Location");
                if (!location.contains(hostName + "/cas/error?")) {
                    throw new IllegalStateException("Unexpected redirection to" + location + ". Expected redirection to 'http[s]://"
                                                    + hostName + "/cas/error?...'");
                }
                // TODO this is an error, extract errorcode
            }
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IllegalStateException("Unexpected response code " + responseCode + ", expected " + HttpURLConnection.HTTP_OK);
            }
            
            // TODO Success : extract SM_UID
            // TODO create a JSP to recopy http request headers into http response headers
            
        }
        
    }
    
    protected void dumpConnection(HttpURLConnection connection) throws IOException {
        
        System.out.println("Response Code : " + connection.getResponseCode());
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.print(entry.getKey() + "\t: ");
            for (String value : entry.getValue()) {
                System.out.print(value + ", ");
            }
            System.out.println();
        }
        
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection sslConnection = (HttpsURLConnection)connection;
            System.out.println("SSL CERTIFICATES");
            
            Certificate[] serverCertificates = sslConnection.getServerCertificates();
            for (Certificate certificate : serverCertificates) {
                System.out.println(certificate);
            }
        }
        
        System.out.println();
        System.out.println();
        System.out.println("RESPONSE BODY");
        System.out.println();
        System.out.println();
        InputStream in = connection.getInputStream();
        
        byte[] buffer = new byte[512];
        int length;
        while ((length = in.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, length));
        }
        
    }
}
