package acme.me.common.spring.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

public abstract class RedisPoolConfiguration {

    public JedisClientConfiguration getJedisPoolConfig() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder1 = JedisClientConfiguration.builder();

        return builder1.build();
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        StringRedisSerializer serializer = new StringRedisSerializer(StandardCharsets.UTF_8);

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setEnableDefaultSerializer(true);

        return redisTemplate;
    }

    public abstract JedisConnectionFactory redisConnectionFactory();
}
