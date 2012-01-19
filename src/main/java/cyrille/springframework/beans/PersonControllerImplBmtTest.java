/*
 * Created on Feb 14, 2007
 */
package cyrille.springframework.beans;

import java.util.List;

import junit.framework.TestCase;
import cyrille.sample.person.Account;
import cyrille.sample.person.Person;
import cyrille.springframework.hibernate3.AccountDao;
import cyrille.springframework.hibernate3.PersonDao;

public class PersonControllerImplBmtTest extends TestCase {

    public void testCreateBankAccount() {
        PersonDao personDao = new PersonDao() {

            public Person loadPersonById(Long id) {
                return null;
            }

            public void saveOrUpdate(Person person) {

            }

            public List<Person> loadPersonsByName(String name) {
                return null;
            }
        };
        AccountDao accountDao = new AccountDao() {

            public Account loadAccountById(Long id) {
                // TODO Auto-generated method stub
                return null;
            }

            public List<Account> loadAccountsByPerson(Person person) {
                // TODO Auto-generated method stub
                return null;
            }

            public void saveOrUpdate(Account account) {
                // TODO Auto-generated method stub

            }

        };

        PersonControllerCmtImpl personControllerCmtImpl = new PersonControllerCmtImpl(accountDao, personDao);

        Person person = new Person("Le Clerc", "Cyrille", null);

        personControllerCmtImpl.createBankAccount(person, 100);

        assertEquals("accounts.size", 1, person.getAccounts().size());
        Account actualAccount = person.getAccounts().get(0);
        assertEquals("amount", 100, actualAccount.getAmount());
    }
}
