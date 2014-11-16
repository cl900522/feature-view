package acme.me.j2se.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpRunable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ExtThread.class);
    public void run() {
        logger.error("ImpRunable "+Thread.currentThread().getName()+" is running!");
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
