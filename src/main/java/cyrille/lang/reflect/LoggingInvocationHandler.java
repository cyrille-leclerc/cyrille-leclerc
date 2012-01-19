/*
 * Created on Sep 27, 2004
 */
package cyrille.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class LoggingInvocationHandler implements InvocationHandler {

    private Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method,
     *      java.lang.Object[])
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(LoggingInvocationHandler.class.getName() + " > " + this.target.getClass().getName() + "." + method.getName());
        Object result;
        try {
            result = method.invoke(this.target, args);
        } finally {
            System.out.println(LoggingInvocationHandler.class.getName() + " < " + this.target.getClass().getName() + "." + method.getName());
        }
        return result;
    }
}