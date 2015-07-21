package acme.me.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLifeCycle {
    static public void main(String[] args){
        ApplicationContext apc = new ClassPathXmlApplicationContext("spring-beans.xml");

        Sleepable sleeper = (Sleepable)apc.getBean("humanProxy");
        sleeper.sleep();
    }
}
