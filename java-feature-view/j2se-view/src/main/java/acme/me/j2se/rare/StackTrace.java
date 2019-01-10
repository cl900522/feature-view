package acme.me.j2se.rare;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/10 16:19
 * @Description: java-feature-view
 */
public class StackTrace {
    private Logger logger = Logger.getLogger(StackTrace.class);

    @Test
    public void ps() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.getClassName());
            System.out.println(stackTraceElement.getMethodName());
            System.out.println(stackTraceElement.getFileName());
            System.out.println(stackTraceElement.getLineNumber());
            System.out.println("------------------");
        }
        new Throwable().printStackTrace();
    }


    @Test
    public void ps2() {
        printLineInfo();
    }

    private void printLineInfo() {
        StackTraceElement[] stackTraceArr = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceArr.length; i++) {
            StackTraceElement stackTraceElement = stackTraceArr[i];
            if (stackTraceElement.getClassName().equals(StackTrace.class.getName()) && stackTraceElement.getMethodName().equals("printLineInfo")) {
                stackTraceElement = stackTraceArr[i + 1];
                System.out.println(stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName() + "->" + stackTraceElement.getLineNumber());

            }
        }
    }

    @Test
    public void ps3() {
        logger.info("Here");
    }

}
