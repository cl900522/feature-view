package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/21 10:44
 * @description: java-feature-view
 */
public class QueueTest {
    @Test
    public void test1() throws InterruptedException {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                queue.add("q" + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                queue.add("TDelayed" + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                queue.poll();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println(queue);
    }

    @Test
    public void test2() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        queue.add("add");
        queue.add("add2");
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    @Test
    public void test3() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue();
        queue.add("add");
        queue.add("add2");
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        System.out.println(queue.poll());//获取空
        System.out.println(queue.take());//阻塞直到队列有新的对象
    }

    @Test
    public void test4() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(5);
        queue.add("add");
        queue.add("add2");
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        System.out.println(queue.poll());//获取空
        System.out.println(queue.take());//阻塞直到队列有新的对象
    }

    @Test
    public void test5() throws InterruptedException {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue(2);
        queue.add("2");
        System.out.println(queue.toString());
        queue.add("4");
        System.out.println(queue.toString());
        queue.add("6");
        System.out.println(queue.toString());
        queue.add("1");
        System.out.println(queue.toString());
        queue.add("3");
        System.out.println(queue.toString());
        queue.add("5");
        System.out.println(queue.toString());

        System.out.println("Polling ....");
        //自动排序
        System.out.println(queue.poll());
        System.out.println(queue.toString());
        System.out.println(queue.poll());
        System.out.println(queue.toString());
        System.out.println(queue.poll());
        System.out.println(queue.toString());
        System.out.println(queue.poll());
        System.out.println(queue.toString());
        System.out.println(queue.poll());
        System.out.println(queue.toString());
        System.out.println(queue.poll());
        System.out.println(queue.toString());

        System.out.println(queue.poll());//获取空
        System.out.println(queue.take());//阻塞直到队列有新的对象
    }

    class TDelayed implements Delayed {

        private long start;

        public TDelayed(long d) {
            this.start = d + System.currentTimeMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return (start - System.currentTimeMillis()) * 1000;
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof TDelayed) {
                return (int) (start - ((TDelayed) o).start);
            }
            return 0;
        }

        @Override
        public String toString() {
            return new Date(start).toString();
        }
    }

    @Test
    public void test6() throws InterruptedException {
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.add(new TDelayed(9000L));
        delayQueue.add(new TDelayed(6000L));
        delayQueue.add(new TDelayed(2000L));
        delayQueue.add(new TDelayed(1000L));

        System.out.println(delayQueue.poll());
        System.out.println(delayQueue.take());
    }

    @Test
    public void test7() {
        int a = 5;
        System.out.println(a);
        a = a >>> 1;
        System.out.println(a);
    }
}
