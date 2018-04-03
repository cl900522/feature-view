package acme.me.cache.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheUtil {
    private static final Logger logger = Logger.getLogger(RedisCacheUtil.class);

    private static JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        RedisCacheUtil.jedisPool = jedisPool;
    }

    /******************** List operations *************************/

    public static int listSize(String key) {
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.llen(key.getBytes());
        jedis.close();
        return length > Integer.MAX_VALUE ? Integer.MAX_VALUE : Integer.valueOf(length.toString());
    }

    public static void listAdd(String key, Serializable object) {
        if (object == null) {
            return;
        }
        Jedis jedis = jedisPool.getResource();
        jedis.lpush(key.getBytes(), SerializingUtil.serialize(object));
        jedis.close();
    }

    public static <T> List<T> listGet(String key) {
        Jedis jedis = jedisPool.getResource();
        Long totalCount = jedis.llen(key.getBytes());
        List<byte[]> bytes = jedis.lrange(key.getBytes(), 0, totalCount);
        List<T> objList = new ArrayList<T>();
        for (byte[] by : bytes) {
            T node = (T) SerializingUtil.deserialize(by);
            objList.add(node);
        }
        jedis.close();
        return objList;
    }

    public static <T> List<T> listGet(String key, int start, int count) {
        Jedis jedis = jedisPool.getResource();
        Long totalCount = jedis.llen(key.getBytes());
        if (count >= totalCount) {
            count = Integer.valueOf(totalCount.toString());
        }
        List<byte[]> bytes = jedis.lrange(key.getBytes(), start, start + count - 1);
        List<T> objList = new ArrayList<T>(count);
        for (byte[] by : bytes) {
            T node = (T) SerializingUtil.deserialize(by);
            objList.add(node);
        }
        jedis.close();
        return objList;
    }

    public static <T> List<T> listPop(String key, int size) {
        Jedis jedis = jedisPool.getResource();
        List<T> objList = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            byte[] bytes = jedis.lpop(key.getBytes());
            T node = (T) SerializingUtil.deserialize(bytes);
            if (node == null) {
                break;
            }
            objList.add(node);
        }
        jedis.close();
        return objList;
    }

    /******************** set operations *************************/
    public static void setAdd(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        jedis.sadd(key, values);
        jedis.close();
    }

    public static boolean setContains(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Boolean contains = jedis.sismember(key, value);
        jedis.close();
        return contains;
    }

    public static Set<String> setContent(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.smembers(key);
        jedis.close();
        return keys;
    }

    public static long setSize(String key) {
        Jedis jedis = jedisPool.getResource();
        Long size = jedis.scard(key);
        jedis.close();
        return size == null ? 0 : size;
    }

    /******************** Map operations *************************/
    public static long mapSize(String key) {
        Jedis jedis = jedisPool.getResource();
        Long size = jedis.hlen(key);
        jedis.close();
        return size == null ? 0 : size;
    }

    public static void mapAdd(String key, String field, Serializable object) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = SerializingUtil.serialize(object);
        jedis.hset(key.getBytes(), field.getBytes(), bytes);
        jedis.close();
    }

    public static void mapAddByte(String key, String field, byte[] bytes) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key.getBytes(), field.getBytes(), bytes);
        jedis.close();
    }

    public static boolean mapContains(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        boolean exist = jedis.hexists(key.getBytes(), field.getBytes());
        jedis.close();
        return exist;
    }

    public static void mapDelete(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        jedis.hdel(key.getBytes(), field.getBytes());
        jedis.close();
    }

    public static <T> T mapGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
        T result = (T) SerializingUtil.deserialize(bytes);
        jedis.close();
        return result;
    }

    public static byte[] mapGetByte(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.hget(key.getBytes(), field.getBytes());
        jedis.close();
        return bytes;
    }

    public static Set<String> mapKeys(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.hkeys(key);
        jedis.close();
        return keys;
    }

    public static void mapIncrease(String key, String field, long count) {
        Jedis jedis = jedisPool.getResource();
        jedis.hincrBy(key.getBytes(), field.getBytes(), count);
        jedis.close();
    }

    /***********************
     * common object operation
     ***************************/
    public static void put(String key, Serializable object) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = SerializingUtil.serialize(object);
        jedis.setex(key.getBytes(), 3600, bytes);
        jedis.close();
    }

    public static <T> T get(String key) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get(key.getBytes());
        T result = (T) SerializingUtil.deserialize(bytes);
        jedis.close();
        return result;
    }

    public static List<byte[]> mapPop(String key, byte[][] fields) {
        Jedis jedis = jedisPool.getResource();
        List<byte[]> bytes = jedis.hmget(key.getBytes(), fields);
        jedis.hdel(key.getBytes(), fields);
        jedis.close();
        return bytes;
    }

    public static void delete(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key.getBytes());
        jedis.close();
    }

    public static void expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key, seconds);
        jedis.close();
    }
}
