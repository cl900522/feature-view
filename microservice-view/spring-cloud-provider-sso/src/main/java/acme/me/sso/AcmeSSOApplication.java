package acme.me.sso;

import acme.me.common.action.UnionExceptionHandler;
import acme.me.common.config.Swagger2Configuration;
import acme.me.common.spring.jdbc.StandAloneDataSourceConfiguration;
import acme.me.common.spring.redis.StandAloneRedisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("acme.me.sso.dao")
@Import({Swagger2Configuration.class, StandAloneRedisConfiguration.class, UnionExceptionHandler.class, StandAloneDataSourceConfiguration.class})
public class AcmeSSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmeSSOApplication.class, args);
    }

}
