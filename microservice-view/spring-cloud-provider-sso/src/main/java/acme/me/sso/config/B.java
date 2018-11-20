package acme.me.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource
public class B {

    private String app;

    private String version;


    @Value("${app.name}")
    public void setApp(String app) {
        this.app = app;
    }

    @Value("${app.version}")
    public void setVersion(String version) {
        this.version = version;
    }
}
