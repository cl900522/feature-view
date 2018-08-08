package com.acme.dubbo.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acme.dubbo.inter.UserService;

public class ConsumerStarter {
    public static void main(String[] args) {
        try {
            // 初始化Spring
            ApplicationContext ctx = new ClassPathXmlApplicationContext("com/acme/dubbo/consumer/spring-dubbo.xml");
            UserService bean = ctx.getBean(UserService.class);
            String message = bean.getMessage("Jone");
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
