/*
 * Created on Sep 17, 2004
 */
package cyrille.rmi.iiop;

import java.rmi.RemoteException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class HelloWorldImpl implements HelloWorld {

    public static final int AVERAGE_SLEEP_DURATION_IN_MILLIS = 25;

    private static final Log log = LogFactory.getLog(HelloWorldImpl.class);

    private int m_invocationsCounter;

    private Random m_random;

    private String m_serverName;

    public HelloWorldImpl() throws RemoteException {
        this("server");
    }

    public HelloWorldImpl(String serverName) throws RemoteException {
        super();
        this.m_random = new Random();
        this.m_serverName = serverName;
    }

    /**
     * @see test.rmi.iiop.HelloWorld#sayHello(test.rmi.iiop.Request)
     */
    public Response sayHello(Request request) throws RemoteException {
        Monitor monitor = MonitorFactory.start(this.m_serverName + ".sayHello");
        try {
            boolean traceEnabled = log.isTraceEnabled();
            if (traceEnabled) {
                log.trace("> sayHello(serverName=" + this.m_serverName + ", request=" + request + ")");
            }
            int sleepDurationInMillis = this.m_random.nextInt(AVERAGE_SLEEP_DURATION_IN_MILLIS * 2);

            Thread.sleep(sleepDurationInMillis);

            Response response = new Response(request.getValue() + "-" + this.m_invocationsCounter + "-" + sleepDurationInMillis + "ms");
            this.m_invocationsCounter++;

            if (traceEnabled) {
                log.trace("< sayHello() " + response);
            }
            return response;
        } catch (InterruptedException e) {
            String msg = "Exception sleeping: " + e;
            log.error(msg, e);
            throw new RemoteException(msg, e);
        } finally {
            monitor.stop();
        }
    }
}