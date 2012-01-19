package cyrille.xstream;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

public class XStreamServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static class MyObject {
        String firstName;

        String lastname;

        public MyObject() {
            super();
        }

        public MyObject(String firstName, String lastname) {
            super();
            this.firstName = firstName;
            this.lastname = lastname;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastname() {
            return this.lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        XStream xstream = new XStream();

        Writer out = response.getWriter();

        MyObject myObject = new MyObject("Cyrille", "Le Clerc");

        boolean temporaryString = false;
        if (temporaryString) {
            String myObjectAsXmlString = xstream.toXML(myObject);
            out.write(myObjectAsXmlString);
        } else {
            xstream.toXML(myObject, out);
        }
    }
}
