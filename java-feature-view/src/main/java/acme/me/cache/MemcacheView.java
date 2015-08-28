package acme.me.cache;

import java.net.InetSocketAddress;
import java.util.Date;

import net.spy.memcached.MemcachedClient;

public class MemcacheView {
    public static void main(String[] args) throws Exception {
        MemcachedClient c = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));

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

        c.shutdown();
    }
}
