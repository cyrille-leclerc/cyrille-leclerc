/*
 * Created on Oct 4, 2004
 */
package cyrille.rmi.iiop;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class JndiUtilsTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JndiUtilsTest.class);
    }

    public void testCreateSubcontext() throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
        Context context = new InitialContext(env);
        Context subContext = JndiUtils.createSubcontext(context, "/enablers/toto/titi");
        System.out.println(subContext.getNameInNamespace());
    }

}
