package acme.me.j2se.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程运行返回值得
 * @author 明轩
 */
public class ThreadWithResult implements Callable<String> {
    private static Logger logger = LoggerFactory.getLogger(ThreadWithResult.class);

    public String call() throws Exception {
        String threadName = Thread.currentThread().getName();
        logger.info(threadName + " will sleep 5000ms!");
        Thread.sleep(5000);
        logger.info(threadName + "slept 5000ms!");
        return "Ok";
    }

    @Test
    public void futureTask() {
        FutureTask<String> futask = new FutureTask<String>(new ThreadWithResult());
        Thread futureThread = new Thread(futask);
        futureThread.setName("$FutureThread");
        futureThread.start();

        try {
            logger.info("Result: " + futask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
