package acme.me.common.spring.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "data.redis.cluster")
public class ClusterRedisConfiguration extends RedisPoolConfiguration {
    @Getter
    @Setter
    private List<RedisNode> nodes;

    @Bean
    @Override
    public JedisConnectionFactory redisConnectionFactory() {

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        for (RedisNode node : nodes) {
            redisClusterConfiguration.addClusterNode(node);
        }

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
        return jedisConnectionFactory;
    }
}
