package acme.me.cache.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Created by caijun.yang on 2018/3/21
 */
public class RedisClientImpl implements RedisClient {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte keys[] = serializer.serialize(key);
                byte values[] = redisConnection.get(keys);
                if (values == null) {
                    return null;
                }
                String value = serializer.deserialize(values);
                //System.out.println("key:"+key+",value:"+value);
                return value;
            }
        });
    }

    public Boolean set(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte keys[] = serializer.serialize(key);
                byte values[] = serializer.serialize(value);
                redisConnection.set(keys, values);
                return true;
            }
        });
    }


    public Long delete(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> redisSerializer = getRedisSerializer();
                byte keys[] = redisSerializer.serialize(key);
                return redisConnection.del(keys);
            }
        });
    }


    public Boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.exists(keys);
            }
        });
    }


    public Long incr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.incr(keys);
            }
        });
    }

    public Long decr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.decr(keys);
            }
        });
    }

    public Long incrby(final String key, final Long offset) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.incrBy(keys, offset);
            }
        });
    }

    public Long decrby(final String key, final Long offset) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.decrBy(keys, offset);
            }
        });
    }


    public Long lpush(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                byte values[] = getRedisSerializer().serialize(value);
                return redisConnection.lPush(keys, values);
            }
        });
    }


    public Long rpush(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                byte values[] = getRedisSerializer().serialize(value);
                return redisConnection.rPush(keys, values);
            }
        });
    }

    public String lpop(final String key) {
        byte[] bytes = redisTemplate.execute(new RedisCallback<byte[]>() {
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.lPop(keys);
            }
        });
        return getRedisSerializer().deserialize(bytes);
    }

    public String rpop(final String key) {
        byte[] bytes = redisTemplate.execute(new RedisCallback<byte[]>() {
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.rPop(keys);
            }
        });
        return getRedisSerializer().deserialize(bytes);
    }

    public Long llen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.lLen(keys);
            }
        });
    }


    public Long sadd(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.sAdd(keys, getRedisSerializer().serialize(value));
            }
        });
    }

    public String sPop(final String key) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte keys[] = getRedisSerializer().serialize(key);
                return redisConnection.sPop(keys);
            }
        });
        return getRedisSerializer().deserialize(result);
    }


    public Long scard(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keys = getRedisSerializer().serialize(key);
                return redisConnection.sCard(keys);
            }
        });
    }


    public Boolean hset(final String key, final String field, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hSet(
                        getRedisSerializer().serialize(key),
                        getRedisSerializer().serialize(field),
                        getRedisSerializer().serialize(value)
                );
            }
        });
    }


    public String hget(final String key, final String field) {
        byte[] bytes = redisTemplate.execute(new RedisCallback<byte[]>() {

            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hGet(getRedisSerializer().serialize(key),
                        getRedisSerializer().serialize(field));
            }
        });
        String deserialize = getRedisSerializer().deserialize(bytes);
        return deserialize;
    }


    public Long hdel(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hDel(getRedisSerializer().serialize(key), getRedisSerializer().serialize(field));
            }
        });
    }

    public Boolean hexists(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hExists(
                        getRedisSerializer().serialize(key),
                        getRedisSerializer().serialize(field)
                );
            }
        });
    }

    /**
     * 获取主库的redis模板
     *
     * @return
     */
    protected RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    /**
     * 获取主库的字符串序列化对象
     *
     * @return
     */
    protected RedisSerializer<String> getRedisSerializer() {
        RedisSerializer<String> redisSerializer = getRedisTemplate().getStringSerializer();
        return redisSerializer;
    }


}
