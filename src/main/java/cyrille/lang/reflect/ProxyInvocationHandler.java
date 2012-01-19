/*
 * Created on Jun 12, 2005
 */
package cyrille.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {

    private Object m_target;

    public ProxyInvocationHandler(Object target) {
        super();
        this.m_target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = this.m_target.getClass().getMethod(method.getName(), method.getParameterTypes());
        Object result = targetMethod.invoke(this.m_target, args);
        return result;
    }
}
