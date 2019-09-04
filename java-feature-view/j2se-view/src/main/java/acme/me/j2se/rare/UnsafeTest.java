package acme.me.j2se.rare;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
    static Unsafe unsafe;

    // 记录变量state在类Te st UnSafe 中的偏移位（ 2 . 2 . 2)
    static long stateOffset;

    //变量 2 . 3 )
    private volatile long state = 0;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);

            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());

            unsafe = Unsafe.getUnsafe();
        }
    }

    public static void main(String[] args) {
        //创建实例 ，并且设置state 直为 1(2.2 5)
        UnsafeTest test = new UnsafeTest();
        //(2 . 2 . 6)
        Boolean sucess = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println(sucess);
        sucess = unsafe.compareAndSwapLong(test, stateOffset, 1, 2);
        System.out.println(sucess);

    }

}
