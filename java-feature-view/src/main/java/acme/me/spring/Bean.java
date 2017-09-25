package acme.me.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Bean implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {
    private static Logger logger = Logger.getLogger(MyBeanPostProcessor.class);

    public void init() {
        logger.error("##init 初始化 Bean1");
    }

    public void anoDestroy() {
        logger.error("##destroy 销毁 Bean1");
    }

    @PostConstruct
    public void beforeInit() {
        logger.error("##PostConstract 初始化Bean信息");
    }

    @PreDestroy
    public void beforeDestory() {
        logger.error("##PreDestroy 销毁 Bean信息");
    }

    @Override
    public void destroy() throws Exception {
        logger.error("##Destory bean");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.error("##afterPropertiesSet");
    }

    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        logger.error("###Get bean factory");

    }

    @Override
    public void setBeanName(String arg0) {
        logger.error("###My beanname is:" + arg0);
    }
}
