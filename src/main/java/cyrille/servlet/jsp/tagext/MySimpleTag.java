/*
 * Created on Apr 13, 2006
 */
package cyrille.servlet.jsp.tagext;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @jsp.tag name="myTag" body-content="empty" description="This is my tag"
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class MySimpleTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String myParameter;

    public MySimpleTag() {
        super();
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print("My simple tag ended.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            out.print("My simple tag started.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    public String getMyParameter() {
        return this.myParameter;
    }

    /**
     * Sets the myParameter attribute. This is included in the tld file.
     * 
     * @jsp.attribute description="MyParameter attribute" required="true" rtexprvalue="true"
     */
    public void setMyParameter(String myParameter) {
        this.myParameter = myParameter;
    }
}
