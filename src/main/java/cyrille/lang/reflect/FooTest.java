package cyrille.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import junit.framework.TestCase;

public class FooTest extends TestCase {

    public void testDoJobDirectImpl() {
        System.out.println("> testDoJobDirectImpl");

        Foo foo = new FooImpl("my-server");
        foo.doJob("cyrille");

    }

    public void testDoJobWithProxy() {
        System.out.println("> testDoJobWithProxy");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InvocationHandler invocationHandler = new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method=" + method + "args=" + Arrays.asList(args));
                return null;
            }

        };

        Foo foo = (Foo) Proxy.newProxyInstance(classLoader, new Class[] { Foo.class }, invocationHandler);

        foo.doJob("cyrille");

    }

    public void testDoJobWithFooImplProxy() {
        System.out.println("> testDoJobWithFooImplProxy");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        final Foo fooImpl = new FooImpl("my-server");

        InvocationHandler invocationHandler = new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method=" + method + "args=" + Arrays.asList(args));
                
                // begin TX if necessary
                
                Object result = method.invoke(fooImpl, args);
                
                // commit/rollback TX if necessary
                return result;
            }

        };

        Foo foo = (Foo) Proxy.newProxyInstance(classLoader, new Class[] { Foo.class }, invocationHandler);

        foo.doJob("cyrille");

    }
    public void testDoAnotherJob() {
        System.out.println("> testDoJobWithFooImplProxy");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        final FooImpl fooImpl = new FooImpl("my-server");

        InvocationHandler invocationHandler = new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method=" + method + "args=" + Arrays.asList(args));
                
                // begin TX if necessary
                
                Object result = method.invoke(fooImpl, args);
                
                // commit/rollback TX if necessary
                return result;
            }

        };

        Foo foo = (Foo) Proxy.newProxyInstance(classLoader, new Class[] { Foo.class }, invocationHandler);

        fooImpl.setProxifiedFoo(foo);
        
        foo.doAnotherJob("cyrille");

    }
}
