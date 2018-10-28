package acme.me.spring;

import java.beans.PropertyDescriptor;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    private static Logger logger = Logger.getLogger(MyInstantiationAwareBeanPostProcessor.class);
    private static Integer step = 0;

    @Override
    public Object postProcessBeforeInstantiation(Class<?> arg0, String arg1) throws BeansException {
        logger.error("##BeforeInstantiation: [" + arg0.getName() + "," + arg1 + "]");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object arg0, String arg1) throws BeansException {
        logger.error("##AfterInstantiation: [" + arg0.getClass().getName() + "," + arg1 + "]");
        return false;
    }

    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        logger.error("##BeforeInitialization");
        return arg0;
    }

    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        logger.error("##AfterInitialization");
        return arg0;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues arg0, PropertyDescriptor[] arg1, Object arg2, String arg3) throws BeansException {
        logger.error("##ProcessPropertyValues");
        return null;
    }

}
