package cyrille.net;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

public class UrlTest {
    
    @Test
    public void testFileUrl() throws Exception {
        String filePath = "c:/test-" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd.HHmmssS") + ".txt";
        File file = new File(filePath);
        file.deleteOnExit();
        
        FileWriter writer = new FileWriter(filePath);
        writer.append("Hello world");
        writer.close();
        
        URL url = new URL("file:///" + filePath);
        InputStream in = url.openStream();
        System.out.println("Read " + url);
        IOUtils.copy(in, System.out);
    }
    
    @Test
    public void testBasicAuth() throws Exception {
        URL url = new URL("http://www-devsf:8081/acegi-security-sfrcas-demo/protected/parameters.jsp");
        
        URLConnection connection = url.openConnection();
        String username = "osm";
        String password = "1111";
        String loginPassword = username + ":" + password;
        connection.setRequestProperty("Authorization", "Basic " + new sun.misc.BASE64Encoder().encode(loginPassword.getBytes()));
        connection.connect();
        dumpConnection(connection);
    }
    
    @Test
    public void testSslUrl() throws Exception {
        URL url = new URL("https://cas-server:10008");
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.connect();
        dumpConnection(connection);
    }
    
    private void dumpConnection(URLConnection connection) throws IOException, SSLPeerUnverifiedException {
        if (connection instanceof HttpURLConnection) {
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            
            System.out.println("Connection Response Message " + httpConnection.getResponseMessage());
        }
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.print(entry.getKey() + "\t: ");
            for (String value : entry.getValue()) {
                System.out.print(value + ", ");
            }
            System.out.println();
        }
        
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConnection = (HttpsURLConnection)connection;
            System.out.println("CipherSuite\t: " + httpsConnection.getCipherSuite());
            Certificate[] serverCertificates = httpsConnection.getServerCertificates();
            for (Certificate certificate : serverCertificates) {
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509Certificate = (X509Certificate)certificate;
                    System.out.println(x509Certificate.getSubjectX500Principal());
                } else {
                    System.out.println(certificate);
                }
            }
        }
        
        System.out.println();
        System.out.println("##################################################");
        System.out.println();
        InputStream in = connection.getInputStream();
        IOUtils.copy(in, System.out);
    }
    
    @Test(expected = UnknownHostException.class)
    public void testUnknownHostException() throws Exception {
        URL url = new URL("http://unknown-host/");
        url.openConnection().connect();
    }
    @Test(expected=ConnectException.class)
    public void testConnectException() throws Exception {
        URL url = new URL("http://localhost:12456/");
        url.openConnection().connect();
    }
}
