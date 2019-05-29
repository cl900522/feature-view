package acme.me.j2se;

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
        System.out.println(c);
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
                    System.out.println(s+"->"+matcher.find());
                }
            });
        }

        Thread.sleep(5000);
    }
}
