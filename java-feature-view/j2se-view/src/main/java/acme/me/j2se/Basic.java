package acme.me.j2se;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Basic {
    @Test
    public void test() {
        int a = 20;
        int b = 30;
        boolean c = false && true || false;
        System.out.println(c); //false
        Assert.assertFalse(c);
    }

    public static void main(String[] args) throws InterruptedException {
        Pattern p = Pattern.compile("[1-9]*");

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 10000; i++) {
            final Integer s = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Matcher matcher = p.matcher("" + s);
                    System.out.println(s + "->" + matcher.find());
                }
            });
        }

        Thread.sleep(5000);
    }

    /**
     * 关于intern方法作用：当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串 （该对象由 equals(Object) 方法确定），则返回池中的字符串。
     * 否则，将此 String 对象添加到池中， 并且返回此 String 对象的引用。
     */
    @Test
    public void test2() {
        String s = new String("1");
        s = s.intern();
        String s2 = "1";
        System.out.println(s == s2); //true
        Assert.assertTrue(s == s2);

        String s3 = new String("1") + new String("1");
        s3 = s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4); //true
        Assert.assertTrue(s3 == s3);

    }

    @Test
    /**
     * 位运算标记
     */
    public void test3() {
        Assert.assertTrue(1 << 0 == 1);
        Assert.assertTrue(1 << 1 == 2);

        int a = 1 << 2;
        int b = 1 << 4;
        int c = 1 << 6;
        int res = a | b;

        Assert.assertTrue((a & res) > 0);
        Assert.assertTrue((b & res) > 0);
        Assert.assertTrue((c & res) == 0);
    }
}
