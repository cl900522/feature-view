package acme.me.j2se.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/9 09:37
 * @description: java-feature-view
 */
@Slf4j
public class CyclicBarrierExample {

    private static CyclicBarrier barrier = new CyclicBarrier(5);

    @Test
    public void test1() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race1(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
            Thread.sleep(100);
        }
        executor.shutdown();
    }

    private static void race1(int threadNum) throws Exception {
        log.info("{} is ready", threadNum);
        barrier.await();
        log.info("{} continue", threadNum);
    }

    @Test
    public void test2() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race2(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race2(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.warn("BarrierException", e);
        }
        log.info("{} continue", threadNum);
    }

    private static CyclicBarrier barrier3 = new CyclicBarrier(5, () -> {
        log.info("callback is running");
    });

    @Test
    public void main() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race3(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
            Thread.sleep(100);
        }
        executor.shutdown();
    }

    private static void race3(int threadNum) throws Exception {
        log.info("{} is ready", threadNum);
        barrier3.await();
        log.info("{} continue", threadNum);
    }
}
