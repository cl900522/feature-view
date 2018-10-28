package acme.me.j2se.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 休眠 sleep：在指定的毫秒数内让当前正在执行的线程休眠（暂停执行），此操作受到系统计时器和调度程序精度和准确性的影响。该线程不丢失任何监视器的所属权。
 * 通过调用sleep使任务进入休眠状态，在这种情况下，任务在指定的时间内不会运行。 调用sleep的时候锁并没有被释放。 Java
 * SE5引入了更加显示的sleep()版本作为TimeUnit类的一部分，这个方法允许你指定sleep()延迟的时间单元，因此可以提供更好的可阅读性。
 * TimeUnit还可以被用来执行转换，就想稍后你会在本书中看到的那样。
 */

/**
 * wait：调用wait使线程挂起，直到线程得到了notify或notifyAll消息，线程才会进入就绪状态。
 * 使你可以等待某个条件发生变化，而改变这个条件超出了当前方法的控制能力。
 * 线程的执行被挂起，对象上的锁被释放。意味着另一个任务可以获得这个锁。因此在改对象中的其他synchronized方法可以在wait期间被调用。
 * 就意味着生命“我已经刚刚做完能做的所有事情，因此我要在这里等待，但是我希望其他synchronized操作在条件适合的情况下才能够执行”
 */

/**
 * yield：如果知道已经完成了在run()方法的循环的一次迭代过程中所需要的工作，就可以给线程调度一个机制暗示：我的工作已经做的差不多了，
 * 可以让给别的线程使用CPU了。通过调用yield()来实现。 当调用yield时，你也是在建议具有相同优先级的其他线程可以运行。
 * 对于任何重要的控制或在调整应用时，都不恩那个依赖于yield。实际上，yield经常被误用。（yield并不意味着退出和暂停，只是，
 * 告诉线程调度如果有人需要，可以先拿去，我过会再执行，没人需要，我继续执行） 调用yield的时候锁并没有被释放。
 */

/**
 * 中断 interrupt：Thread.interrupt()或者新类库里面通过Executor的submit()来获得Future
 * <?>返回值，这个Future提供cancel()以停止这个线程。
 * Thread类包含interrupt()方法，因此你可以终于被阻塞的任务，这个方法将设置线程的中断状态。如果一个线程已经被阻塞，或者视图执行一个阻塞操作，
 * 那么设置这个线程的终端状态将抛出InterruptedException。当抛出该异常或者该任何调用Thread.interrupted()时，
 * 中断状态将复位。 你在Executor上调用shutdownNow()，那么它将发送一个interrupt()调用给他启动的所有线程。
 */
public class ThreadOperate {
    private static Logger logger = LoggerFactory.getLogger(ThreadOperate.class);

    @Test
    public void sleepTest() {
        Thread waitThread = new Thread(new RunWait());
        waitThread.setName("$WaitThread");
        waitThread.start();

        Thread notifyThread = new Thread(new RunNotify());
        notifyThread.setName("$NotifyThread");
        notifyThread.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class RunWait implements Runnable {
        @Override
        public void run() {
            synchronized (ThreadOperate.class) {
                String threadName = Thread.currentThread().getName();
                System.out.println("Enter " + threadName);
                logger.info(threadName + " will sleep 5000ms!");
                try {
                    // 调用wait()方法，线程会放弃对象锁，进入等待此对象的等待锁定池
                    Thread.sleep(5000);
                    System.out.println("thread1 slept 5000ms");
                    Thread.yield();
                    ThreadOperate.class.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 is going on ....");
                System.out.println("thread1 is over!!!");
            }
        }
    }

    private static class RunNotify implements Runnable {
        @Override
        public void run() {
            synchronized (ThreadOperate.class) {
                System.out.println("enter thread2....");
                System.out.println("thread2 is sleep....");
                // 只有针对此对象调用notify()方法后本线程才进入对象锁定池准备获取对象锁进入运行状态。
                // 区别：
                // 如果我们把代码：ThreadOperate.class.notify();给注释掉，即ThreadOperate.class调用了wait()方法，但是没有调用notify()
                // 方法，则线程永远处于挂起状态。
                try {
                    ThreadOperate.class.notify();
                    // sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，
                    // 但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。
                    // 在调用sleep()方法的过程中，线程不会释放对象锁。
                    Thread.sleep(5000);
                    System.out.println("thread2 slept 5000ms");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is going on....");
                System.out.println("thread2 is over!!!");
            }
        }
    }

    @Test
    public void interrupteTest() {
        try {
            Thread thread = new Thread(new Run3());
            thread.start();
            Thread.sleep(1000);
            thread.interrupt();

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Run3 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("To sleep first 5000");
                Thread.sleep(3000);

                System.out.println("Sleeped first 5000");
            } catch (InterruptedException e) {
                Boolean isIn = Thread.currentThread().isInterrupted();
                System.out.println(isIn);

                Thread.currentThread().interrupt();
                try {
                    System.out.println("To sleep another 5000");
                    /* interrupt 之后无法再调用sleep */
                    Thread.sleep(3000);
                    System.out.println("Sleeped another 5000");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Test
    public void joinTest() {
        try {
            Thread thread = new Thread(new Run4());
            thread.start();

            logger.info("Let thread run 1000ms");
            thread.join(1000);
            logger.info("Thread not finished");
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Run4 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("To will sleep 5000");
                Thread.sleep(5000);
                System.out.println("Sleeped 5000");
            } catch (InterruptedException e) {
            }
        }
    }
}
