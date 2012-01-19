/*
 * Created on Nov 30, 2005
 */
package cyrille.management;

import java.sql.Timestamp;

import javax.management.Notification;
import javax.management.NotificationListener;

public class SystemOutNotificationListener implements NotificationListener {

    public SystemOutNotificationListener() {
        super();
    }

    public void handleNotification(Notification notification, Object handback) {
        System.out.println(new Timestamp(System.currentTimeMillis()) + ", handleNotification[notification=" + notification + ", handback="
                + handback);

    }
}
