package acme.me.j2se.rare;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/15 09:30
 * @description: java-feature-view
 */
public class VariableInvisualable {

    public static int v = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        new Thread(threadA, "threadA").start();
        Thread.sleep(100);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();
    }

    static class ThreadA extends Thread {
        public void run() {
            while (true) {
                if (v == 1) {
                    //永远无法进入并输出结果
                    System.out.println(Thread.currentThread().getName() + " : v is " + v);
                    break;
                }
            }
        }
    }

    static class ThreadB extends Thread {
        public void run() {
            v++;
            System.out.println(Thread.currentThread().getName() + " : v is " + v);
        }
    }

    @Test
    public void test1() {

        for (; ; ) {
            final State state = new State();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    long ct = state.c;
                    boolean at = state.a;
                    if (ct == 2L) {
                        if(!at) {
                            System.out.println(at);
                        }

                        /*state.b += 1;
                        if (state.b != 2) {
                            System.out.println(state.b);
                        }*/
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    state.b = 1L;
                    state.a = true;
                    state.c = state.b + 1L;
                }
            }).start();
        }
    }


    static class State {
        private boolean a = false;
        private long b = 0L;
        private long c = 0L;
    }
}
