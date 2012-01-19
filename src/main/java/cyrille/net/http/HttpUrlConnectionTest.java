package cyrille.net.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class HttpUrlConnectionTest {
    
    @Test
    public void test() throws Exception {
        URL url = new URL("http://www.sfr.fr/cas/parameters.jsp");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.connect();
        dumpConnection(connection);
    }
    
    private void dumpConnection(URLConnection connection) throws Exception {
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
}
