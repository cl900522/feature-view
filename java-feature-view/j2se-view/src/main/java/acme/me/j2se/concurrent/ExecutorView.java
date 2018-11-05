package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程运行返回值得
 * @author 明轩
 */
public class ExecutorView {

    public class MyCallAble implements Callable<String> {
        private int id;

        public MyCallAble(int m) {
            id = m;
        }

        public String call() throws Exception {
            System.out.println("Executing thread now!");
            return "This thread is: " + id;


        }
    }

    @Test
    public void main() {
        System.out.println("1.###################################");
        FutureTask<String> futask = new FutureTask<String>(new MyCallAble(10));
        new Thread(futask).start();
        try {
            Thread.sleep(2000);
            System.out.println(futask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("2.###################################");
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<String> fu = pool.submit(new MyCallAble(20));
        try {
            Thread.sleep(2000);
            System.out.println(fu.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();

        System.out.println("3.###################################");
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<String> cs = new ExecutorCompletionService<String>(threadPool);
        for (int i = 1; i < 5; i++) {
            cs.submit(new MyCallAble(i));
        }
        System.out.println("loop to get result!");
        for (int i = 1; i < 5; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }

    @Test
    public void testSize() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 1000; i++) {
            Run1 r = new Run1(i);
            threadPool.execute(r);
        }
//        try {
//            threadPool.awaitTermination(10, TimeUnit.SECONDS); //最佳等待方案
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
        threadPool.shutdown();
        while (threadPool.getPoolSize() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        while(threadPool.getPoolSize() != 0); //效率极度地下
    }

    static public class Run1 implements Runnable {
        private static final AtomicInteger counter = new AtomicInteger(0);
        public Integer value;

        public Run1(Integer value) {
            this.value = value;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int incrementAndGet = counter.incrementAndGet();
            System.out.println("Value is:" + value);
        }

    }
}
