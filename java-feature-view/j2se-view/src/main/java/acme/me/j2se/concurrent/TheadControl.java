package acme.me.j2se.concurrent;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public class TheadControl {
    public class PrintRunable implements Runnable {
        Object lock;
        private Integer i;

        PrintRunable(Object lock, Integer i) {
            this.lock = lock;
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (lock) {
                while (i < 100) {
                    System.out.println("" + i);
                    i += 2;

                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }
    }

    @Test
    public void con1() throws InterruptedException {
        Integer i = 1;
        Runnable r1 = new PrintRunable(i, 0);
        Runnable r2 = new PrintRunable(i, 1);
        new Thread(r1).start();
        new Thread(r2).start();
        Thread.sleep(500);
    }

    @Test
    public void con2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("t1 finish");
            } catch (InterruptedException e) {
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(8000);
                System.out.println("t2 finish");
            } catch (InterruptedException e) {
            }
        });
        t2.start();

        t1.join();
        t2.join();
    }

    /**
     * [5] Monitor Ctrl-Break // IDE 自带的
     * [4] Signal Dispatcher // 分发处理发送给JVM信号的线程
     * [3] Finalizer // 调用对象finalize方法的线程
     * [2] Reference Handler // 清除Reference的线程
     * [1] main // main线程，用户程序入口
     *
     * @param args
     */
    @Test
    public static void main(String[] args) throws InterruptedException {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName() + "->" + threadInfo.getThreadState());

        }

        TimeUnit.SECONDS.wait(1);
    }


}
