/*
 * Created on May 12, 2004
 */
package cyrille.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class MockServletRequest implements ServletRequest {

    private Map<String, String[]> m_parameters = new HashMap<String, String[]>();

    /**
     * 
     */
    public MockServletRequest() {
        super();
    }

    public void addParameter(String paramName, String value) {
        Object parameter = this.m_parameters.get(paramName);
        String[] oValue;
        if (parameter == null) {
            oValue = new String[] { value };
        } else {
            List<String> lst;
            if (parameter instanceof String[]) {
                String[] parameters = (String[]) parameter;
                lst = Arrays.asList(parameters);
            } else if (parameter instanceof String) {
                lst = new ArrayList<String>();
            } else {
                throw new ClassCastException("Expected String or String[], not" + parameter);
            }
            lst.add(value);
            oValue = lst.toArray(new String[lst.size()]);
        }
        this.m_parameters.put(paramName, oValue);
    }

    /**
     * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
     */
    public Object getAttribute(String arg0) {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getCharacterEncoding()
     */
    public String getCharacterEncoding() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getContentLength()
     */
    public int getContentLength() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#getContentType()
     */
    public String getContentType() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getInputStream()
     */
    public ServletInputStream getInputStream() throws IOException {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see ServletRequest#getLocalAddr()
     */
    public String getLocalAddr() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocale()
     */
    public Locale getLocale() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocales()
     */
    public Enumeration getLocales() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocalName()
     */
    public String getLocalName() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getLocalPort()
     */
    public int getLocalPort() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
     */
    public String getParameter(String paramName) {
        Object parameter = this.m_parameters.get(paramName);
        if (parameter instanceof String[]) {
            String[] parameters = (String[]) parameter;
            return parameters[0];
        }
        return (String) parameter;
    }

    /**
     * @see javax.servlet.ServletRequest#getParameterMap()
     */
    public Map getParameterMap() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String arg0) {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getProtocol()
     */
    public String getProtocol() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getReader()
     */
    public BufferedReader getReader() throws IOException {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
     * @deprecated
     */
    @Deprecated
    public String getRealPath(String arg0) {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemoteAddr()
     */
    public String getRemoteAddr() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemoteHost()
     */
    public String getRemoteHost() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getRemotePort()
     */
    public int getRemotePort() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#getRequestDispatcher(java.lang.String)
     */
    public RequestDispatcher getRequestDispatcher(String arg0) {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getScheme()
     */
    public String getScheme() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getServerName()
     */
    public String getServerName() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    /**
     * @see javax.servlet.ServletRequest#getServerPort()
     */
    public int getServerPort() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return 0;
    }

    /**
     * @see javax.servlet.ServletRequest#isSecure()
     */
    public boolean isSecure() {
        if (true) {
            throw new UnsupportedOperationException();
        }
        return false;
    }

    /**
     * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String arg0) {
        if (true) {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * @see javax.servlet.ServletRequest#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String arg0, Object arg1) {
        if (true) {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
     */
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
        if (true) {
            throw new UnsupportedOperationException();
        }

    }

}