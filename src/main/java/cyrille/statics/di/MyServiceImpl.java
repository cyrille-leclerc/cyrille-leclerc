/*
 * Created on Feb 17, 2006
 */
package cyrille.statics.di;

import org.apache.commons.lang.Validate;

public class MyServiceImpl implements MyService {

    private MyDao m_myDao;

    public MyServiceImpl(MyDao myDao) {
        super();
        this.m_myDao = myDao;
    }

    public String doJob(String in) {
        Validate.notNull(in, "my message");
        return null;
    }

}
