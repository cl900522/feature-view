package acme.me.j2se.schedule;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringQuartzView {
    @Test
    public void test1() {
        AbstractApplicationContext apc = new ClassPathXmlApplicationContext("xml/spring-quartz.xml");
        AbstractApplicationContext apc0 = new ClassPathXmlApplicationContext("xml/spring-quartz0.xml");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
