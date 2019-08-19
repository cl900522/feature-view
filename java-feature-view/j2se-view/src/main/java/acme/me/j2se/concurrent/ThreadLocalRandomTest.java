package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 09:29
 * @description: java-feature-view
 */
public class ThreadLocalRandomTest {
    private static Random random = new Random();

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(random.nextInt(1000));
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

                    System.out.println(threadLocalRandom.nextInt(100));
                }
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
