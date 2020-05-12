package acme.me.j2se.concurrent;

import lombok.ToString;
import org.junit.Test;
import sun.misc.Contended;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VolatileTest {
    public final static long ITERATIONS = 500L * 1000L * 1000L;

    @ToString
    private class ContentField {
        @Contended
        protected int a;

        @Contended
        protected int b;

        @Contended
        protected int c;

        @Contended
        protected int d;
    }

    @ToString
    private class ContentField2 {
        @Contended("group1")
        protected int a;

        @Contended("group1")
        protected int b;

        @Contended("group1")
        protected int c;

        @Contended("group1")
        protected int d;
    }

    @ToString
    private class ContentField3 {
        @Contended("group1")
        protected int a;

        @Contended("group1")
        protected int b;

        @Contended("group2")
        protected int c;

        @Contended("group2")
        protected int d;
    }


    @Test
    public void test() throws Exception {
        Integer size = 4;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        ContentField contentField = new ContentField();

        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            new Thread(new PlusRunnable(i, contentField, countDownLatch)).start();
        }
        countDownLatch.await();
        System.out.println(contentField);
        System.out.println(System.currentTimeMillis() - start);

        countDownLatch = new CountDownLatch(size);
        ContentField2 contentField2 = new ContentField2();

        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            new Thread(new PlusRunnable2(i, contentField2, countDownLatch)).start();
        }
        countDownLatch.await();
        System.out.println(contentField2);
        System.out.println(System.currentTimeMillis() - start);

    }


    @Test
    public void test2() throws Exception {
        Integer size = 2;
        ContentField3 contentField = new ContentField3();

        List<Thread> threadList = new ArrayList<>(2);
        for (int i = 0; i < size; i++) {
            threadList.add(new Thread(new PlusRunnable3(i, contentField)));
        }
        long start = System.currentTimeMillis();
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        System.out.println(contentField);
        System.out.println(System.currentTimeMillis() - start);
        threadList.clear();

        //　start new threads
        contentField = new ContentField3();
        for (int i = 0; i < size; i++) {
            threadList.add(new Thread(new PlusRunnable4(i, contentField)));
        }
        start = System.currentTimeMillis();
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        System.out.println(contentField);
        System.out.println(System.currentTimeMillis() - start);
    }


    private class PlusRunnable implements Runnable {
        int index;
        ContentField contentField;
        CountDownLatch countDownLatch;

        public PlusRunnable(int index, ContentField contentField, CountDownLatch countDownLatch) {
            this.index = index;
            this.contentField = contentField;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                switch (index) {
                    case 0:
                        contentField.a += i;
                        break;
                    case 1:
                        contentField.b += i;
                        break;
                    case 2:
                        contentField.c += i;
                        break;
                    case 3:
                        contentField.d += i;
                        break;
                }
            }

            countDownLatch.countDown();
        }
    }

    private class PlusRunnable2 implements Runnable {
        int index;
        ContentField2 contentField;
        CountDownLatch countDownLatch;

        public PlusRunnable2(int index, ContentField2 contentField, CountDownLatch countDownLatch) {
            this.index = index;
            this.contentField = contentField;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                switch (index) {
                    case 0:
                        contentField.a += i;
                        break;
                    case 1:
                        contentField.b += i;
                        break;
                    case 2:
                        contentField.c += i;
                        break;
                    case 3:
                        contentField.d += i;
                        break;
                }
            }

            countDownLatch.countDown();
        }
    }

    private class PlusRunnable3 implements Runnable {
        int index;
        ContentField3 contentField;

        public PlusRunnable3(int index, ContentField3 contentField) {
            this.index = index;
            this.contentField = contentField;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                switch (index) {
                    case 0:
                        contentField.a += i;
                        contentField.b += i;
                        break;
                    case 1:
                        contentField.c += i;
                        contentField.d += i;
                        break;
                }
            }
        }
    }

    private class PlusRunnable4 implements Runnable {
        int index;
        ContentField3 contentField;

        public PlusRunnable4(int index, ContentField3 contentField) {
            this.index = index;
            this.contentField = contentField;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                switch (index) {
                    case 0:
                        contentField.a += i;
                        contentField.c += i;
                        break;
                    case 1:
                        contentField.b += i;
                        contentField.d += i;
                        break;
                }
            }
        }
    }

}
