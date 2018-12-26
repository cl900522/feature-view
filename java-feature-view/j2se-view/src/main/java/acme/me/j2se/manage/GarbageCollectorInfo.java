package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

public class GarbageCollectorInfo {

    @Test
    public void monitor() {
        printGarbageCollectorInfo();
    }

    private static void printGarbageCollectorInfo(){
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbage : garbages){
            System.out.println("垃圾收集器：名称="+garbage.getName()+",收集="+garbage.getCollectionCount()+",总花费时间="
                    +garbage.getCollectionTime()+",内存区名称="+ Arrays.deepToString(garbage.getMemoryPoolNames()));
        }
    }

}
