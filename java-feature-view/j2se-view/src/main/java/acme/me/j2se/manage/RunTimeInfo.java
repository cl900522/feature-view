package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取vm运行时信息
 */
public class RunTimeInfo {
    @Test
    public void monitor() {
        printRuntimeInfo();
    }

    private static void printRuntimeInfo() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        System.out.println("进程PID=" + runtime.getName().split("@")[0]);
        System.out.println("jvm规范名称:" + runtime.getSpecName());
        System.out.println("jvm规范运营商:" + runtime.getSpecVendor());
        System.out.println("jvm规范版本:" + runtime.getSpecVersion());
        //返回虚拟机在毫秒内的开始时间。该方法返回了虚拟机启动时的近似时间
        System.out.println("jvm启动时间:" + new Date(runtime.getStartTime()));
        //相当于System.getProperties
        System.out.println("获取System.properties:");
        Map<String, String> systemProperties = runtime.getSystemProperties();
        for (Map.Entry<String, String> stringStringEntry : systemProperties.entrySet()) {
            System.out.println("\t" + stringStringEntry.getKey() + ": " + stringStringEntry.getValue());
        }

        System.out.println("jvm正常运行时间（毫秒）:" + runtime.getUptime());
        //相当于System.getProperty("java.vm.name").
        System.out.println("jvm名称:" + runtime.getVmName());
        //相当于System.getProperty("java.vm.vendor").
        System.out.println("jvm运营商:" + runtime.getVmVendor());
        //相当于System.getProperty("java.vm.version").
        System.out.println("jvm实现版本:" + runtime.getVmVersion());
        List<String> args = runtime.getInputArguments();
        if (args != null && !args.isEmpty()) {
            System.out.println("vm参数:");
            for (String arg : args) {
                System.out.println("\t" + arg);
            }
        }
        System.out.println("类路径:" + runtime.getClassPath());
        System.out.println("引导类路径:" + runtime.getBootClassPath());
        System.out.println("库路径:" + runtime.getLibraryPath());
    }
}
