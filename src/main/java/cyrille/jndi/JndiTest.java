/*
 * Created on Oct 4, 2004
 */
package cyrille.jndi;

import java.io.PrintWriter;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class JndiTest extends TestCase {

    private int MAX_DEPTH = 8;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JndiTest.class);
    }

    public void testConnect() throws Exception {
        String providerUrl = "iiop://127.0.0.1:2809/"; // "iiop://10.173.35.106:900"; //
        String initialContextFactory = "com.sun.jndi.cosnaming.CNCtxFactory";

        Properties env = new Properties();
        env.put(Context.PROVIDER_URL, providerUrl);
        env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
        // env.put(Context.SECURITY_PRINCIPAL, "websphere");
        // env.put(Context.SECURITY_CREDENTIALS, "mot1de1passe");
        InitialContext context = new InitialContext(env);

        PrintWriter out = new PrintWriter(System.out, true);

        dumpContext(context, "", 1, out);

    }

    private void dumpContext(Context cx, String indent, int depth, PrintWriter out) throws NamingException {
        NamingEnumeration enu = cx.listBindings("");
        while (enu.hasMoreElements()) {
            Binding binding = (Binding) enu.nextElement();
            if (binding != null) {
                Object o = binding.getObject();
                if (o instanceof Context) {
                    if (depth > this.MAX_DEPTH && !("jdbc".equals(binding.getName()))) {
                        // Websphere recursive context work around
                        out.println(indent + "+- " + binding.getName() + "- recursive context");
                    } else {
                        out.println(indent + "+- " + binding.getName());
                        dumpContext((Context) o, indent + "|   ", depth + 1, out);
                    }

                } else {
                    out.println(indent + "+- " + binding.getName() + "=" + (o == null ? "null" : o.getClass().getName()) + "\t" + o);
                }
            } else {
                out.println(indent + "+- #null binding#");
            }
        }

    }
}