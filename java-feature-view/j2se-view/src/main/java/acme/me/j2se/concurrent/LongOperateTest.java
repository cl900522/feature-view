package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 11:22
 * @description: java-feature-view
 */
public class LongOperateTest {
    private AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrOne = new Integer[]{1, 2, 2, 0, 41, 23, 63, 0, 4, 0, 0, 54, 4, 2, 1, 3, 5, 0, 44, 0, 42, 0, 23};
    private static Integer[] arrTwo = new Integer[]{1, 2, 2, 0, 41, 0, 0, 23, 4, 47, 0, 0, 4, 2, 0, 3, 5, 23, 44, 0, 42, 3, 0};
    private static Integer[] arrThree = new Integer[]{1, 2, 2, 0, 41, 0, 0, 23, 4, 47, 0, 0, 4, 2, 0, 3, 5, 23, 44, 0, 42, 3, 0};

    private class Run1 implements Runnable {
        private Integer[] arr;

        Run1(Integer[] arr) {
            this.arr = arr;
        }

        @Override
        public void run() {
            for (Integer integer : arr) {
                if (integer == 0) {
                    atomicLong.incrementAndGet();
                }
            }
        }
    }

    @Test
    public void test1() {
        Thread thread1 = new Thread(new Run1(arrOne));
        Thread thread2 = new Thread(new Run1(arrTwo));
        Thread thread3 = new Thread(new Run1(arrThree));

        threadStartAndJoin(thread1, thread2, thread3);

        System.out.println("test1->0:" + atomicLong.get());
    }


    private LongAdder longAdder = new LongAdder();

    private class Run2 implements Runnable {
        private Integer[] arr;

        Run2(Integer[] arr) {
            this.arr = arr;
        }

        @Override
        public void run() {
            for (Integer integer : arr) {
                if (integer == 0) {
                    longAdder.add(1L);
                }
            }
        }
    }

    @Test
    public void test2() {
        Thread thread1 = new Thread(new Run2(arrOne));
        Thread thread2 = new Thread(new Run2(arrTwo));
        Thread thread3 = new Thread(new Run2(arrThree));

        threadStartAndJoin(thread1, thread2, thread3);

        System.out.println("test2->0:" + longAdder.sum());
    }

    /* LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
        @Override
        public long applyAsLong(long left, long right) {
            return left + right;
        }
    }, 0L); */

    LongAccumulator longAccumulator = new LongAccumulator(((left, right) -> left + right), 0L);

    private class Run3 implements Runnable {
        private Integer[] arr;

        Run3(Integer[] arr) {
            this.arr = arr;
        }

        @Override
        public void run() {
            for (Integer integer : arr) {
                if (integer == 0) {
                    longAccumulator.accumulate(1L);
                }
            }
        }
    }

    @Test
    public void test3() {


        Thread thread1 = new Thread(new Run3(arrOne));
        Thread thread2 = new Thread(new Run3(arrTwo));
        Thread thread3 = new Thread(new Run3(arrThree));

        threadStartAndJoin(thread1, thread2, thread3);

        System.out.println("test3->0:" + longAccumulator.get());

    }

    private void threadStartAndJoin(Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
