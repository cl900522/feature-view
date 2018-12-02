package acme.me.common.spring.jdbc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties("data.jdbc")
public class StandAloneDataSourceConfiguration extends DataSourceConfiguration {
    @Getter
    @Setter
    private SingleMysqlConfiguration standalone;

    @Override
    @Bean
    public DataSource dataSource() {
        return standalone.buildDataSource();
    }
}
