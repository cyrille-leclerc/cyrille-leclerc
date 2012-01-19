/*
 * Created on Feb 14, 2007
 */
package cyrille.springframework.beans;

import cyrille.sample.person.Person;

public interface PersonController {

    void createBankAccount(Person person, int amount);
}
