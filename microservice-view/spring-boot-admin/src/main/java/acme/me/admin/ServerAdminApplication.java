package acme.me.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/16 15:28
 * @Description: microservice-view
 */
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ServerAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerAdminApplication.class, args);
    }
}
