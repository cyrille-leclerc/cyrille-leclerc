<%@page import="java.util.concurrent.locks.ReentrantLock"%>
<%@page import="java.util.concurrent.locks.Lock"%>
<%!static Lock lock = new ReentrantLock();%>
<%
    System.out.println(Thread.currentThread().getName() + " - lock ...");
    lock.lock();
    try {
        System.out.println(Thread.currentThread().getName() + " - sleep ...");
        Thread.sleep(10 * 1000);
    } finally {
        lock.unlock();
        System.out.println(Thread.currentThread().getName() + " - unlocked bye");
    }
%>