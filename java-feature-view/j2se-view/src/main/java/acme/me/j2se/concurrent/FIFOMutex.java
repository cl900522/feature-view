package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 19:24
 * @description: java-feature-view
 */
public class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        //只有队首的线程可以获取锁（1)
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            System.out.println(Thread.currentThread().getName() + " park");
            LockSupport.park(this);
            System.out.println(Thread.currentThread().getName() + " unpark");
            if (Thread.interrupted())
                wasInterrupted = true;
        }

        waiters.remove();
        if (wasInterrupted)
            current.interrupt();
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

    @Test
    public void test1() {
        for (int i = 0; i < 5; i++) {
            final Integer t = i;
            Thread thread = new Thread(() -> {
                lock();
                System.out.println(Thread.currentThread().getName() + " run finish!");
                unlock();
            });
            thread.setName("ThreadName->" + i);
            thread.start();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }
}

