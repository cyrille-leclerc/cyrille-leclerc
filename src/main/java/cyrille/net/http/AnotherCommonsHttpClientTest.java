/*
 * Created on Mar 18, 2005
 */
package cyrille.net.http;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class AnotherCommonsHttpClientTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AnotherCommonsHttpClientTest.class);
    }

    public void testSimple() throws HttpException, IOException {
        String uri = "/downloads/test/test-small.xml";
        String host = "127.0.0.1";
        int port = 80;

        HttpClient httpClient = new HttpClient();

        HostConfiguration hostConfiguration = new HostConfiguration();

        hostConfiguration.setHost(host, port, "http");

        GetMethod getMethod = new GetMethod(uri);

        Cookie cookie = new Cookie(host, "TEST_COOKIE", "my-value", "/", 60 * 60, false);
        HttpState httpState = new HttpState();
        httpState.addCookie(cookie);

        int resultCode = httpClient.executeMethod(hostConfiguration, getMethod, httpState);
        System.out.println("resultCode : " + resultCode);

        InputStream response = getMethod.getResponseBodyAsStream();
        System.out.println();
        IOUtils.copy(response, System.out);
    }
    
    public void testBigFile() throws HttpException, IOException {
        String uri = "/downloads/java/springframework/spring-framework-2.0.7-with-dependencies/spring-framework-2.0.7/docs/reference/html_single/";
        String host = "127.0.0.1";
        int port = 80;

        HttpClient httpClient = new HttpClient();

        HostConfiguration hostConfiguration = new HostConfiguration();

        hostConfiguration.setHost(host, port, "http");

        GetMethod getMethod = new GetMethod(uri);

        Cookie cookie = new Cookie(host, "TEST_COOKIE", "my-value", "/", 60 * 60, false);
        HttpState httpState = new HttpState();
        httpState.addCookie(cookie);

        int resultCode = httpClient.executeMethod(hostConfiguration, getMethod, httpState);
        System.out.println("resultCode : " + resultCode);

        InputStream response = getMethod.getResponseBodyAsStream();
        System.out.println();
        IOUtils.copy(response, System.out);
    }

    public void testMultiThreaded() throws HttpException, IOException {

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setMaxTotalConnections(10);
        connectionManager.getParams().setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 20);
        // Socket SO_TIMEOUT (e.g. 15-30 secs)
        connectionManager.getParams().setSoTimeout(30);
        // Timeout to establish connection (e.g. 5 secs)
        connectionManager.getParams().setConnectionTimeout(30);

        String uri = "/downloads/test/test-small.xml";
        String host = "127.0.0.1";
        int port = 80;

        HttpClient httpClient = new HttpClient(connectionManager);

        HostConfiguration hostConfiguration = new HostConfiguration();

        hostConfiguration.setHost(host, port, "http");

        GetMethod httpMethod = new GetMethod(uri);
        httpMethod.addRequestHeader("connection", "keep-alive");

        Cookie cookie = new Cookie(host, "TEST_COOKIE", "my-value", "/", 60 * 60, false);
        HttpState httpState = new HttpState();
        httpState.addCookie(cookie);

        try {
            int resultCode = httpClient.executeMethod(hostConfiguration, httpMethod, httpState);
            System.out.println("resultCode : " + resultCode);

            InputStream response = httpMethod.getResponseBodyAsStream();
            System.out.println();
            IOUtils.copy(response, System.out);
        } finally {
            httpMethod.releaseConnection();
        }
    }
}