package acme.me.j2se.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadView {
    private static Logger logger = LoggerFactory.getLogger(ThreadView.class);

    List<Thread> threadList;
    public ThreadView(){
        threadList = new ArrayList<Thread>();
    }

    public static void main(String[] args) throws InterruptedException{
        logger.info("define th1.###########################");
        ExtThread m = new ExtThread();
        logger.info("define th2.###########################");
        Thread n = new Thread(new ImpRunable());
        m.start();
        n.start();
    }
}
