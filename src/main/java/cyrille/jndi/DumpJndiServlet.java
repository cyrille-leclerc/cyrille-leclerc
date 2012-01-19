/*
 * Created on Aug 25, 2004
 */
package cyrille.jndi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class DumpJndiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private int MAX_DEPTH = 8;

    /**
     * 
     */
    public DumpJndiServlet() {
        super();
    }

    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String jndiHost = request.getParameter("jndiHost");
            String jndiPort = request.getParameter("jndiPort");

            Properties propsContext = new Properties();
            String providerUrl;
            if (StringUtils.isBlank(jndiHost)) {
                providerUrl = "iiop:///";
            } else if (StringUtils.isBlank(jndiPort)) {
                providerUrl = "iiop://" + jndiHost + "/";
            } else {
                providerUrl = "iiop://" + jndiHost + ":" + jndiPort + "/";
            }
            propsContext.put(Context.PROVIDER_URL, "iiop://" + (jndiHost == null ? "/" : jndiHost));
            InitialContext cx = new InitialContext(propsContext);

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.write("parameters to invoke the servlet\r\n");
            out.write("jndiHost optional default iiop:/// \r\n");
            out.write("jndiPort optional default to iiop://<jndiHost>/\r\n");
            out.write("providerUrl=" + providerUrl + "\r\n");

            dumpContext(cx, "", 0, out);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
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
}