package cyrille.pool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class PoolTest extends TestCase {

    public static interface FormattingService {
        public String format(Date date);

        public void setPattern(String pattern);

    }

    public static class LockedFormattingService implements FormattingService {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        final Lock readLock = readWriteLock.readLock();

        final Lock writeLock = readWriteLock.writeLock();

        public String format(Date date) {
            readLock.lock();
            try {
                return dateFormat.format(date);
            } finally {
                readLock.unlock();
            }
        }

        public void setPattern(String pattern) {
            writeLock.lock();
            try {
                this.dateFormat = new SimpleDateFormat(pattern);
            } finally {
                writeLock.unlock();
            }
        }
    }

    public static class PooledFormattingService implements FormattingService {
        ObjectPool dateFormatsPool;

        String pattern;

        public PooledFormattingService() {
            PoolableObjectFactory factory = new BasePoolableObjectFactory() {
                @Override
                public Object makeObject() throws Exception {
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                }
            };
            dateFormatsPool = new GenericObjectPool(factory);
        }

        public String format(Date date) {
            try {
                SimpleDateFormat dateFormat = (SimpleDateFormat) this.dateFormatsPool.borrowObject();
                try {
                    return dateFormat.format(date);
                } finally {
                    this.dateFormatsPool.returnObject(dateFormat);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public void setPattern(final String pattern) {
            PoolableObjectFactory factory = new BasePoolableObjectFactory() {
                @Override
                public Object makeObject() throws Exception {
                    return new SimpleDateFormat(pattern);
                }
            };
            this.dateFormatsPool = new GenericObjectPool(factory);
        }

    }

    public static class RecreateFormattingService implements FormattingService {

        String pattern = "dd/MM/yyyy HH:mm:ss";

        public String format(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class SynchronizedFormattingService implements FormattingService {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        public synchronized String format(Date date) {
            return dateFormat.format(date);
        }

        public synchronized void setPattern(String pattern) {
            this.dateFormat = new SimpleDateFormat(pattern);
        }
    }

    public void test() throws Exception {
        test(new RecreateFormattingService());
        test(new SynchronizedFormattingService());
        test(new PooledFormattingService());
        test(new LockedFormattingService());
    }

    public void test(final FormattingService formattingService) throws Exception {
        final int THREADS_COUNT = 50;

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);

        long startMillis = System.currentTimeMillis();

        for (int i = 0; i < THREADS_COUNT; i++) {
            Runnable formatDateCommand = new Runnable() {
                public void run() {
                    for (int j = 0; j < 100000; j++) {
                        formattingService.format(new Date());
                        // StressTestUtils.incrementProgressBarSuccess();
                    }
                }
            };
            executorService.execute(formatDateCommand);
        }
        
        executorService.shutdown();
        
        while (executorService.isShutdown() == false) {
            formattingService.setPattern("dd/MM/yyyy HH:mm:ss");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long endMillis = System.currentTimeMillis();

        System.out.println("Duration for " + formattingService + ": " + DurationFormatUtils.formatDurationHMS(endMillis - startMillis));
    }
}
