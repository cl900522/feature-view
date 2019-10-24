package acme.me.j2se;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 如果我们配置了-XX:-OmitStackTraceInFastThrow，再次运行，就不会看到Fast Throw优化后抛出的异常，全是包含了详细异常栈的异常信息。
public class OmitStackTest {
    @Test
    public void test1() throws InterruptedException {
        NPEThread npeThread = new NPEThread();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(npeThread);
            // 稍微sleep一下, 是为了不要让异常抛出太快, 导致控制台输出太快, 把有异常栈信息冲掉, 只留下fast throw方式抛出的异常
            Thread.sleep(2);
        }
    }

    static class NPEThread extends Thread {
        private static int count = 0;

        @Override
        public void run() {
            try {
                System.out.println(this.getClass().getSimpleName() + "--" + (++count));
                String str = null;
                // 制造空指针NPE
                System.out.println(str.length());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
