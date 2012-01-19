/*
 * Created on May 7, 2006
 */
package cyrille.springframework.hibernate3;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import cyrille.sample.person.Person;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    private PlatformTransactionManager transactionManager;

    public PersonServiceImpl() {
        super();
    }

    public void doTheJob(final Long personId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(this.transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {

                Person personsToChange = PersonServiceImpl.this.personDao.loadPersonById(personId);
                personsToChange.setFirstName(personsToChange.getFirstName() + "-");
                PersonServiceImpl.this.personDao.saveOrUpdate(personsToChange);

            }
        });

    }

    public void doAnotherJob(final Long personId) {

        TransactionStatus transactionStatus = this.transactionManager.getTransaction(null);

        try {
            Person personsToChange = this.personDao.loadPersonById(personId);
            personsToChange.setFirstName(personsToChange.getFirstName() + "-");
            this.personDao.saveOrUpdate(personsToChange);
        } catch (Exception e) {
            this.transactionManager.rollback(transactionStatus);
        }
        this.transactionManager.commit(transactionStatus);

    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
