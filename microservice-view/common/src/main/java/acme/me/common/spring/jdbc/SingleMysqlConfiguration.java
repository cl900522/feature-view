package acme.me.common.spring.jdbc;

import acme.me.common.spring.redis.RedisPoolConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.sql.DataSource;

@Setter
@Getter
public class SingleMysqlConfiguration {
    private String driverClass = "com.mysql.jdbc.Driver";

    private String jdbcUrl;

    private String username = "root";

    private String password = "root";

    private Integer maxActive = 50;

    private Integer maxIdle = 20;

    private Long maxWait = 3000L;

    public DataSource buildDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }
}
