/*
 * Created on Jul 27, 2004
 */
package cyrille.xstream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.io.path.PathTracker;

/**
 * <p>
 * Recopy of {@link com.thoughtworks.xstream.io.path.PathTracker}to provide additional methods
 * </p>
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class MyPathTracker extends PathTracker {

    private int m_capacity;

    private String m_currentPath;

    private List m_currentPathAsList;

    private Map[] m_indexMapStack;

    private String[] m_pathStack;

    private int m_pointer;

    public MyPathTracker() {
        this(16);
    }

    public MyPathTracker(int initialCapacity) {
        this.m_capacity = initialCapacity;
        this.m_pathStack = new String[this.m_capacity];
        this.m_indexMapStack = new Map[this.m_capacity];
    }

    public String getCurrentElement() {
        String result;

        if (this.m_pointer < 1) {
            result = null;
        } else {
            result = getElement(this.m_pointer - 1);
        }
        return result;
    }

    public List getCurrentPathAsList() {
        // rebuild m_currentPathAsList if necessary
        return this.m_currentPathAsList;
    }

    @Override
    public String getCurrentPath() {
        if (this.m_currentPath == null) {
            StringBuffer result = new StringBuffer();
            this.m_currentPathAsList = new ArrayList();
            for (int i = 0; i < this.m_pointer; i++) {
                result.append('/');
                result.append(this.m_pathStack[i]);
                this.m_currentPathAsList.add(this.m_pathStack[i]);
                Integer integer = ((Integer) this.m_indexMapStack[i].get(this.m_pathStack[i]));
                int index = integer.intValue();
                if (index > 1) {
                    result.append('[').append(index).append(']');
                }
            }
            this.m_currentPath = result.toString();
        }
        return this.m_currentPath;
    }

    public String getElement(int i) {
        List currentPathAsList = getCurrentPathAsList();
        String result = (String) currentPathAsList.get(i);
        return result;
    }

    public String getPreviousElement() {
        String result;
        if (this.m_pointer < 2) {
            result = null;
        } else {
            result = getElement(this.m_pointer - 2);
        }
        return result;
    }

    @Override
    public void popElement() {
        this.m_indexMapStack[this.m_pointer] = null;
        this.m_currentPath = null;
        this.m_pointer--;
    }

    @Override
    public void pushElement(String name) {
        if (this.m_pointer + 1 >= this.m_capacity) {
            resizeStacks(this.m_capacity * 2);
        }
        this.m_pathStack[this.m_pointer] = name;
        Map indexMap = this.m_indexMapStack[this.m_pointer];
        if (indexMap == null) {
            indexMap = new HashMap();
            this.m_indexMapStack[this.m_pointer] = indexMap;
        }
        if (indexMap.containsKey(name)) {
            indexMap.put(name, new Integer(((Integer) indexMap.get(name)).intValue() + 1));
        } else {
            indexMap.put(name, new Integer(1));
        }
        this.m_pointer++;
        this.m_currentPath = null;
    }

    private void resizeStacks(int newCapacity) {
        String[] newPathStack = new String[newCapacity];
        Map[] newIndexMapStack = new Map[newCapacity];
        int min = Math.min(this.m_capacity, newCapacity);
        System.arraycopy(this.m_pathStack, 0, newPathStack, 0, min);
        System.arraycopy(this.m_indexMapStack, 0, newIndexMapStack, 0, min);
        this.m_pathStack = newPathStack;
        this.m_indexMapStack = newIndexMapStack;
        this.m_capacity = newCapacity;
    }

}