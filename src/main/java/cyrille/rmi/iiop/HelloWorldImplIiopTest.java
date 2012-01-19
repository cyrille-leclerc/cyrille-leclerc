/*
 * Created on Sep 17, 2004
 */
package cyrille.rmi.iiop;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jamonapi.MonitorFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class HelloWorldImplIiopTest extends TestCase {

    public static int NUMBER_OF_INVOCATIONS_PER_THREAD = 10;

    public static int NUMBER_OF_THREADS = 10;

    private static final Log log = LogFactory.getLog(HelloWorldImplIiopTest.class);

    public static void main(String[] args) {
        TestRunner.runAndWait(new TestSuite(HelloWorldImplIiopTest.class));
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MonitorFactory.setEnabled(true);

        // Bind the object
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        env.put(Context.PROVIDER_URL, "iiop://82.229.180.44:1500/");
        Context context = new InitialContext(env);
        HelloWorldImpl helloWorldImpl = new HelloWorldImpl();
        Context enablersContext;
        try {
            enablersContext = (Context) context.lookup("enablers");
            log.debug("/enablers context already exist");
        } catch (NameNotFoundException e) {
            enablersContext = context.createSubcontext("enablers");
            log.debug("/enablers context created");
        }
        enablersContext.rebind("HelloWorldServer", helloWorldImpl);

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println(MonitorFactory.getReport());
    }

    public void testSayHello() throws NamingException, RemoteException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        env.put(Context.PROVIDER_URL, "iiop://localhost:1500/");
        Context context = new InitialContext(env);
        Object enablersContext = context.lookup("enablers");
        System.out.println("enablersContext " + enablersContext);

        Object oHelloWorld = context.lookup("enablers/HelloWorldServer");
        System.out.println("helloWorld " + oHelloWorld);

        HelloWorld helloWorld = (HelloWorld) PortableRemoteObject.narrow(oHelloWorld, HelloWorld.class);
        log.debug("helloWorld: " + helloWorld);
        Request request = new Request("request");
        Response response = helloWorld.sayHello(request);
        log.debug(response);
    }

    public void testSayHelloBulkTest() throws NamingException, RemoteException, InterruptedException {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        env.put(Context.PROVIDER_URL, "iiop://localhost:1500/");
        Context context = new InitialContext(env);
        Object oHelloWorld = context.lookup("enablers/HelloWorldServer");
        final HelloWorld helloWorld = (HelloWorld) PortableRemoteObject.narrow(oHelloWorld, HelloWorld.class);

        Runnable runnable = new Runnable() {

            public void run() {
                for (int j = 0; j < NUMBER_OF_INVOCATIONS_PER_THREAD; j++) {
                    Request request = new Request("request");
                    Response response;
                    try {
                        response = helloWorld.sayHello(request);
                        // log.debug(response);
                    } catch (RemoteException e) {
                        log.error(e, e);
                    }
                }

            }
        };

        log.debug("helloWorld: " + helloWorld);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        long duration = NUMBER_OF_INVOCATIONS_PER_THREAD * HelloWorldImpl.AVERAGE_SLEEP_DURATION_IN_MILLIS * 2;
        log.debug("Sleep for " + duration + " ms");
        Thread.sleep(duration);
    }
}