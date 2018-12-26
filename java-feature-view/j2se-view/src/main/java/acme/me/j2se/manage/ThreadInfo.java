package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadInfo {

    @Test
    public void monitor() {
        printThreadInfo();
    }


    private static void printThreadInfo() {
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        System.out.println("ObjectName=" + thread.getObjectName());
        System.out.println("仍活动的线程总数=" + thread.getThreadCount());
        System.out.println("峰值=" + thread.getPeakThreadCount());
        System.out.println("线程总数（被创建并执行过的线程总数）=" + thread.getTotalStartedThreadCount());
        System.out.println("仍活动的守护线程（daemonThread）总数=" + thread.getDaemonThreadCount());

        //检查是否有死锁的线程存在
        long[] deadlockedIds = thread.findDeadlockedThreads();
        if (deadlockedIds != null && deadlockedIds.length > 0) {
            java.lang.management.ThreadInfo[] deadlockInfos = thread.getThreadInfo(deadlockedIds);
            System.out.println("死锁线程信息:");
            System.out.println("\t\t线程名称\t\t状态\t\t");
            for (java.lang.management.ThreadInfo deadlockInfo : deadlockInfos) {
                System.out.println("\t\t" + deadlockInfo.getThreadName() + "\t\t" + deadlockInfo.getThreadState()
                        + "\t\t" + deadlockInfo.getBlockedTime() + "\t\t" + deadlockInfo.getWaitedTime()
                        + "\t\t" + deadlockInfo.getStackTrace().toString());
            }
        } else {
            System.out.println("无死锁线程！");
        }
        long[] threadIds = thread.getAllThreadIds();
        if (threadIds != null && threadIds.length > 0) {
            java.lang.management.ThreadInfo[] threadInfos = thread.getThreadInfo(threadIds);
            System.out.println("所有线程信息:");
            System.out.println("\t\t线程名称\t\t\t\t\t状态\t\t\t\t\t线程id");
            for (java.lang.management.ThreadInfo threadInfo : threadInfos) {
                System.out.println("\t\t" + threadInfo.getThreadName() + "\t\t\t\t\t" + threadInfo.getThreadState()
                        + "\t\t\t\t\t" + threadInfo.getThreadId());
            }
        }

    }
}
