/*
 * Created on Feb 14, 2007
 */
package cyrille.springframework.beans;

import cyrille.sample.person.Account;
import cyrille.sample.person.Person;
import cyrille.springframework.hibernate3.AccountDao;
import cyrille.springframework.hibernate3.PersonDao;

public class PersonControllerCmtImpl implements PersonController {

    AccountDao accountDao;

    PersonDao personDao;

    /**
     * @param accountDao
     * @param personDao
     */
    public PersonControllerCmtImpl(AccountDao accountDao, PersonDao personDao) {
        super();
        this.accountDao = accountDao;
        this.personDao = personDao;
    }

    /**
     * @Transactional(propagation=)
     * @see cyrille.springframework.beans.PersonController#createBankAccount(cyrille.sample.person.Person,
     *      int)
     */
    public void createBankAccount(Person person, int amount) {
        Account account = new Account(amount);

        person.add(account);

        this.accountDao.saveOrUpdate(account);
        this.personDao.saveOrUpdate(person);

    }

}
