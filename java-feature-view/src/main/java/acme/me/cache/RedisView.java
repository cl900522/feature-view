package acme.me.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisView {
    private Jedis jedis;// 非切片额客户端连接
    private JedisPool jedisPool;// 非切片连接池
    private ShardedJedis shardedJedis;// 切片额客户端连接
    private ShardedJedisPool shardedJedisPool;// 切片连接池

    public RedisView() {
        initialPool();
        initialShardedPool();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxWaitMillis(3000l);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "192.168.1.100", 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("192.168.1.100", 6379));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public static void main(String[] args) {
        RedisView view = new RedisView();

        int TEST_COUNT = 100;
        Jedis jedis = view.jedisPool.getResource();

        Thread[] threads = new Thread[TEST_COUNT];
        for (int i = 0; i < threads.length; i++) {
            RedisRun redisRun = new RedisRun();
            redisRun.jedis = jedis;
            threads[i] = new Thread(redisRun);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    public static class RedisRun implements Runnable {
        public JedisPool jedisPool;
        public Jedis jedis;

        @Override
        public void run() {
            User user = new User();
            user.birthDate = new Date();
            user.password = Math.random() + "";
            user.userName = Math.random() + "";

            jedis.setex(user.userName, 3600 * 24, user.toString());
            System.out.println("Execute well!");
        }
    }
}
