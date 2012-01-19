<%@page import="java.util.concurrent.locks.ReentrantLock"%>
<%@page import="java.util.concurrent.locks.Lock"%>
<%
    Lock lockB = (Lock)session.getAttribute("lockB");
    if (lockB == null) {
        lockB = new ReentrantLock();
        session.setAttribute("lockB", lockB);
    }

    Lock lockA = (Lock)session.getAttribute("lockA");
    if (lockA == null) {
        lockA = new ReentrantLock();
        session.setAttribute("lockA", lockA);
    }
    
    System.out.println(Thread.currentThread().getName() + " - lock A ...");
    lockB.lock();
    try {
        System.out.println(Thread.currentThread().getName() + " - sleep ...");
        Thread.sleep(10 * 1000);
        System.out.println(Thread.currentThread().getName() + " - lock B ...");
        lockA.lock();        
        Thread.sleep(10 * 1000);
    } finally {
        lockB.unlock();
        lockA.unlock();
        System.out.println(Thread.currentThread().getName() + " - unlocked bye");
    }
%>