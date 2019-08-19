package acme.me.j2se.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 13:57
 * @description: java-feature-view
 */
public class SingleInstancePattern {

    private static volatile int tag = 0;
    private static Long offset = null;
    private static SingleInstancePattern intance = null;

    SingleInstancePattern() {
    }

    private static Unsafe unsafe = Unsafe.getUnsafe();

    static {
        Field tag = null;
        try {
            tag = SingleInstancePattern.class.getDeclaredField("tag");
            offset = unsafe.objectFieldOffset(tag);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public SingleInstancePattern getInstance() {
        for (; ; ) {
            if (unsafe.compareAndSwapInt(SingleInstancePattern.class, offset, 0, 1)) {
                // TODO init
            }
            break;
        }
        return intance;
    }
}
