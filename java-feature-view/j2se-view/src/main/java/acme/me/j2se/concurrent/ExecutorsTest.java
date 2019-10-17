package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/22 11:16
 * @description: java-feature-view
 */
public class ExecutorsTest {

    class CountedThreadFactory implements ThreadFactory {
        private String name;
        private Integer count;

        public CountedThreadFactory(String name) {
            this.name = name;
            count = 0;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(name + "-" + count);
            count++;
            return thread;
        }

        public String toString() {
            return "Has created [" + count + "] threads";
        }
    }

    /**
     * @see ThreadPoolExecutor
     */
    @Test
    public void test1() throws InterruptedException {
        CountedThreadFactory threadFactory = new CountedThreadFactory("test1");
        ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);
        executorService.submit(() -> {
            System.out.println("No thing1");
        });
        executorService.submit(() -> {
            System.out.println("No thing2");
        });

        Thread.sleep(100000);
        System.out.println(threadFactory.toString());
    }

    /**
     * @see ScheduledThreadPoolExecutor
     */
    @Test
    public void test2() throws InterruptedException {
        CountedThreadFactory threadFactory = new CountedThreadFactory("schedual-test1");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2, threadFactory);
        executorService.schedule(() -> {
            System.out.println("Delay output");
        }, 1000, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(() -> {
            System.out.println("FixedRate output:" + System.currentTimeMillis());
        }, 100, 1000, TimeUnit.MILLISECONDS);

        executorService.scheduleWithFixedDelay(() -> {
            System.out.println("FixedDelay output:" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }, 100, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(60000);
        System.out.println(threadFactory.toString());
    }

    @Test
    public void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            final Integer index = i;
            executorService.submit(() -> {
                Long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 1000L) {
                }
                System.out.println("Finish runnable: " + index);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(20L,TimeUnit.SECONDS);
    }

    @Test
    public void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            final Integer index = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("Runnable interrupt: " + index);
                }
                System.out.println("Finish runnable: " + index);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(20L, TimeUnit.SECONDS);
    }

    @Test
    public void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            final Integer index = i;
            executorService.submit(() -> {
                Long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 1000L) {
                }
                System.out.println("Finish runnable: " + index);
            });
        }
        executorService.shutdownNow();
        executorService.awaitTermination(20L,TimeUnit.SECONDS);
    }

    @Test
    public void test6() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            final Integer index = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.out.println("Runnable interrupt: " + index);
                }
                System.out.println("Finish runnable: " + index);
            });
        }
        executorService.shutdownNow();
        executorService.awaitTermination(20L, TimeUnit.SECONDS);
    }
}
