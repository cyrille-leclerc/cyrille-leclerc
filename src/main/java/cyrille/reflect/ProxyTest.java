package cyrille.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import junit.framework.TestCase;

public class ProxyTest extends TestCase {

    public static interface MonService {

        public String doToto(String param);
    }

    public static class MonServiceImpl implements MonService {

        public String doToto(String param) {
            System.out.println(">doToto(" + param + ")");

            return "OK";
        }
    }

    public static class MonInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoke(method=" + method + ", args=" + Arrays.toString(args));
            return "proxified response";
        }

    }

    public static class MonInvocationHandlerQuiWrappe implements InvocationHandler {

        MonService implementation;

        public MonInvocationHandlerQuiWrappe(MonService implementation) {
            super();
            this.implementation = implementation;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoke(method=" + method + ", args=" + Arrays.toString(args));
            try {
                // begin if not already begun and config says "require tx"
                Object result = method.invoke(this.implementation, args);
                // commit if begun on the invocation
                return result;
            } catch (Exception e) {
                // rollback if begun in this invocation
                // otherwise setRollbackOnly
                throw e;
            }
        }

    }

    public void testWithoutProxy() throws Exception {
        MonService monService = new MonServiceImpl();

        String response = monService.doToto("without proxy");

        System.out.println(response);
    }

    public void testWithProxy() throws Exception {
        System.out.println("> testWithProxy");

        InvocationHandler invocationHandler = new MonInvocationHandler();
        MonService monService = (MonService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { MonService.class },
                invocationHandler);
        String response = monService.doToto("test proxified");
        System.out.println(response);

    }

    public void testWithProxyQuiWrappe() throws Exception {
        System.out.println("> testWithProxyQuiWrappe");
        MonServiceImpl monServiceImpl = new MonServiceImpl();
        InvocationHandler invocationHandler = new MonInvocationHandlerQuiWrappe(monServiceImpl);
        MonService monService = (MonService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { MonService.class },
                invocationHandler);
        String response = monService.doToto("test proxified qui wrappe");
        System.out.println(response);

    }
}
