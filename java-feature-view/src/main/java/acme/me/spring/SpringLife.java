package acme.me.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLife {
    static public void main(String[] args){
        ApplicationContext apc = new ClassPathXmlApplicationContext("spring-beans.xml");
    }
}
