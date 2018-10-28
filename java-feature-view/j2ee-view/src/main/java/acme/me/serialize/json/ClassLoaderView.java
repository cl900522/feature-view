package acme.me.serialize.json;

import java.net.URL;

public class ClassLoaderView {
    public static void main(String[] args) {
        /**
         * bootstrap classloader －引导（也称为原始）类加载器，它负责加载Java的核心类。
         * 在Sun的JVM中，在执行java的命令中使用-Xbootclasspath选项或使用 - D选项指定sun.boot.class.path系统属性值可以指定附加的类。
         * 这个加载器的是非常特殊的，它实际上不是 java.lang.ClassLoader的子类，而是由JVM自身实现的。
         */
        URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
          System.out.println(urls[i].toExternalForm());
        }

        /**
         * extension classloader是system classloader的parent，而bootstrap classloader是extension classloader的parent，
         * 但它不是一个实际的classloader，所以为null。
         */
        String a = new String("");
        ClassLoader extensionClassLoader = a.getClass().getClassLoader();
        System.out.println(extensionClassLoader);
        /**
         * classloader 加载类用的是全盘负责委托机制。所谓全盘负责，即是当一个classloader加载一个Class的时候，
         * 这个Class所依赖的和引用的所有 Class也由这个classloader负责载入，除非是显式的使用另外一个classloader载入；
         * 委托机制则是先让parent（父）类加载器 (而不是super，它与parent classloader类不是继承关系)寻找，只有在parent找不到的时候才从自己的类路径中去寻找。
         * 此外类加载还采用了cache机制，也就是如果 cache中保存了这个Class就直接返回它，如果没有才从文件中读取和转换成Class，
         * 并存入cache，这就是为什么我们修改了Class但是必 须重新启动JVM才能生效的原因。
         */
        ClassLoader loader1 = ClassLoader.getSystemClassLoader();
        ClassLoaderView  classLoaderView = new ClassLoaderView();
        ClassLoader loader2 = classLoaderView.getClass().getClassLoader();

        System.out.println(loader1);
        System.out.println(loader2);

        if (loader1 == loader2) {
            System.out.println("Class loader and class  loader2 are the same");
        }
    }
}
