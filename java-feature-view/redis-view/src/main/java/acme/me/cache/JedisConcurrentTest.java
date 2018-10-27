package acme.me.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import acme.me.cache.util.RedisClient;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class JedisConcurrentTest {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private JedisPool jedisPool;

    private static final int count = 10000;

    ExecutorService newFixedThreadPool = null;

    @Before
    public void before() {
        newFixedThreadPool = Executors.newFixedThreadPool(10);
    }

    @Test
    public void springJedisTemplateTest() {
        long start = System.currentTimeMillis();
        Integer count = 10000;
        for (int i = 0; i < count; i++) {
            final int index = i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    redisClient.hset("KEY_FOR_TEST", "" + index, "" + index);
                }
            });
        }

        try {
            newFixedThreadPool.shutdown();
            newFixedThreadPool.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        redisClient.delete("KEY_FOR_TEST");
        System.out.println(String.format("All cost %d ms", end - start));
    }

    @Test
    public void jedisPoolTest() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            final int index = i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    jedisPool.getResource().hset("KEY_FOR_TEST", "" + index, "" + index);
                }
            });
        }

        try {
            newFixedThreadPool.shutdown();
            newFixedThreadPool.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
