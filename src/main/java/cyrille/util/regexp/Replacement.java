package cyrille.util.regexp;

/*
 * This code writes "One dog, two dogs in the yard."
 * to the standard-output stream:
 */
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacement {
    public static void main(String[] args) throws Exception {
        try {
            // sampleTest();
            CharSequence charSequence = getData();
            anotherTest(charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CharSequence getData() throws Exception {
        URL url = Replacement.class.getResource("data.txt");
        Reader reader = new InputStreamReader(url.openStream());
        char[] buffer = new char[1024];
        int length;
        StringBuffer charSequence = new StringBuffer();
        while ((length = reader.read(buffer)) != -1) {
            charSequence.append(buffer, 0, length);
        }
        return charSequence;
    }

    private static void sampleTest() {
        // Create a pattern to match cat
        Pattern p = Pattern.compile("cat");
        // Create a matcher with an input string
        Matcher m = p.matcher("one cat," + " two cats in the yard");
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        // Loop through and create a new String
        // with the replacements
        while (result) {
            m.appendReplacement(sb, "dog");
            result = m.find();
        }
        // Add the last segment of input to
        // the new String
        m.appendTail(sb);
        System.out.println(sb.toString());
    }

    public static void anotherTest(CharSequence charSequence) throws Exception {
        Pattern pattern = Pattern.compile("new Warning\\(.*,.*,.*\\)");
        Matcher matcher = pattern.matcher(charSequence);
        while (matcher.find()) {
            System.out.println("start " + matcher.start() + " - end " + matcher.end() + " - '" + matcher.group() + "'");
        }
    }
}