package acme.me.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

<<<<<<< HEAD
import org.apache.log4j.Logger;
=======
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
import org.springframework.stereotype.Component;

@Component
public class Bean {
<<<<<<< HEAD
    private static Logger logger = Logger.getLogger(MyBeanPostProcessor.class);

    public void init(){
        logger.error("##init 初始化 Bean1");
=======

    public void init(){
        System.out.println("##init 初始化 Bean1");
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
    }

    @PostConstruct
    public void beforeInit(){
<<<<<<< HEAD
        logger.error("##PostConstract 初始化Bean信息");
=======
        System.out.println("##PostConstract 初始化Bean信息");
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
    }

    @PreDestroy
    public void beforeDestory(){
<<<<<<< HEAD
        logger.error("##PreDestroy 销毁 Bean信息");
=======
        System.out.println("##PreDestroy 销毁 Bean信息");
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
    }
}
