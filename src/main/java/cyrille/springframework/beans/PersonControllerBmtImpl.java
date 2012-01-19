/*
 * Created on Feb 14, 2007
 */
package cyrille.springframework.beans;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import cyrille.sample.person.Account;
import cyrille.sample.person.Person;
import cyrille.springframework.hibernate3.AccountDao;
import cyrille.springframework.hibernate3.PersonDao;

public class PersonControllerBmtImpl implements PersonController {

    AccountDao accountDao;

    PersonDao personDao;

    PlatformTransactionManager platformTransactionManager;

    /**
     * @param accountDao
     * @param personDao
     */
    public PersonControllerBmtImpl(AccountDao accountDao, PersonDao personDao, PlatformTransactionManager platformTransactionManager) {
        super();
        this.accountDao = accountDao;
        this.personDao = personDao;
        this.platformTransactionManager = platformTransactionManager;
    }

    public void createBankAccount(Person person, int amount) {
        TransactionStatus transactionStatus = this.platformTransactionManager.getTransaction(new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRED));
        Account account = new Account(amount);

        person.add(account);

        this.accountDao.saveOrUpdate(account);
        this.personDao.saveOrUpdate(person);
        this.platformTransactionManager.commit(transactionStatus);
    }

}
