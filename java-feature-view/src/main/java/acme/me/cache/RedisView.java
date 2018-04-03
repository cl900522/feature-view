package acme.me.cache;

import java.util.ArrayList;
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

        this.jedisPool = new JedisPool(config, "192.168.2.202", 6380);
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
        shards.add(new JedisShardInfo("192.168.2.202", 6380));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public static void main(String[] args) {
        RedisView view = new RedisView();
        Jedis jedis = view.jedisPool.getResource();

        putdata(jedis);

        jedis.expire("a", 1);
        
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String hget = jedis.hget("a","key12");
        System.out.println(hget);
        System.out.println(jedis.hlen("a"));

        putdata(jedis);
        hget = jedis.hget("a","key12");
        System.out.println(hget);
    }

    private static void putdata(Jedis jedis) {
        for (int i = 0; i < 1000; i++) {
            jedis.hset("a", "key" + i, "" + i);
        }
        System.out.println("finish put");
    }

}
