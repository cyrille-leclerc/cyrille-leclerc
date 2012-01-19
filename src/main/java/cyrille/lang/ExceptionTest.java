package cyrille.lang;

import junit.framework.TestCase;

public class ExceptionTest extends TestCase {

    public void testStackTrace() throws Exception {
        for (int i = 0; i < 100000; i++) {
            analyseStackTrace1(false);
        }
        analyseStackTrace1(true);
    }

    private void analyseStackTrace1(boolean dumpStackTrace) {
        analyseStackTrace2(dumpStackTrace);
    }

    private void analyseStackTrace2(boolean dumpStackTrace) {
        analyseStackTrace3(dumpStackTrace);
    }

    private void analyseStackTrace3(boolean dumpStackTrace) {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (dumpStackTrace) {
                System.out.println(stackTraceElement);
            }
        }

    }
}
