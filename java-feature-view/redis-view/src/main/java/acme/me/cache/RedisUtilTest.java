package acme.me.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import acme.me.cache.util.RedisCacheUtil;
import acme.me.cache.util.RedisClient;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "spring-redis.xml")
public class RedisUtilTest {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void performTest() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(500);

        long start = System.currentTimeMillis();
        Integer count = 1000000;
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
            newFixedThreadPool.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void performTest1() {
        RedisCacheUtil util = new RedisCacheUtil();
        util.setJedisPool(jedisPool);

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(500);

        long start = System.currentTimeMillis();
        Integer count = 1000000;
        for (int i = 0; i < count; i++) {
            final int index = i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    RedisCacheUtil.mapAdd("KEY_FOR_TEST", "" + index, "" + index);
                }
            });
        }

        try {
            newFixedThreadPool.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
