package acme.me.sso;

import acme.me.common.action.UnionExceptionHandler;
import acme.me.common.config.Swagger2Configuration;
import acme.me.common.spring.redis.StandAloneRedisConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@Import({Swagger2Configuration.class, StandAloneRedisConfiguration.class, UnionExceptionHandler.class})
public class AcmeSSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmeSSOApplication.class, args);
    }

}
