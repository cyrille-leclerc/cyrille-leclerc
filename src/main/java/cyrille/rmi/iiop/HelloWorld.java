/*
 * Created on Sep 17, 2004
 */
package cyrille.rmi.iiop;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public interface HelloWorld extends Remote {

    public Response sayHello(Request request) throws RemoteException;
}