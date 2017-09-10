package acme.me.j2se.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程运行返回值得
 * @author 明轩
 *
 */
public class ExecutorView implements Callable<String> {

    private int id;

    public ExecutorView(int m) {
        id = m;
    }

    public String call() throws Exception {
        System.out.println("Executing thread now!");
        return "This thread is: " + id;
    }

    @Test
    public void main() {
        System.out.println("1.###################################");
        FutureTask<String> futask = new FutureTask<String>(new ExecutorView(10));
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
        Future<String> fu = pool.submit(new ExecutorView(20));
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
            cs.submit(new ExecutorView(i));
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

}
