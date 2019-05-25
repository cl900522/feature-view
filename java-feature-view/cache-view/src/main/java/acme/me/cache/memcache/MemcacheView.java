package acme.me.cache.memcache;

import acme.domain.User;
import net.spy.memcached.MemcachedClient;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemcacheView {
    public static void main(String[] args) throws Exception {
        MemcachedClient c = new MemcachedClient(new InetSocketAddress("192.168.10.101", 11211));

        // 存取一个简单的Integer
        // Store a value (async) for one hour
        c.set("someKey", 3600, new Integer(4));
        // Retrieve a value (synchronously).
        Object myObject = c.get("someKey");
        Integer result = (Integer) myObject;
        System.out.println(result);

        // 存取一个序列化的对象
        System.out.println("存之前的时间：" + System.currentTimeMillis());
        User user = new User();
        user.userName = "ZhangSan";
        user.password = "alongpasswordhere";
        user.birthDate = new Date();
        System.out.println(user.userName + " " + user.password + " " + user.birthDate.getTime());

        c.set("user1", 3600, user);
        System.out.println("取之前的时间：" + System.currentTimeMillis());
        User storedUser = (User) (c.get("user1"));
        System.out.println(storedUser.userName + " " + storedUser.password + " " + storedUser.birthDate.getTime());
        System.out.println("取之后的时间：" + System.currentTimeMillis());

        Long s = System.currentTimeMillis();
        List<CacheObject> list = new ArrayList<CacheObject>();
        for (int i = 0; i < 100000; i++) {
            CacheObject a = new CacheObject();
            a.openid = Math.random()+"";
            list.add(a);
        }

        c.delete("CACHE_QUEUE");
        c.add("CACHE_QUEUE", 3600, list);

        list = (List<CacheObject>) c.get("CACHE_QUEUE");
        System.out.println(list.size());
        for (CacheObject co : list) {
            System.out.println(co.toString());
        }
        System.out.println(System.currentTimeMillis() - s);

        c.shutdown();
    }

    public static class CacheObject implements Serializable {
        public String openid;

        public String toString() {
            return openid;
        }
    }
}
