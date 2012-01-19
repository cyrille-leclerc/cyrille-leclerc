/*
 * Created on Sep 17, 2004
 */
package cyrille.rmi.iiop;

import java.rmi.RemoteException;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class HelloWorldImplTest extends TestCase {

    public void testSayHello() throws RemoteException {
        HelloWorldImpl helloWorldImpl = new HelloWorldImpl();
        Request request = new Request("hello");
        Response response = helloWorldImpl.sayHello(request);
    }

}
