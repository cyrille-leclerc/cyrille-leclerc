package cyrille.util;

import java.util.Comparator;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class BeanComparator implements Comparator {
    private final static int SORT_FIELDS = 0;

    private final static int PROPERTIES_NAMES = 1;

    private final static int ASCENDINGS = 2;

    /**
     * Array of col1 : String sortField e.g. "size.sortKey" col2 : String[] sortFieldGetters e.g.
     * {"size", "sortKey"} col4 : Boolean flag indicating whether the sort is ascending or not
     */
    private Object[][] sortFields = null;

    /**
     * BoComparator constructor comment.
     * 
     * @param newSortField
     *            String : name of the bean attribute to sort on (i.e. getXXX must exist), can be
     *            separated by point, getBo() implied
     * @param newAscending
     *            boolean : sort ascending or not
     */
    public BeanComparator(String newSortField, boolean newAscending) {
        this(new String[] { newSortField }, new boolean[] { newAscending });
    }

    /**
     * BoComparator constructor comment.
     * 
     * @param newSortField
     *            String[] : name of the bean attribute to sort on (i.e. getXXX must exist), can be
     *            separated by point, getBo() implied
     * @param newAscending
     *            boolean[] : sort ascending or not
     * @throws IllegalArgumentException :
     */
    public BeanComparator(String[] newSortField, boolean[] newAscending) {
        super();
        if (newSortField == null) {
            throw new IllegalArgumentException("arguments can NOT be bull sortField=" + newSortField);
        }
        if (newSortField.length != newAscending.length) {
            throw new IllegalStateException("newSortField and newAscending arrays have different sizes");
        }
        this.sortFields = new Object[newSortField.length][4];
        for (int i = 0; i < newSortField.length; i++) {
            this.sortFields[i][SORT_FIELDS] = newSortField[i];
            this.sortFields[i][ASCENDINGS] = Boolean.valueOf(newAscending[i]);

            StringTokenizer sortFieldsTk = new StringTokenizer(newSortField[i], ".");

            String[] gettersNames = new String[sortFieldsTk.countTokens()];
            this.sortFields[i][PROPERTIES_NAMES] = gettersNames;

            int j = 0;
            while (sortFieldsTk.hasMoreElements()) {
                String sortField = (String) sortFieldsTk.nextElement();
                gettersNames[j] = sortField;
                j++;
            }
        }
    }

    /**
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second.
     * @throws ClassCastException
     *             if the arguments' types prevent them from being compared by this Comparator.
     */
    public int compare(Object o1, Object o2) {
        // System.out.println("Compare");
        // System.out.println("\t'" + o1 + "'");
        // System.out.println("\t'" + o2 + "'");

        int result = 0;
        try {
            int sortFieldsCounter = 0;
            while (result == 0 && sortFieldsCounter < this.sortFields.length) {
                Object currentO1 = o1;
                Object currentO2 = o2;
                String[] propertiesNames = (String[]) this.sortFields[sortFieldsCounter][PROPERTIES_NAMES];
                boolean ascending = ((Boolean) this.sortFields[sortFieldsCounter][ASCENDINGS]).booleanValue();

                int i = 0;
                while (currentO1 != null && currentO2 != null && i < propertiesNames.length) {
                    currentO1 = PropertyUtils.getProperty(currentO1, propertiesNames[i]);
                    currentO2 = PropertyUtils.getProperty(currentO2, propertiesNames[i]);
                    i++;
                }

                if (currentO1 == null) {
                    if (currentO2 == null) {
                        result = 0;
                    } else {
                        result = +1;
                    }
                } else {
                    if (currentO2 == null) {
                        // result = -1;
                        result = -1;
                    } else {
                        if (!(currentO1 instanceof Comparable)) {
                            throw new RuntimeException("Not comparable for getter " + currentO1.toString());
                        }
                        result = ((Comparable) currentO1).compareTo(currentO2);
                    }
                }

                result = (ascending == true ? result : -result);

                // System.out.println("\t\t on '" + sortField + "' = '" + result + "'");
                sortFieldsCounter++;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
