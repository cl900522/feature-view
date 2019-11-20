package acme.me.serialize.json;

import acme.me.serialize.joo.Student;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.AntiCollisionHashMap;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJsonView {
    @Test
    public void test1() {
        Student user = new Student();
        user.setName("张三");
        user.setSex("male");
        user.setBirthDate(new Date());
        String o = JSON.toJSONString(user);


        Student student = JSON.parseObject(o, Student.class);
        Assert.assertTrue(student.getBirthDate().equals(user.getBirthDate()));

    }

    @Test
    public void test2() {
        Type type = TypeReference.LIST_STRING;
        List<String> o = JSON.parseObject("[1,2,6,23]", type);
        Assert.assertTrue(o.contains("1"));
        Assert.assertTrue(o.contains("2"));
        Assert.assertTrue(o.contains("6"));
        Assert.assertTrue(o.contains("23"));


        Type mapType = (new TypeReference<Map<String, String>>() {
        }).getType();

        // Map默认实例化为 AntiCollisionHashMap
        Map<String, String> map = JSON.parseObject("{\"key\":\"value\"}", mapType);
        Assert.assertTrue(map.getClass() == AntiCollisionHashMap.class);
        Assert.assertTrue(map.get("key").equals("value"));


        mapType = (new TypeReference<HashMap<String, String>>() {
        }).getType();
        map = JSON.parseObject("{\"key\":\"value\"}", mapType);
        Assert.assertTrue(map.getClass() == HashMap.class);
        Assert.assertTrue(map.get("key").equals("value"));

    }

    private class FstJsonObj {
        private String name;
    }

}
