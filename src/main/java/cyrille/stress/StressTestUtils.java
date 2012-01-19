/*
 * Created on Nov 12, 2004
 */
package cyrille.stress;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jamonapi.MonitorFactory;

/**
 * Util class for Stress tests
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public final class StressTestUtils {

    private static final int DASH_PER_LINE = 30;

    private static final StressTestUtils instance = new StressTestUtils();

    private static final String SYMBOL_FAILURE = "x";

    private static final String SYMBOL_SUCCESS = "-";

    /**
     * Save Jamon report in the given <code>jamonReportFileName</code>
     * 
     * @param jamonReportFileName
     * @throws Exception
     */
    public static void dumpJamonReport(String jamonReportFileName) throws Exception {
        String jamonReport = MonitorFactory.getReport();
        File file = new File(jamonReportFileName);
        FileWriter out = new FileWriter(jamonReportFileName);
        out.write(jamonReport);
        out.close();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - Report saved in " + file.getAbsolutePath());
    }

    /**
     * Outputs a dash in SystemOut and line breaks when necessary
     */
    public static void incrementProgressBarFailure() {
        instance.incrementProgressBar(SYMBOL_FAILURE);
    }

    /**
     * Outputs a dash in SystemOut and line breaks when necessary
     */
    public static void incrementProgressBarSuccess() {
        instance.incrementProgressBar(SYMBOL_SUCCESS);
    }

    public static void writeProgressBarLegend() {
        System.out.println();
        System.out.println(SYMBOL_SUCCESS + " : Success");
        System.out.println(SYMBOL_FAILURE + " : Failure");
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private long lastSampleTime;

    private int progressBarCounter;

    /**
     * Private contructor for singleton class
     */
    private StressTestUtils() {
        super();
    }

    public static long getLastSampleTime() {
        return instance.lastSampleTime;
    }

    public static int getProgressBarCounter() {
        return instance.progressBarCounter;
    }

    /**
     * <p>
     * Outputs a dash in SystemOut and line breaks when necessary
     * </p>
     */
    protected synchronized void incrementProgressBar(String symbol) {
        if (this.lastSampleTime == 0) {
            this.lastSampleTime = System.currentTimeMillis();
        }
        System.out.print(symbol);
        this.progressBarCounter++;

        if (this.progressBarCounter % DASH_PER_LINE == 0) {
            System.out.println();
            long now = System.currentTimeMillis();
            long elapsedDuration = now - this.lastSampleTime;
            String throughput;
            if (elapsedDuration == 0) {
                throughput = "infinite";
            } else {
                long throughputAsInt = (DASH_PER_LINE * 1000) / elapsedDuration;
                throughput = String.valueOf(throughputAsInt);
            }
            System.out.print("[" + this.dateFormat.format(new Date()) + " " + StringUtils.leftPad(throughput + "", 4) + " req/s]\t");
            this.lastSampleTime = System.currentTimeMillis();
        }
    }
}
