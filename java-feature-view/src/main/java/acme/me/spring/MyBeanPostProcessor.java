package acme.me.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{
    private static Logger logger = Logger.getLogger(MyBeanPostProcessor.class);

    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        logger.error("##After bean ##"+arg0.toString()+"## initial");
        return arg0;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        logger.error("##Before bean ##"+arg0.toString()+"## initial");
        return arg0;
    }

}
