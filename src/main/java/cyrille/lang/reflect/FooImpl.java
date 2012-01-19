/*
 * Created on Sep 23, 2004
 */
package cyrille.lang.reflect;

import java.sql.Timestamp;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc </a>
 */
public class FooImpl implements Foo {

    private String serverName;

    private Foo proxifiedFoo;
    
    public FooImpl(String serverName) {
        super();
        this.serverName = serverName;
    }

    /**
     * @see cyrille.lang.reflect.Foo#doJob(java.lang.String)
     */
    public String doJob(String name) {

        System.out.println(FooImpl.class.getName() + " > doJob(serverName=" + this.serverName + "name=" + name + ")");
        String result = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println(FooImpl.class.getName() + " < doJob()" + result);

        return result;
    }

    public String doAnotherJob(String name) {
        System.out.println(FooImpl.class.getName() + " > doAnotherJob(serverName=" + this.serverName + "name=" + name + ")");
        String result = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println(FooImpl.class.getName() + " < doAnotherJob()" + result);
        proxifiedFoo.doaThirdJob(name);
        return result;
    }

    public String doaThirdJob(String name) {
        System.out.println(FooImpl.class.getName() + " > doaThirdJob(serverName=" + this.serverName + "name=" + name + ")");
        String result = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println(FooImpl.class.getName() + " < doaThirdJob()" + result);
        return result;
    }

    public void setProxifiedFoo(Foo proxifiedFoo) {
        this.proxifiedFoo = proxifiedFoo;
    }

}