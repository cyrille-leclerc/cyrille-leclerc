package cyrille.lang.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class TestReferences {
    public static void main(String[] args) {
        try {
            TestReferences testReferences = new TestReferences();
            for (int i = 4; i < 15; i++) {
                // testReferences.testSoftReference(i);
                testReferences.testSoftReferenceWithReferenceQueue(i);
            }
            for (int i = 0; i < 10; i++) {
                testReferences.testWeakReference(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSoftReference(int factor) throws Exception {
        final int MAX = 1000;
        System.gc();
        long freeMemoryBefore = Runtime.getRuntime().freeMemory();
        Reference[] softReferences = new Reference[MAX];
        for (int i = 0; i < softReferences.length; i++) {
            ReferencedObject referencedObject = new ReferencedObject();
            referencedObject.setLastName("" + i);
            referencedObject.setData(new byte[factor * 1024]);
            softReferences[i] = new SoftReference(referencedObject);
        }
        long freeMemoryAfter = Runtime.getRuntime().freeMemory();

        System.gc();

        long freeMemoryAfterGc = Runtime.getRuntime().freeMemory();

        int nullsCount = 0;
        int nonNullsCount = 0;
        for (Reference reference : softReferences) {
            ReferencedObject referencedObject = (ReferencedObject) reference.get();
            if (referencedObject == null) {
                nullsCount += 1;
            } else {
                nonNullsCount += 1;
            }
        }
        System.out.println("SOFT Factor '" + factor + "'\tNulls '" + nullsCount + "' nonNulls '" + nonNullsCount + "' " + freeMemoryBefore
                + "-" + freeMemoryAfter + "-" + freeMemoryAfterGc);
    }

    public void testSoftReferenceWithReferenceQueue(int factor) throws Exception {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        final int MAX = 1000;
        System.gc();
        long freeMemoryBefore = Runtime.getRuntime().freeMemory();
        Reference[] softReferences = new Reference[MAX];
        for (int i = 0; i < softReferences.length; i++) {
            ReferencedObject referencedObject = new ReferencedObject();
            referencedObject.setLastName("" + i);
            referencedObject.setData(new byte[factor * 1024]);
            softReferences[i] = new SoftReference(referencedObject, referenceQueue);
        }
        long freeMemoryAfter = Runtime.getRuntime().freeMemory();

        System.gc();

        long freeMemoryAfterGc = Runtime.getRuntime().freeMemory();

        int nullsCount = 0;
        int nonNullsCount = 0;
        for (Reference reference : softReferences) {
            ReferencedObject referencedObject = (ReferencedObject) reference.get();
            if (referencedObject == null) {
                nullsCount += 1;
            } else {
                nonNullsCount += 1;
            }
        }
        int polledReferences = 0;
        Reference reference;
        while ((reference = referenceQueue.poll()) != null) {
            reference.get();
            polledReferences++;
        }

        System.out.println("SOFT Factor '" + factor + "\tNulls '" + nullsCount + " nonNulls '" + nonNullsCount + "' polledReferences '"
                + polledReferences + "' \tfreeMemoryBefore '" + freeMemoryBefore + "'- freeMemoryAfter '" + freeMemoryAfter
                + "' - freeMemoryAfterGc '" + freeMemoryAfterGc + "'");
    }

    public void testWeakReference(int factor) throws Exception {
        final int MAX = 1000;
        System.gc();
        long freeMemoryBefore = Runtime.getRuntime().freeMemory();
        Reference[] softReferences = new Reference[MAX];
        for (int i = 0; i < softReferences.length; i++) {
            ReferencedObject referencedObject = new ReferencedObject();
            referencedObject.setLastName("" + i);
            referencedObject.setData(new byte[factor * 1024]);
            softReferences[i] = new WeakReference(referencedObject);
        }
        long freeMemoryAfter = Runtime.getRuntime().freeMemory();

        System.gc();

        long freeMemoryAfterGc = Runtime.getRuntime().freeMemory();

        int nullsCount = 0;
        int nonNullsCount = 0;
        for (Reference element : softReferences) {
            ReferencedObject referencedObject = (ReferencedObject) element.get();
            if (referencedObject == null) {
                nullsCount += 1;
            } else {
                nonNullsCount += 1;
            }
        }
        System.out.println("WEAK Factor '" + factor + "\tNulls '" + nullsCount + " nonNulls '" + nonNullsCount + "' " + freeMemoryBefore
                + "-" + freeMemoryAfter + "-" + freeMemoryAfterGc);
    }
}
