package acme.me.schedule;

import org.junit.Test;
import org.quartz.JobDetail;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringQuartzView {
    @Test
    public void test1() {
        AbstractApplicationContext apc = new ClassPathXmlApplicationContext("xml/spring-quartz.xml");
        AbstractApplicationContext apc0 = new ClassPathXmlApplicationContext("xml/spring-quartz0.xml");
        
        JobDetail bean = (JobDetail) apc.getBean("jobDetail2");
        bean.getJobDataMap().put("", "");
        bean.getJobDataMap().put("", "");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
