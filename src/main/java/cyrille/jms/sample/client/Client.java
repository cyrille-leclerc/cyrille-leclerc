/*
 * Created on Mar 11, 2007
 */
package cyrille.jms.sample.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cyrille.jms.sample.CheckingAccountService;

public class Client {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(Client.class.getResource("beans.xml").toExternalForm());
        CheckingAccountService service = (CheckingAccountService) applicationContext.getBean("checkingAccountService");

        for (int i = 0; i < 10; i++) {
            System.out.println("Cancel account " + i);
            service.cancelAccount(new Long(i));
        }
    }
}
