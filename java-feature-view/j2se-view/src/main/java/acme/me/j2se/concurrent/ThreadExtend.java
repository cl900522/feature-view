package acme.me.j2se.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * 不建议使用此方法定义线程，因为采用继承Thread的方式定义线程后，你不能在继承其他的类了，导致程序的可扩展性大大降低。
 *
 * @author 明轩
 */
public class ThreadExtend extends Thread {
    private static Logger logger = LoggerFactory.getLogger(ThreadExtend.class);

    @Override
    public void run() {
        logger.error(Thread.currentThread().getName() + " is running!");
        for (int i = 0; i < 100; i++) {
            if (i == 49) {
                try {
                    logger.error(Thread.currentThread().getName() + " will sleep");
                    Thread.sleep(10);
                    logger.error(Thread.currentThread().getName() + " waked up");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void extThreadTest() {
        Thread.currentThread().setName("#Main Tread");

        Thread thread = new ThreadExtend();
        thread.setName("$New Thread instance of ExtThread");
        thread.start();
        thread.run();
        System.out.println("All Finish");
    }

    @Test
    public void extRunnableTest() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.error(Thread.currentThread().getName() + " is running!");
            }
        });
        thread.setName("$New Thread instance of Runnable");
        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All Finish");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("Finish sub");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setDaemon(true);
        //t1.setDaemon(false);

        t1.start();
        try {
            Thread.sleep(5000);
            System.out.println("Finish main");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadLocalView() {
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));
        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());

        ThreadLocal<String> threadLocal = new ThreadLocal();
        threadLocal.set("Great");
        System.out.println(threadLocal.get());
        System.gc();
        System.out.println(threadLocal.get()); //threadLocal不会被gc回收，也就一直能获取到

    }
}
