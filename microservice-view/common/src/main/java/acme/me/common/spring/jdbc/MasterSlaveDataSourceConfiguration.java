package acme.me.common.spring.jdbc;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@ConfigurationProperties("data.jdbc")
public class MasterSlaveDataSourceConfiguration extends DataSourceConfiguration {
    @Setter
    private SingleMysqlConfiguration master;

    @Setter
    private List<SingleMysqlConfiguration> slaves;

    @Override
    @Bean
    public DataSource dataSource() {
        DataSource masterDataSource = this.master.buildDataSource();
        return masterDataSource;
    }
}
