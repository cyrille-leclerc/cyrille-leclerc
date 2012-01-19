package cyrille.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import cyrille.sample.product.Color;
import cyrille.sample.product.Product;
import cyrille.sample.product.Sex;
import cyrille.sample.product.Size;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class SortTest extends TestCase {

    public void testSortProducts() throws Exception {
        List products = new ArrayList();

        // BUILD LIST
        /*
         * for (int size = 32; size < 43; size++) { Product product = new Product("product A", new
         * Color("black", "blacb"), new Size("" + size, Sex.FEMALE)); products.add(product); } for
         * (int size = 32; size < 43; size++) { Product product = new Product("product A", new
         * Color("red", "red"), new Size("" + size, Sex.FEMALE)); products.add(product); } for (int
         * size = 5; size < 13; size++) { Product product = new Product("product B", new
         * Color("black", "black"), new Size("" + size, Sex.MALE)); products.add(product); }
         * 
         * for (int size = 5; size < 13; size++) { Product product = new Product("product B", new
         * Color("green","green"), new Size("" + size, Sex.MALE)); products.add(product); } for (int
         * size = 5; size < 13; size++) { Product product = new Product("product B", new
         * Color("yellow","yellow"), new Size("" + size, Sex.MALE)); products.add(product); }
         * products.add(new Product("nullColor", null, null));
         */
        for (int i = 0; i < 5; i++) {
            Product product = new Product("product " + i, new Color("color " + i, "color " + i), new Size("size " + i, new Sex("sex " + i)));
            products.add(product);
        }
        for (int i = 6; i < 10; i++) {
            Product product = new Product("product " + i, new Color("color " + i, null), new Size("size " + i, new Sex(null)));
            products.add(product);
        }
        // SHUFFLE
        Collections.shuffle(products);

        // DISPLAY
        System.out.println("SHUFFLED");
        Iterator it = products.iterator();
        while (it.hasNext()) {
            Product product = (Product) it.next();
            System.out.println(product);
        }

        // Sort
        /*
         * String[] sortFields = new String[] { "name", "color.name", "size.name" }; boolean[]
         * ascendings = new boolean[] { true, true, true }; BoComparator comparator = new
         * BoComparator(sortFields, ascendings);
         */
        Comparator comparator = new BeanComparator("size.sex.name", true);
        Collections.sort(products, comparator);

        // DISPLAY
        System.out.println("SORTED");
        it = products.iterator();
        while (it.hasNext()) {
            Product product = (Product) it.next();
            System.out.println(product);
        }

    }

    public void testSortColor() throws Exception {
        List colors = new ArrayList();

        // BUILD LIST
        for (int i = 0; i < 5; i++) {
            Color color = new Color("color " + i, "color " + i);
            colors.add(color);
        }
        for (int i = 6; i < 10; i++) {
            Color color = new Color("color " + i, null);
            colors.add(color);
        }

        // SHUFFLE
        Collections.shuffle(colors);

        // DISPLAY
        System.out.println("SHUFFLED");
        Iterator it = colors.iterator();
        while (it.hasNext()) {
            Color color = (Color) it.next();
            System.out.println(color);
        }

        // Sort
        /*
         * String[] sortFields = new String[] { "name", "color.name", "size.name" }; boolean[]
         * ascendings = new boolean[] { true, true, true }; BoComparator comparator = new
         * BoComparator(sortFields, ascendings);
         */
        Comparator comparator = new BeanComparator("description", true);
        Collections.sort(colors, comparator);

        // DISPLAY
        System.out.println("SORTED");
        it = colors.iterator();
        while (it.hasNext()) {
            Color color = (Color) it.next();
            System.out.println(color);
        }

    }
}
