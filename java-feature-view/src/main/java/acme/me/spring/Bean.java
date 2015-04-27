package acme.me.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Bean {
    private static Logger logger = Logger.getLogger(MyBeanPostProcessor.class);

    public void init(){
        logger.error("##init 初始化 Bean1");
    }

    @PostConstruct
    public void beforeInit(){
        logger.error("##PostConstract 初始化Bean信息");
    }

    @PreDestroy
    public void beforeDestory(){
        logger.error("##PreDestroy 销毁 Bean信息");
    }
}
