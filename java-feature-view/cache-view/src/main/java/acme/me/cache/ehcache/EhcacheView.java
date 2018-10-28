package acme.me.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class EhcacheView {

    @Test
    public void ehcacheTest() {
        CacheManager cacheManager = CacheManager.create();
        // cacheManager = CacheManager.getInstance();
        // cacheManager = CacheManager.create("/ehcache.xml");
        // CacheManager.create("http://localhost:8080/test/ehcache.xml");
        // cacheManager = CacheManager.newInstance("/config/ehcache.xml");

        InputStream input = this.getClass().getResourceAsStream("/ehcache.xml");
        cacheManager = CacheManager.create(input);

        // 获取ehcache配置文件中的一个cache
        Cache sampleCache = cacheManager.getCache("sampleCache");
        BlockingCache cache = new BlockingCache(cacheManager.getEhcache("SimplePageCachingFilter"));

        Element element = new Element("key", "val");
        sampleCache.put(element);
        Element result = sampleCache.get("key");
        String value = (String) result.getObjectValue();
        Assert.assertEquals(value, "val");
        sampleCache.remove("key");
        sampleCache.removeAll();

        // 获取缓存管理器中的缓存配置名称
        for (String cacheName : cacheManager.getCacheNames()) {
            System.out.println(cacheName);
        }
        // 获取所有的缓存对象
        for (Object key : cache.getKeys()) {
            System.out.println(key);
        }

        // 得到缓存中的对象数
        cache.getSize();
        // 得到缓存对象占用内存的大小
        cache.getMemoryStoreSize();
        // 得到缓存读取的命中次数
        cache.getStatistics().getCacheHits();
        // 得到缓存读取的错失次数
        cache.getStatistics().getCacheMisses();
    }
}
