package cyrille.misc;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class Arithmetics {
    public static void main(String[] args) {
        Arithmetics arithmetics = new Arithmetics();
        try {
            arithmetics.modulo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modulo() throws Exception {
        int max = 52;
        for (int i = 0; i < 2 * max; i++) {
            System.out.println(i + "%" + max + "\t= " + (i % max));
        }
    }
}
