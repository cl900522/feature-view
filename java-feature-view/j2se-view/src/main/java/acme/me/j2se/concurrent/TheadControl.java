package acme.me.j2se.concurrent;

import org.junit.Test;

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
}
