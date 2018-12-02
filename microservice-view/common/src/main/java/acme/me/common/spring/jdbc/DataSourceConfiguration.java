package acme.me.common.spring.jdbc;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

public abstract class DataSourceConfiguration {

    @Bean
    public abstract DataSource dataSource();
}
