/*
 * Created on Mar 11, 2007
 */
package cyrille.jms.sample.server;

import cyrille.jms.sample.CheckingAccountService;

public class SimpleCheckingAccountService implements CheckingAccountService {

    public void cancelAccount(Long accountId) {
        System.out.println("Cancelling account [" + accountId + "]");
    }

}
