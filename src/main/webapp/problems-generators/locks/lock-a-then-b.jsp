<%@page import="java.util.concurrent.locks.ReentrantLock"%>
<%@page import="java.util.concurrent.locks.Lock"%>
<%
    Lock lockA = (Lock)session.getAttribute("lockA");
    if (lockA == null) {
        lockA = new ReentrantLock();
        session.setAttribute("lockA", lockA);
    }

    Lock lockB = (Lock)session.getAttribute("lockB");
    if (lockB == null) {
        lockB = new ReentrantLock();
        session.setAttribute("lockB", lockB);
    }
    
    System.out.println(Thread.currentThread().getName() + " - lock A ...");
    lockA.lock();
    try {
        System.out.println(Thread.currentThread().getName() + " - sleep ...");
        Thread.sleep(10 * 1000);
        System.out.println(Thread.currentThread().getName() + " - lock B ...");
        lockB.lock();        
        Thread.sleep(10 * 1000);
    } finally {
        lockA.unlock();
        lockB.unlock();
        System.out.println(Thread.currentThread().getName() + " - unlocked bye");
    }
%>