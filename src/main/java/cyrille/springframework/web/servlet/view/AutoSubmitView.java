package cyrille.springframework.web.servlet.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Equivalent of a {@link RedirectView} implemented with an auto submitted form.
 * 
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class AutoSubmitView extends AbstractUrlBasedView implements View {
    
    public AutoSubmitView(String url) {
        super(url);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        
        Map<String, String> formFields = model;
        
        out.println("<html><body>");
        out.println("<form method='POST' name='autoLoginForm' action='" + getUrl() + "'>");
        for (Map.Entry<String, String> entry : formFields.entrySet()) {
            out.println("<input type=hidden name='" + entry.getKey() + "' value='" + entry.getValue() + "' />");
        }
        out.println("</form>");
        out.println("<script language='JavaScript'>document.forms['autoLoginForm'].submit();</script>");
        out.println("</body></html>");
    }
}
