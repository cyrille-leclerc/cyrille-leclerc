/*
 * Created on Oct 22, 2006
 */
package cyrille.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class Java5CollectionTest extends TestCase {

    public void testCheckedList() {
        List myList = new ArrayList();
        List myCheckedList = Collections.checkedList(myList, String.class);
    }
}
