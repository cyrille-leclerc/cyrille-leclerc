package cyrille.test;

import org.apache.commons.lang.Validate;

public class Calculatrice {

    public int additionner(int val1, int val2) {

        return val1 + val2;
    }

    /**
     * 
     * @param operateur
     * @param diviseur
     *            ne doit pas être egal à zéro
     * @return
     */
    public int diviser(int operateur, int diviseur) {
        Validate.isTrue(false == (0 == diviseur), "diviseur = 0");
        return operateur / diviseur;
    }
}
