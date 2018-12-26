package acme.me.j2se.manage;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;

public class CompileInfo {



    private static void printCompilationInfo(){
        CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();
        System.out.println("JIT编译器名称："+compilation.getName());
        //判断jvm是否支持编译时间的监控
        if(compilation.isCompilationTimeMonitoringSupported()){
            System.out.println("总编译时间："+compilation.getTotalCompilationTime()+"秒");
        }
    }
}
