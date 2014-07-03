package acme.me.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{

    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("##After bean ##"+arg0.toString()+"## initial");
        return arg0;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("##Before bean ##"+arg0.toString()+"## initial");
        return arg0;
    }

}
