/*
 * Created on Apr 20, 2005
 */
package cyrille.lang.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class StackTraceUtils {

    /**
     * 
     */
    private StackTraceUtils() {
        super();
    }

    /**
     * <p>
     * Splits the given text into an array, separator String specified.
     * </p>
     * <p>
     * This method is different of {@link java.util.StringTokenizer}that only accept chars as
     * separator but not strings
     * </p>
     * 
     * @param str
     * @param separator
     */
    public static String[] split(String str, String separator) {
        if (str == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        int fromIndex = 0;
        int idx;
        while ((idx = str.indexOf(separator, fromIndex)) != -1) {
            String subString = str.substring(fromIndex, idx);
            result.add(subString);
            fromIndex = idx + separator.length();
        }
        if (fromIndex != str.length()) {
            // add last element
            String subString = str.substring(fromIndex);
            result.add(subString);
        }
        return result.toArray(new String[result.size()]);
    }

    public static String[] splitNestedStackTraces(String stackTrace) {
        String separator = "Caused by: ";
        String[] result = split(stackTrace, separator);
        // prefix nested stack traces by "Caused by: "
        for (int i = 1; i < result.length; i++) {
            result[i] = separator + result[i];
        }
        return result;
    }

    public static String truncateStackTrace(String stackTrace, String searchedString) {
        StringBuffer result = new StringBuffer();
        int fromIndex = 0;
        int idxSearchedString = stackTrace.indexOf(searchedString, fromIndex);
        String suffix;
        if (idxSearchedString == -1) {
            idxSearchedString = stackTrace.length();
            suffix = "";
        } else {
            suffix = "... more";
        }
        String currentStack = stackTrace.substring(fromIndex, idxSearchedString);
        result.append(currentStack + suffix);
        return result.toString();
    }

    public static String truncateNestedStackTrace(String stackTrace, String searchedString) {
        String[] stackTraces = splitNestedStackTraces(stackTrace);
        StringBuffer result = new StringBuffer();
        for (String nestedStackTrace : stackTraces) {
            String truncatedNestedStackTrace = truncateStackTrace(nestedStackTrace, searchedString);
            result.append(truncatedNestedStackTrace + "\r\n");
        }

        return result.toString();
    }
}
