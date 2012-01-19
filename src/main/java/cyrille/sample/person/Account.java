/*
 * Created on Feb 14, 2007
 */
package cyrille.sample.person;

public class Account {

    protected int amount;

    public Account() {
        super();
    }

    public Account(int amount) {
        super();
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
