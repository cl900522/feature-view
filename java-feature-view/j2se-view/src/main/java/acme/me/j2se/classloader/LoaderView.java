package acme.me.j2se.classloader;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * JVM在运行时会产生三个ClassLoader，Bootstrap ClassLoader、Extension ClassLoader和App ClassLoader。其中，
 * Bootstrap ClassLoader是用C++编写的，在Java中看不到它，是null。它用来加载核心类库，就是在lib下的类库，
 * Extension ClassLoader加载lib/ext下的类库，
 * App ClassLoader加载Classpath里的类库，三者的关系为:App ClassLoader的Parent是Extension ClassLoader，
 * <p>
 * 而Extension ClassLoader的Parent为Bootstrap ClassLoader。加载一个类时，首先BootStrap进行寻找，找不到再由Extension ClassLoader寻找，最后才是App ClassLoader。
 * 将ClassLoader设计成委托模型的一个重要原因是出于安全考虑，比如在Applet中，如果编写了一个java.lang.String类并具有破坏性。
 * 假如不采用这种委托机制，就会将这个具有破坏性的String加载到了用户机器上，导致破坏用户安全。但采用这种委托机制则不会出现这种情况。因为要加载java.lang.String类时，
 * 系统最终会由Bootstrap进行加载，这个具有破坏性的String永远没有机会加载。
 *
 * @author ChenMx
 */
public class LoaderView {
    private static final Logger logger = LoggerFactory.getLogger(LoaderView.class);

    @Test
    public void bootClassLoader() throws InterruptedException {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }

        ClassLoader loader = new URLClassLoader(urls, this.getClass().getClassLoader());
        logger.info("Found classLoader: {}", loader.getClass().getName());
        while (loader.getParent() != null) {
            loader = loader.getParent();
            logger.info("Found classLoader: {}", loader.getClass().getName());
        }

        Thread.sleep(1000);
    }
}
