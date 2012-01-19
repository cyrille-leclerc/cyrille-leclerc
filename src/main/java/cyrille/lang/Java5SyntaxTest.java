/*
 * Created on Oct 27, 2005
 */
package cyrille.lang;

import java.util.ArrayList;
import java.util.List;

public class Java5SyntaxTest {

    public enum Rank {
        ACE, DEUCE, EIGHT, FIVE, FOUR, JACK, KING, NINE, QUEEN, SEVEN, SIX, TEN, THREE
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    private static final List<Java5SyntaxTest> protoDeck = new ArrayList<Java5SyntaxTest>();

    // Initialize prototype deck
    static {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                protoDeck.add(new Java5SyntaxTest(rank, suit));
            }
        }
    }

    public static ArrayList<Java5SyntaxTest> newDeck() {
        return new ArrayList<Java5SyntaxTest>(protoDeck); // Return copy of prototype deck
    }

    private final Rank rank;

    private final Suit suit;

    private Java5SyntaxTest(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank rank() {
        return this.rank;
    }

    public Suit suit() {
        return this.suit;
    }

    @Override
    public String toString() {
        return this.rank + " of " + this.suit;
    }

}
