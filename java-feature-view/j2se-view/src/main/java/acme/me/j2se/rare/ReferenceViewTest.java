package acme.me.j2se.rare;

import acme.me.j2se.jni.Student;
import org.junit.Test;

import java.lang.ref.*;
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
    /**
     *   如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；
     *   如果内存空间不足了，就会回收这些对象的内存。
     *   只要垃圾回收器没有回收它，该对象就可以被程序使用。
     *   软引用可用来实现内存敏感的高速缓存。
     */
    public void stest1() {
        String str = new String("abc");
        SoftReference<String> softRef = new SoftReference<String>(str);
        str = null;
        //配合上内存不足的条件和gc触发，就可以使得softRef保存的对象清理
        System.gc();
        System.out.println(softRef.get());
    }


    @Test
    /**
     *  弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。
     *  在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。
     *  不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
     */
    public void wTest() {
        WeakReference<String> sr = new WeakReference<String>(new String("hello")); // GC后无法获取

        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());


        String hello = new String("Hello");
        sr = new WeakReference<String>(hello);

        System.out.println(sr.get());
        System.gc();
        System.out.println(sr.get());//GC后还能够获取,应为hello对象还在引用字符串


        hello = null;
        System.out.println(sr.get());
        System.gc();
        System.out.println(sr.get()); //GC 后无法获取
    }

    @Test
    /**
     *   “虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。
     *   如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
     *
     *   虚引用主要用来跟踪对象被垃圾回收器回收的活动。
     *   虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列 （ReferenceQueue）联合使用。
     *   当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之 关联的引用队列中。
     */
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
        final ReferenceQueue<Student> queue = new ReferenceQueue<Student>();
        PhantomReference<Student> pr = new PhantomReference<Student>(hello, queue);
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

                        if (pr.isEnqueued()) {
                            queue.poll();
                        }

                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Thread.sleep(10 * 60000);
    }


    @Test
    public void OtEST() {
        AtomicInteger i = new AtomicInteger(0);
        i.incrementAndGet();
        i.incrementAndGet();

        System.out.println(i.get());
    }
}
