package acme.me.j2se.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 17:34
 * @description: java-feature-view
 */
public class LockSupportTest {
    @Test
    public void test1() {
        /**
         * 因调用park（） 方法而被阻塞的线程被其他线程中断而返回时并不会抛出InterruptedException 异常。
         *
         */
        LockSupport.park();
        LockSupport.unpark(Thread.currentThread());
    }
}
