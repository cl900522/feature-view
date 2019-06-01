package acme.me.j2se.rare;

import acme.me.j2se.jni.Student;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 虚引用模拟
 *
 * @author: cdchenmingxuan
 * @date: 2019/2/27 14:54
 * @description: java-feature-view
 */
public class ReferenceViewTest {
    @Test
    public void pTest1() throws InterruptedException {
        ReferenceQueue<BigData> queue = new ReferenceQueue<BigData>();
        List<PhantomReference> references = new ArrayList<>();
        do {
            Reference<? extends BigData> poll = queue.remove(500);

            if (poll != null) {
                //如果不增加
                references.remove(poll);
            }

            BigData hello = new BigData(1024 * 1024 * 10);

            PhantomReference<BigData> pr = new PhantomReference<BigData>(hello, queue);
            references.add(pr);
            System.out.println("PUT 10m");
            hello = null;
            System.gc();
            Thread.sleep(1000);
            System.out.println("isEnqueue:" + pr.isEnqueued());
        } while (true);
    }

    @Test
    public void pTest() throws InterruptedException {
        Student hello = new Student();
        ReferenceQueue<Student> queue = new ReferenceQueue<Student>();
        PhantomReference<Student> pr = new PhantomReference<Student>(hello, queue);
        System.gc();
        Thread.sleep(1000);//未回收
        System.out.println(pr.isEnqueued());

        System.gc();
        Thread.sleep(1000);//未回收
        System.out.println(pr.isEnqueued());

        hello = null;
        System.gc();
        Thread.sleep(1000);//回收
        System.out.println(pr.isEnqueued());

        Boolean isRun = true;
        new Thread() {
            public void run() {
                while (isRun) {
                    try {
                        Field rereferent = Reference.class.getDeclaredField("referent");
                        rereferent.setAccessible(true);
                        Object result = rereferent.get(pr);
                        System.out.println("gc will collect："
                                + result.getClass() + "@"
                                + result.hashCode() + "\t"
                                + (Student) result);
                        System.gc();
                        System.out.println(pr.isEnqueued());
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Thread.sleep(10*60000);
    }


    @Test
    public void wTest() {
        String hello = new String("Hello");
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));

        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());
    }

    @Test
    public void OtEST() {
        AtomicInteger i = new AtomicInteger(0);
        i.incrementAndGet();
        i.incrementAndGet();

        System.out.println(i.get());
    }
}
