package acme.me.j2se.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不建议使用此方法定义线程，因为采用继承Thread的方式定义线程后，你不能在继承其他的类了，导致程序的可扩展性大大降低。
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
}
