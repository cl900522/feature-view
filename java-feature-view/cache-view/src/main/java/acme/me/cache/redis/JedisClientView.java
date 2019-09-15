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

        jedisPool = new JedisPool(config, "192.168.100.100", 6379);
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
    public void test1() {
        jedis.del("key1");
        jedis.del("key2");

        String set = jedis.set("key1", "value1");
        Assert.assertEquals(set, "OK");

        Long setnx1 = jedis.setnx("key1", "value1");
        Assert.assertEquals(setnx1, Long.valueOf(0));

        Long setnx2 = jedis.setnx("key2", "value2");
        Assert.assertEquals(setnx2, Long.valueOf(1));
    }

    @Test
    public void test2() throws Exception {
        jedis.del("key1");

        jedis.set("key1", "value1");
        Long ops = jedis.expire("key1", 1);
        Assert.assertTrue(1L == ops);
        Boolean exist = jedis.exists("key1");
        Assert.assertTrue(exist);

        Thread.sleep(1100L);
        exist = jedis.exists("key1");
        Assert.assertFalse(exist);


        jedis.set("key1", "value1");
        jedis.expireAt("key1", System.currentTimeMillis() + 1000L);
        Assert.assertTrue(1L == ops);
        exist = jedis.exists("key1");
        Assert.assertTrue(exist);

        Thread.sleep(1100L);
        exist = jedis.exists("key1");
        Assert.assertFalse(exist);
    }

    @Test
    public void test3() {
        jedis.del("set1");
        jedis.del("set2");

        Long sadd = jedis.sadd("set1", "v1", "v2", "v3", "v100");
        Assert.assertTrue(4L == sadd);
        List<String> set1 = jedis.srandmember("set1", 4);
        Assert.assertTrue(set1.contains("v1"));
        Assert.assertTrue(set1.contains("v2"));
        Assert.assertTrue(set1.contains("v3"));
        Assert.assertTrue(set1.contains("v100"));

        jedis.srem("set1", "v100");
        set1 = jedis.srandmember("set1", 4);
        Assert.assertTrue(set1.contains("v1"));
        Assert.assertTrue(set1.contains("v2"));
        Assert.assertTrue(set1.contains("v3"));
        Assert.assertTrue(!set1.contains("v100"));

        jedis.sadd("set2", "v1", "v2", "v99", "v100");

        /**
         * sunion返回并集
         * sunionstore
         */
        jedis.sunionstore("sort3", "sort1", "sort2");
        Set<String> sunion = jedis.sunion("set1", "set2");
        Assert.assertTrue(sunion.contains("v1"));
        Assert.assertTrue(sunion.contains("v2"));
        Assert.assertTrue(sunion.contains("v3"));
        Assert.assertTrue(sunion.contains("v99"));
        Assert.assertTrue(sunion.contains("v100"));
        Assert.assertTrue(sunion.size() == 5);


        /*
        diff只能计算第一个key与后面不包含的
        sdiffstore 将结果存储到sort3
         */
        jedis.sdiffstore("sort3", "set1", "sort2");

        Set<String> sdiff = jedis.sdiff("set1", "set2");
        Assert.assertTrue(sdiff.contains("v3"));
        Assert.assertTrue(sdiff.size() == 1);
    }

    @Test
    public void test4() {
        jedis.del("sort1");
        jedis.del("sort2");

        Long sadd = jedis.sadd("sort1", "67.0", "2.3", "12", "-1");
        Assert.assertTrue(sadd == 4);

        List<String> sort1 = jedis.sort("sort1");
        Assert.assertTrue(sort1.get(0).equals("-1"));
        Assert.assertTrue(sort1.get(3).equals("67.0"));

        /**
         * 将set,List,SortedSet排序后保存到sort2(为list类型)
         */
        jedis.sort("sort1", "sort2");
        String destKeyType = jedis.type("sort2");
        Assert.assertTrue(destKeyType.equals("list"));
    }

    @Test
    public void test5() {
        jedis.del("sortSet1");
        jedis.zadd("sortSet1", 47.0, "Java47");
        jedis.zadd("sortSet1", 0.0, "Java0");
        jedis.zadd("sortSet1", 68.0, "Java68");
        jedis.zadd("sortSet1", 100.0, "Java100");
        jedis.zadd("sortSet1", 78.0, "Java78");
        jedis.zadd("sortSet1", 22.0, "Java22");
        jedis.zadd("sortSet1", 40.0, "Java40");

        //jedis.zrange("sortSet1",2,4);
        //jedis.zrangeWithScores("sortSet1",2,4);
        Set<String> sortSet1 = jedis.zrangeByScore("sortSet1", 22, 99);
        //Set<Tuple> sortSet1 = jedis.zrangeByScoreWithScores("sortSet1", 22, 99);
        Assert.assertFalse(sortSet1.contains("Java100"));
        Assert.assertFalse(sortSet1.contains("Java0"));
        Assert.assertTrue(sortSet1.contains("Java78"));

        //Java78的分数增加23，为101
        jedis.zincrby("sortSet1", 23, "Java78");
        sortSet1 = jedis.zrangeByScore("sortSet1", 22, 99);
        Assert.assertFalse(sortSet1.contains("Java100"));
        Assert.assertFalse(sortSet1.contains("Java0"));
        Assert.assertFalse(sortSet1.contains("Java78"));

        jedis.zrem("sortSet1", "Java78");
        sortSet1 = jedis.zrange("sortSet1", 0, 10);
        Assert.assertFalse(sortSet1.contains("Java78"));

        jedis.zremrangeByRank("sortSet1", 0, 2);
        sortSet1 = jedis.zrange("sortSet1", 0, 10);
        Assert.assertFalse(sortSet1.contains("Java0"));
        Assert.assertFalse(sortSet1.contains("Java22"));

        jedis.zremrangeByScore("sortSet1", 0, 50);
        sortSet1 = jedis.zrange("sortSet1", 0, 10);
        Assert.assertFalse(sortSet1.contains("Java40"));
        Assert.assertFalse(sortSet1.contains("Java47"));
    }

    @Test
    public void test6() {
        String key = "listKey1";
        jedis.del("listKey1");

        jedis.lpush(key, "s-1", "s1", "s2 ", "s8");
        /* lpush 如果指定的列表是空，则新建一个,然后插入，而lpushx如果指定的列表是空，则不插入 */
        jedis.lpushx(key, "s9", "s3");

        List<String> range = jedis.lrange(key, 0, 2);
        Assert.assertTrue(range.contains("s9"));
        Assert.assertTrue(range.contains("s8"));
        Assert.assertTrue(range.size() == 3);


        jedis.lset(key, 0, "s10");
        range = jedis.lrange(key, 0, 2);
        Assert.assertTrue(range.contains("s10"));
        Assert.assertTrue(range.contains("s8"));
        Assert.assertTrue(range.size() == 3);


        jedis.linsert(key, BinaryClient.LIST_POSITION.AFTER, "s9", "s4");
        String ltrim = jedis.ltrim(key, 0, 3);
        Assert.assertTrue(ltrim.equals("OK"));

        range = jedis.lrange(key, 0, 10);
        Assert.assertTrue(range.contains("s10"));
        Assert.assertTrue(range.contains("s8"));
        Assert.assertFalse(range.contains("s1"));
        Assert.assertTrue(range.size() == 4);

        jedis.sort(key, new SortingParams(), "sortKey2");
        List<String> sortKey2 = jedis.lrange("sortKey2", 0, 10);
    }

    @Test
    public void test7() {
        String bitMap1 = "bitMap1";
        String bitMap2 = "bitMap2";

        jedis.del(bitMap1);
        jedis.del(bitMap2);


        jedis.setbit(bitMap1, 16, "0");
        jedis.setbit(bitMap1, 20, true);
        jedis.setbit(bitMap1, 97, "1");
        jedis.setbit(bitMap1, 42, "1");
        jedis.setbit(bitMap1, 83, false);

        Boolean getbit = jedis.getbit(bitMap1, 20);
        Assert.assertTrue(getbit);
        getbit = jedis.getbit(bitMap1, 16);
        Assert.assertFalse(getbit);
        getbit = jedis.getbit(bitMap1, 42);
        Assert.assertTrue(getbit);
        Long bitcount = jedis.bitcount(bitMap1);
        Assert.assertTrue(bitcount == 3);


        jedis.setbit(bitMap2, 4, true);
        jedis.setbit(bitMap2, 27, "0");
        jedis.setbit(bitMap2, 20, "1");
        jedis.setbit(bitMap2, 53, "1");

        Long pos = jedis.bitpos(bitMap2, true, new BitPosParams(0, 20));
        Assert.assertTrue(pos == 4);

        /*
        BITOP 的复杂度为 O(N) ，当处理大型矩阵(matrix)或者进行大数据量的统计时，最好将任务指派到附属节点(slave)进行，避免阻塞主节点。
         */
        jedis.bitop(BitOP.AND, "bitMapAnd", bitMap1, bitMap2);
        Long andCount = jedis.bitcount("bitMapAnd");
        Assert.assertTrue(andCount == 1);

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
