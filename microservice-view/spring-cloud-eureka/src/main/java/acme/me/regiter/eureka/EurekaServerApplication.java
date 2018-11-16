package acme.me.regiter.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The @SpringBootApplication annotation is equivalent to using @Configuration,
 * @EnableAutoConfiguration and @ComponentScan with their default attributes
 */
@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
