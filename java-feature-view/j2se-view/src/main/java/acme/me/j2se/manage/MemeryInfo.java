package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * 打印堆和非堆内存信息
 */
public class MemeryInfo {
    static final long MB = 1024 * 1024;

    @Test
    public void monitor() {
        printMemoryInfo();
    }

    private static void printMemoryInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();
        System.out.println("head堆:");
        System.out.println("\t初始(M):" + headMemory.getInit() / MB);
        System.out.println("\t最大(上限)(M):" + headMemory.getMax() / MB);
        System.out.println("\t当前(已使用)(M):" + headMemory.getUsed() / MB);
        System.out.println("\t提交的内存(已申请)(M):" + headMemory.getCommitted() / MB);
        System.out.println("\t使用率:" + headMemory.getUsed() * 100 / headMemory.getCommitted() + "%");


        System.out.println("non-head非堆:");
        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();
        System.out.println("\t初始(M):" + nonheadMemory.getInit() / MB);
        System.out.println("\t最大(上限)(M):" + nonheadMemory.getMax() / MB);
        System.out.println("\t当前(已使用)(M):" + nonheadMemory.getUsed() / MB);
        System.out.println("\t提交的内存(已申请)(M):" + nonheadMemory.getCommitted() / MB);
        System.out.println("\t使用率:" + nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%");

        System.out.println("等待执行Finalization对象数：" + memory.getObjectPendingFinalizationCount());
    }
}
