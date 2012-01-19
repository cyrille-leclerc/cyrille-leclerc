package cyrille.jndi;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 */
public class JndiUtil {

    private int MAX_DEPTH = 8;

    public static void main(String[] args) {
        try {
            JndiUtil jndiUtil = new JndiUtil();
            String jndiHost = "10.173.35.106:2809";// "nlvcp046p:2809";//"nlvcp046p:2809";//"amazone:900";//"dev-sirius-a";
            // // //"amazone";
            InitialContext ctx = jndiUtil.connect(jndiHost);

            FileOutputStream fileOutputStream = new FileOutputStream("c:/dumpWASJNDI.txt");
            PrintWriter out = new PrintWriter(fileOutputStream);
            jndiUtil.dumpContext(ctx, "", 0, out);
            fileOutputStream.close();

            // test an Oracle Connection
            // String path = "cell/clusters/Symphonie
            // Cluster/jdbc/SymphoniePrp");//"jdbc/StoreDb";
            String path = "cell/clusters/unicodeclu/jdbc/storedb";
            DataSource ds = (DataSource) ctx.lookup(path);
            Connection cnn = ds.getConnection();
            System.out.println("cnn=" + cnn);
            cnn.createStatement().execute("select 1 from dual");

            // test another connection
            ds = (DataSource) ctx.lookup("jdbc/lv002");
            cnn = ds.getConnection();
            System.out.println("cnn=" + cnn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive method to dump a JNDI tree
     * 
     * @param cx
     * @param indent
     *            for display
     */
    private void dumpContext(Context cx, String indent, int depth, PrintWriter out) {
        NamingEnumeration enu;
        try {
            enu = cx.listBindings("");
        } catch (NamingException e) {
            e.printStackTrace(out);
            return;
        }
        while (enu.hasMoreElements()) {
            Binding binding = (Binding) enu.nextElement();
            if (binding != null) {
                Object o = binding.getObject();
                if (o instanceof Context) {
                    if (depth > this.MAX_DEPTH && !("jdbc".equals(binding.getName()))) {
                        // Websphere 4.0.4 recursive context work around
                        out.println(indent + "+- " + binding.getName() + "- recursive context");
                    } else {
                        out.println(indent + "+- " + binding.getName());
                        dumpContext((Context) o, indent + "|   ", ++depth, out);
                    }

                } else {
                    out.println(indent + "+- " + binding.getName() + "=" + (o == null ? "null" : o.getClass().getName()) + "\t" + o);
                }
            } else {
                out.println(indent + "+- #null binding#");
            }
        }

    }

    /**
     * Connects to an IBM Websphere JNDI source
     * 
     * @return the initial context
     * @throws Exception
     */
    public InitialContext connect(String jndiHost) throws Exception {

        /*
         * All these Environment variables could be set as commandLine parameters sample :
         * -Dorg.omg.CORBA.ORBClass=com.ibm.rmi.iiop.ORB
         */
        Properties sp = System.getProperties();
        sp.put("org.omg.CORBA.ORBClass", "com.ibm.rmi.iiop.ORB");
        sp.put("org.omg.CORBA.ORBSingletonClass", "com.ibm.rmi.corba.ORBSingleton");
        sp.put("javax.rmi.CORBA.UtilClass", "com.ibm.rmi.javax.rmi.CORBA.Util");
        sp.put("javax.rmi.CORBA.StubClass", "com.ibm.rmi.javax.rmi.CORBA.StubDelegateImpl");
        sp.put("javax.rmi.CORBA.PortableRemoteObjectClass", "com.ibm.rmi.javax.rmi.PortableRemoteObject");
        System.setProperties(sp);

        InitialContext cx;
        Properties propsContext = new Properties();
        propsContext.put(Context.PROVIDER_URL, "iiop://" + (jndiHost == null ? "/" : jndiHost));
        /*
         * InitialContext.INITIAL_CONTEXT_FACTORY can be set as an environment variable named
         * "java.naming.factory.initial" (see javadoc) IBM VMs does not require to specify it Which
         * one the the new class ? com.ibm.websphere.naming.WsnInitialContextFactory or
         * com.ibm.ejs.ns.jndi.CNInitialContextFactory ?
         */
        propsContext.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.ejs.ns.jndi.CNInitialContextFactory");
        /*
         * Could be set as an environment variable named "java.naming.factory.url.pkgs" (see
         * javadoc) Line can be ignored (ie removed) to dump the JNDI context and use DataSources
         */
        propsContext.put(Context.URL_PKG_PREFIXES, "com.ibm.ws.naming");

        cx = new InitialContext(propsContext);

        return cx;
    }

}