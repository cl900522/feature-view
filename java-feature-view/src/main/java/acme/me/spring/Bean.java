package acme.me.spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class Bean {

    public void init(){
        System.out.println("##init 初始化 Bean1");
    }

    @PostConstruct
    public void beforeInit(){
        System.out.println("##PostConstract 初始化Bean信息");
    }

    @PreDestroy
    public void beforeDestory(){
        System.out.println("##PreDestroy 销毁 Bean信息");
    }
}
