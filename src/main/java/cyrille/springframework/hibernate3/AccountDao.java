/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import java.util.List;

import cyrille.sample.person.Account;
import cyrille.sample.person.Person;

public interface AccountDao {

    List<Account> loadAccountsByPerson(Person person);

    Account loadAccountById(Long id);

    void saveOrUpdate(Account account);
}