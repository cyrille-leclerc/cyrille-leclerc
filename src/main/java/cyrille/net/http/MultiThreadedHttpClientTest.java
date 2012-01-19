/*
 * Created on Aug 5, 2004
 */
package cyrille.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cyrille.stress.StressTestUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class MultiThreadedHttpClientTest extends TestCase {
    
    private final static Log log = LogFactory.getLog(MultiThreadedHttpClientTest.class);
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(MultiThreadedHttpClientTest.class);
    }
    
    public void test2() throws Exception {
        int maxTotalConnections = 0;
        int maxConnectionsPerHost = 0;
        int connectionManagerTimeout = 0;
        int soTimeout = 0;
        int connectionTimeout = 0;
        
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        // pool size
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        connectionManager.getParams().setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, maxConnectionsPerHost);
        
        // Socket SO_TIMEOUT (e.g. 15-30 secs)
        connectionManager.getParams().setSoTimeout(soTimeout);
        // Timeout to establish connection (e.g. 5 secs)
        connectionManager.getParams().setConnectionTimeout(connectionTimeout);
        
        HttpClient httpClient = new HttpClient(connectionManager);
        
        // max duration to wait for a connection from the connectionManager
        // (e.g. 10 s)
        httpClient.getParams().setConnectionManagerTimeout(connectionManagerTimeout);
        
        HttpMethod httpMethod = new GetMethod();
        httpMethod.addRequestHeader("connection", "keep-alive");
        try {
            httpClient.executeMethod(httpMethod);
        } catch (IOException e) {
            throw new RuntimeException("Exception communicating to <url< with data <zeData>", e);
        } finally {
            httpMethod.releaseConnection();
        }
        
    }
    
    public void test() throws InterruptedException {
        
        final int injectorsCount = 30;
        final int injectionsPerInjectorCount = 50;
        
        MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
        multiThreadedHttpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(injectorsCount);
        // multiThreadedHttpConnectionManager.getParams().setMaxTotalConnections(injectorsCount * 4);
        multiThreadedHttpConnectionManager.getParams().setConnectionTimeout(500);
        // multiThreadedHttpConnectionManager.getParams().setSoTimeout(500);
        
        final HttpClient httpClient = new HttpClient(multiThreadedHttpConnectionManager);
        httpClient.getHostConfiguration().setHost("localhost", 80, "http");
        
        ExecutorService executorService = Executors.newFixedThreadPool(injectorsCount);
        final String[] urisToGet = {
            "/", "/manual/", "/manual/new_features_2_0.html", "/manual/bind.html"
        };
        
        Runnable injector = new Runnable() {
            public void run() {
                for (int numberOfRuns = 0; numberOfRuns < injectionsPerInjectorCount; numberOfRuns++) {
                    for (String uri : urisToGet) {
                        GetMethod httpMethod = new GetMethod(uri);
                        httpMethod.addRequestHeader("connection", "keep-alive");
                        try {
                            httpClient.executeMethod(httpMethod);
                            CountingOutputStream out = new CountingOutputStream(new NullOutputStream());
                            InputStream in = httpMethod.getResponseBodyAsStream();
                            IOUtils.copy(in, out);
                            
                            StressTestUtils.incrementProgressBarSuccess();
                        } catch (IOException e) {
                            StressTestUtils.incrementProgressBarFailure();
                            e.printStackTrace();
                        } finally {
                            httpMethod.releaseConnection();
                        }
                    }
                }
            }
        };
        
        StressTestUtils.writeProgressBarLegend();
        for (int i = 0; i < injectorsCount; i++) {
            executorService.execute(injector);
        }
        executorService.shutdown();
        executorService.awaitTermination(5 * 60, TimeUnit.SECONDS);
        System.out.println("bye");
        
    }
}
