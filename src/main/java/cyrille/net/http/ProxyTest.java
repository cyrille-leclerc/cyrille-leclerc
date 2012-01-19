/*
 * Created on Jun 7, 2006
 */
package cyrille.net.http;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

public class ProxyTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ProxyTest.class);
    }

    public void testProxy() throws Exception {
        HttpClient httpClient = new HttpClient();
        HostConfiguration hostConfiguration = httpClient.getHostConfiguration();
        hostConfiguration.setHost("www.google.com", 80);
        hostConfiguration.setProxy("my-proxy-host", 3128);
        httpClient.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials("my-proxy-login", "my-proxy-password"));

        GetMethod method = new GetMethod("/");

        try {
            httpClient.executeMethod(method);
            String response = method.getResponseBodyAsString();
            System.out.println(response);
        } finally {
            method.releaseConnection();
        }
    }
}
