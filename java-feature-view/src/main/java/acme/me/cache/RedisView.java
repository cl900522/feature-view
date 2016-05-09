package acme.me.cache;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private static Lock lock = new ReentrantLock();  

    public Jedis getJedis() {
        lock.lock();
        return jedis;
    }

    public void release() {
        lock.unlock();
    }

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
        config.setMaxIdle(1000);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);

        this.jedisPool = new JedisPool(config, "192.168.1.100", 6379);
        this.jedis = this.jedisPool.getResource();
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
        Jedis jedis = view.jedisPool.getResource();

        User user = new User();
        user.birthDate = new Date();
        user.password = Math.random() + "";
        user.userName = Math.random() + "";

        /**************single thread***************/
        int i = 0;
        while (i < 50) {
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(user);
                jedis.setex(("join 00" + i).getBytes(), 3600 * 24, os.toByteArray());
                System.out.println("Finish storing " + i + " object!");
            } catch (Exception e) {
                System.err.println(e.getClass().toString() + ": " +e.getMessage());
            }
            i++;
        }

        /**************multi thread***************/
        int TEST_COUNT = 10000;
        jedis = view.jedisPool.getResource();

        Thread[] threads = new Thread[TEST_COUNT];
        for (int j = 0; j < threads.length; j++) {
            RedisRun redisRun = new RedisRun();
            redisRun.jedisView = view;
            threads[j] = new Thread(redisRun);
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
    }

    public static class RedisRun implements Runnable {
        public RedisView jedisView;

        @Override
        public void run() {
            Jedis jedis = jedisView.getJedis();

            User user = new User();
            user.birthDate = new Date();
            user.password = Math.random() + "";
            user.userName = Math.random() + "";

            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(user);
                jedis.setex(("user 00" + user.getUserName()).getBytes(), 3600 * 24, os.toByteArray());
            } catch (Exception e) {
                System.err.println(e.getClass().toString() + ": " +e.getMessage());
            }

            System.out.println("Execute well!");

            jedisView.release();
        }
    }
}
