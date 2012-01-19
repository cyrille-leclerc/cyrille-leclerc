package cyrille.lang;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class TextParserTest extends TestCase {

    public void testParseText() throws Exception {

        InputStream in = getClass().getResourceAsStream("textParser.txt");
        assertNotNull("in", in);
        List<String> lines = IOUtils.readLines(in);
        Map<String, List<String>> linesByUid = new HashMap<String, List<String>>();
        for (String line : lines) {
            String uuid = StringUtils.substringBetween(line, "[uuid=", ",");

            List<String> linesWithSameId = linesByUid.get(uuid);
            if (linesWithSameId == null) {
                linesWithSameId = new ArrayList<String>();
                linesByUid.put(uuid, linesWithSameId);
            }
            linesWithSameId.add(line);
        }
        
        for (String uuid : linesByUid.keySet()) {
            List<String> linesWithSameId = linesByUid.get(uuid);
            if(linesWithSameId.size() > 1) {
                System.err.println("Duplicate lines with uuid " + uuid);
                for (String line : linesWithSameId) {
                    System.err.println("\t" + line);
                }
            }
        }

    }
}
