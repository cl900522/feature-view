package acme.me.cache;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * 原来项目中有用到Redis用作缓存服务，刚开始时只用一台Redis就能够满足服务，随着项目的慢慢进行，发现一台满足不了现有的项
 * 目需求，因为Redis操作都是原子性这样的特性，造成有时同时读写缓存造成查询效率的下降。但是由于我们现在用的还是2.X版本，
 * 还是没有集群功能的（Redis作者在3.0版本中已经加入了集群功能）, 因此只能使用2.x版本中自带的一个叫做ShardedJedis的来实现
 * 分布式缓存。
 * * ShardedJedis是通过一致性哈希来实现分布式缓存的，通过一定的策略把不同的key分配到不同的redis server上，达到横向扩展的
 * 目的。那么ShardedJedis内部是怎么实现的呢，文章会慢慢讲解。
 * <p>
 * ShardedJedis的使用方法除了配置时有点区别，其他和Jedis基本类似，有一点要注意的是 ShardedJedis不支持多命令操作，像mge
 * t、mset、brpop等可以在redis命令后一次性操作多个key的命令，具体包括哪些，大家可以看Jedis下的 MultiKeyCommands 这个类，
 * 这里面就包含了所有的多命令操作。很贴心的是，Redis作者已经把这些命令从ShardedJedis过滤掉了，使用时也调用不了这些方法，
 * 大家知道下就行了。
 */
public class SharJedisClientView {
    private ShardedJedis shardedJedis;// 切片额客户端连接
    private ShardedJedisPool shardedJedisPool;// 切片连接池

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

}
