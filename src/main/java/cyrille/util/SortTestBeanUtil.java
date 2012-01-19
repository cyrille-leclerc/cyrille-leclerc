package cyrille.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;

import cyrille.sample.product.Color;
import cyrille.sample.product.Product;
import cyrille.sample.product.Sex;
import cyrille.sample.product.Size;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class SortTestBeanUtil extends TestCase {

    public void testSort() throws Exception {
        List products = new ArrayList();

        // BUILD LIST
        for (int size = 32; size < 43; size++) {
            Product product = new Product("product A", new Color("black", "blacb"), new Size("" + size, Sex.FEMALE));
            products.add(product);
        }
        for (int size = 32; size < 43; size++) {
            Product product = new Product("product A", new Color("red", "red"), new Size("" + size, Sex.FEMALE));
            products.add(product);
        }
        for (int size = 5; size < 13; size++) {
            Product product = new Product("product B", new Color("black", "black"), new Size("" + size, Sex.MALE));
            products.add(product);
        }

        for (int size = 5; size < 13; size++) {
            Product product = new Product("product B", new Color("green", "green"), new Size("" + size, Sex.MALE));
            products.add(product);
        }
        for (int size = 5; size < 13; size++) {
            Product product = new Product("product B", new Color("yellow", "yellow"), new Size("" + size, Sex.MALE));
            products.add(product);
        }
        products.add(new Product("nullColor", null, null));
        // SHUFFLE
        Collections.shuffle(products);

        // DISPLAY
        System.out.println("SHUFFLED");
        Iterator it = products.iterator();
        while (it.hasNext()) {
            Product product = (Product) it.next();
            System.out.println(PropertyUtils.getNestedProperty(product, "name") + "-"
                    + PropertyUtils.getNestedProperty(product, "color.name"));
        }

        // Sort
        /*
         * String[] sortFields = new String[] { "name", "color.name", "size.name" }; boolean[]
         * ascendings = new boolean[] { true, true, true }; BoComparator comparator = new
         * BoComparator(sortFields, ascendings);
         */
        // BoComparator comparator = new BoComparator("color.name", true);
        // Collections.sort(products, comparator);
        // DISPLAY
        System.out.println("SORTED");
        it = products.iterator();
        while (it.hasNext()) {
            Product product = (Product) it.next();
            System.out.println(product);
        }

    }
}
