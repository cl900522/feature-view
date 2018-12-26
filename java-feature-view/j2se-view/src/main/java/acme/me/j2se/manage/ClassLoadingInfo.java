package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

public class ClassLoadingInfo {
    static final long MB = 1024 * 1024;

    @Test
    public void monitor() {
        printClassLoadingInfo();
    }

    private static void printClassLoadingInfo() {
        ClassLoadingMXBean classLoad = ManagementFactory.getClassLoadingMXBean();
        System.out.println("已加载类总数：" + classLoad.getTotalLoadedClassCount());
        System.out.println("已加载当前类：" + classLoad.getLoadedClassCount());
        System.out.println("已卸载类总数：" + classLoad.getUnloadedClassCount());

    }
}
