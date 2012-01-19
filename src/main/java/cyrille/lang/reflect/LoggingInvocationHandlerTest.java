/*
 * Created on Oct 6, 2004
 */
package cyrille.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import junit.framework.TestCase;

/**
 * @see java.lang.reflect.Proxy#newProxyInstance(java.lang.ClassLoader, java.lang.Class[],
 *      java.lang.reflect.InvocationHandler)
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class LoggingInvocationHandlerTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(LoggingInvocationHandlerTest.class);
    }

    public void testInvoke() {
        FooImpl fooImpl = new FooImpl("MyFoo");

        InvocationHandler invocationHandler = new LoggingInvocationHandler(fooImpl);
        Foo foo = (Foo) Proxy
                .newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { Foo.class }, invocationHandler);
        System.out.println("Before doJob");
        foo.doJob("zeDude");
        System.out.println("After doJob");
    }

}