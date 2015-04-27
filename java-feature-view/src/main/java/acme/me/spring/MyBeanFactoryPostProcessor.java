package acme.me.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static Logger logger = Logger.getLogger(MyBeanFactoryPostProcessor.class);
    
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        logger.error("##MyBeanFactoryPostProcessor is excuting!");
    }

}
