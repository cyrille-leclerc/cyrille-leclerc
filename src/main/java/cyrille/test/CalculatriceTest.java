package cyrille.test;

import junit.framework.TestCase;

public class CalculatriceTest extends TestCase {

    public void testAdditioner() throws Exception {
        Calculatrice calculatrice = new Calculatrice();

        int actual = calculatrice.additionner(1, 3);
        int expected = 4;

        assertEquals(expected, actual);
    }

    public void testDivisionParZero() throws Exception {
        Calculatrice calculatrice = new Calculatrice();
        try {
            calculatrice.diviser(5, 0);
        } catch (IllegalArgumentException e) {
            // OK car j'attends une erreur
            e.printStackTrace();
            return;
        }
        fail("Division par zéro attendue");
    }

    public void testDivisionErreurDeDivision() throws Exception {
        Calculatrice calculatrice = new Calculatrice();

        int actual = calculatrice.diviser(5, 3);
        int expected = 2;

        assertEquals(expected, actual);
    }
}
