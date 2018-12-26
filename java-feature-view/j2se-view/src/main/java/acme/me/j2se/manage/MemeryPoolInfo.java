package acme.me.j2se.manage;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.Arrays;
import java.util.List;

public class MemeryPoolInfo {
    static final long MB = 1024 * 1024;

    @Test
    public void monitor() {
        printMemoryPoolInfo();
    }

    private static void printMemoryPoolInfo(){
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        if(pools != null && !pools.isEmpty()){
            for(MemoryPoolMXBean pool : pools){
                //只打印一些各个内存区都有的属性，一些区的特殊属性，可看文档或百度
                //最大值，初始值，如果没有定义的话，返回-1，所以真正使用时，要注意
                System.out.println("vm内存区:\n\t名称="+pool.getName()+"\n\t所属内存管理者="+ Arrays.deepToString(pool.getMemoryManagerNames())
                        +"\n\tObjectName="+pool.getObjectName()+"\n\t初始大小(M)="+pool.getUsage().getInit()/MB
                        +"\n\t最大(上限)(M)="+pool.getUsage().getMax()/MB
                        +"\n\t已用大小(M)="+pool.getUsage().getUsed()/MB
                        +"\n\t已提交(已申请)(M)="+pool.getUsage().getCommitted()/MB
                        +"\n\t使用率="+(pool.getUsage().getUsed()*100/pool.getUsage().getCommitted())+"%");

            }
        }
    }
}
