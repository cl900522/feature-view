package acme.me.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ExtThread.class);
    public void run() {
        logger.error("ImpThread "+Thread.currentThread().getName()+" is running!");
        for(int i=0;i<100;i++){
            if(i==49)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            System.out.println("--"+i);
        }
    }

}
