/*
 * Created on Jan 2, 2006
 */
package cyrille.net.ssl;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.junit.Test;

public class SslTest {
    
    @Test
    public void testSslSocketFactory() throws Exception {
        System.out.println("SslTest.testSslSocketFactory()");
        SSLSocketFactory socketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        System.out.println(socketFactory.toString());
        String[] ciphersuites = socketFactory.getDefaultCipherSuites();
        for (String cipherSuite : ciphersuites) {
            System.out.println(cipherSuite);
        }
    }
    
    @Test
    public void testDisableCertificateValidation() throws Exception {
        HttpsURLConnection.setDefaultSSLSocketFactory(new EasySSLSocketFactory());
        
        // Now you can access an https URL without having the certificate in the truststore
        URL url = new URL("https://regie-dev.sofialys.com/optin_sms/index.php");
        
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
        httpsURLConnection.connect();
        
    }
}
