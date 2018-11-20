package acme.me.cache.redis;

import org.junit.*;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JedisClientView {
    private static Jedis jedis;// 非切片额客户端连接
    private static JedisPool jedisPool;// 非切片连接池

    /**
     * 初始化非切片池
     */
    @BeforeClass
    public static void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "192.168.100.200", 6379);
        jedis = jedisPool.getResource();
    }

    public static void initCluster() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.83.128", 6379));
        nodes.add(new HostAndPort("192.168.83.128", 6380));
        nodes.add(new HostAndPort("192.168.83.128", 6381));
        nodes.add(new HostAndPort("192.168.83.128", 6382));
        nodes.add(new HostAndPort("192.168.83.128", 6383));
        nodes.add(new HostAndPort("192.168.83.128", 6384));
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        String name = cluster.get("name");
        System.out.println(name);
        cluster.set("age", "18");
        System.out.println(cluster.get("age"));
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hmapTest() {
        byte[] field = "field1".getBytes();
        byte[] key = "HMAP".getBytes();
        jedis.del(key);

        jedis.hset(key, field, "1".getBytes());
        byte[] hget = jedis.hget(key, field);
        Assert.assertEquals(new String(hget), "1");

        jedis.hsetnx(key, field, "1.1".getBytes()); // 1.1 will be set if HMAP has no field field
        hget = jedis.hget(key, field);
        Assert.assertEquals(new String(hget), "1");
        Assert.assertNotEquals(new String(hget), "1.1");

        jedis.hsetnx(key, "field2".getBytes(), "2".getBytes());
        jedis.hsetnx(key, "field3".getBytes(), "3".getBytes());
        jedis.hsetnx(key, "field4".getBytes(), "4".getBytes());
        jedis.hsetnx(key, "field5".getBytes(), "5".getBytes());
        jedis.hsetnx(key, "field6".getBytes(), "6".getBytes());

        Assert.assertEquals(jedis.hlen(key).intValue(), 6);

        jedis.hincrBy(key, "field7".getBytes(), 7);
        byte[] field7 = jedis.hget(key, "field7".getBytes());
        Assert.assertEquals(new String(field7), "7");

        List<byte[]> hvals = jedis.hvals(key);

        jedis.hdel(key, field);
        Assert.assertEquals(jedis.hlen(key).intValue(), 6);
        jedis.del(key);
    }

    @AfterClass
    public static void finishTest() {
        jedisPool.close();
    }

}
