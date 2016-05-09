package acme.me.j2se.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不建议使用此方法定义线程，因为采用继承Thread的方式定义线程后，你不能在继承其他的类了，导致程序的可扩展性大大降低。
 * @author 明轩
 *
 */
public class ExtThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(ExtThread.class);

    @Override
    public void run() {
        logger.error("ExtThread "+Thread.currentThread().getName()+" is running!");
        for(int i=0;i<100;i++){
            if(i==49)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            System.out.println("###"+i);
        }
    }

    public static void main(String[] args){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                exe();
            }
        });
        thread.run();
        System.out.println("Finish");
    }

    public static void exe (){
        System.out.println("Exe function do!");
    }
}
