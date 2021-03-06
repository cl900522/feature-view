package acme.me.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
/*该注解会把RestController修饰的类注册到注册中心去。*/
@EnableFeignClients
public class AcmeUserApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AcmeUserApplication.class, args);
    }

}
