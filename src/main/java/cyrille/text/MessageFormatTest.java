package cyrille.text;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class MessageFormatTest extends TestCase {

    /**
     * Method testMessageFormat.
     */
    public void testMessageFormat() throws Exception {
        Object[] values = new String[] { "qtyActual", "ofNum", "qtyExp", "qtyRens" };
        System.out.println(MessageFormat.format("Hello world '{1}'", new Object[] { "Cyrille", "toto" }));
        String pattern = "La qté {0} est invalide pour l''OF {1} (qté attendue : {2} et qté déjà  renseignée: {3}";
        System.out.println(MessageFormat.format(pattern, values));

        ResourceBundle bundle = ResourceBundle.getBundle("cyrille.text.MessageFormatTest");
        pattern = bundle.getString("test1");
        System.out.println("pattern=" + pattern);
        System.out.println(MessageFormat.format(pattern, values));

        pattern = bundle.getString("test2");
        System.out.println("pattern=" + pattern);
        System.out.println(MessageFormat.format(pattern, values));

    }

    public void testMessageFormatwyeth() {
        String subjectPattern = "[contact us] Contact {0} ({1})";
        String bodyPattern = "Email sent by {0} ({1})\r\nMessage:\r\n{2}";
        String[] arguments = { "Cyrille Le Clerc", "cleclerc@pobox.com", "This is my message" };

        String expectedSubject = "[contact us] Contact Cyrille Le Clerc (cleclerc@pobox.com)";
        String actualSubject = MessageFormat.format(subjectPattern, arguments);
        assertEquals(actualSubject, expectedSubject);

        String expectedBody = "Email sent by Cyrille Le Clerc (cleclerc@pobox.com)\r\nMessage:\r\nThis is my message";
        String actualBody = MessageFormat.format(bodyPattern, arguments);
        assertEquals(actualBody, expectedBody);
    }
}
