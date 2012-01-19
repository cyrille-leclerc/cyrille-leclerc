/*
 * Created on Mar 11, 2007
 */
package cyrille.jms.sample.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Server {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(Server.class.getResource("beans.xml").toExternalForm());
        System.out.println("JMS server component started");
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
