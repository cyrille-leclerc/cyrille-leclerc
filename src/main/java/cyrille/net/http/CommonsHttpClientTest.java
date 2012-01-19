/*
 * Created on Mar 18, 2005
 */
package cyrille.net.http;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class CommonsHttpClientTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CommonsHttpClientTest.class);
    }

    public void testWithUrl() throws HttpException, IOException {
        String url = "http://fr.pprod.z3mandarine.com/service/SA?VS=VSTest_INSTAL&msisdn=33687654321";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        int resultCode = httpClient.executeMethod(getMethod);
        System.out.println("resultCode : " + resultCode);
        String result = getMethod.getResponseBodyAsString();
        System.out.println();
        System.out.println(result);
    }

    public void testWithUri() throws HttpException, IOException {
        String uri = "/Actualites_et_medias/";
        String host = "127.0.0.1"; // "fr.dir.yahoo.com";
        int port = 8081;

        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setHost(host, port);

        GetMethod getMethod = new GetMethod(uri);

        Cookie cookie = new Cookie(host, "SMS_SERVER_SESSION_ID", "<msisdn>-<short-code>", "/", 30 * 60, false);
        HttpState httpState = new HttpState();
        httpState.addCookie(cookie);

        int resultCode = httpClient.executeMethod(null, getMethod, httpState);
        // getMethod.addRequestHeader("Cookie",
        // "SMS_SERVER_SESSION_ID=<msisdn>-<short-code>");
        // int resultCode = httpClient.executeMethod(getMethod);
        String result = getMethod.getResponseBodyAsString();

        System.out.println("resultCode : " + resultCode);
        System.out.println();
        System.out.println(result);
    }

    public void testSMMS() throws HttpException, IOException {
        String url = "http://localhost:8081/cyrille/tools/parameters.jsp";

        HttpClient httpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        getMethod.addRequestHeader("Cookie", "SMS_SERVER_SESSION_ID=33612345678-123456");
        int resultCode = httpClient.executeMethod(getMethod);
        String result = getMethod.getResponseBodyAsString();

        System.out.println("resultCode : " + resultCode);
        System.out.println();
        System.out.println(result);
    }

    public void testSMMS2() throws HttpException, IOException {
        String url = "http://localhost:8081/cyrille/tools/parameters.jsp";

        HttpClient httpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        Cookie cookie = new Cookie("localhost", "SMS_SERVER_SESSION_ID", "33612345678-123456", "/", 30 * 60, false);
        HttpState httpState = new HttpState();
        httpState.addCookie(cookie);

        int resultCode = httpClient.executeMethod(null, getMethod, httpState);

        String result = getMethod.getResponseBodyAsString();

        System.out.println("resultCode : " + resultCode);
        System.out.println();
        System.out.println(result);
    }
}