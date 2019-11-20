package acme.me.serialize.json;

import acme.me.serialize.joo.Student;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonView {
    @Test
    public void test1() {
        Gson gson = new Gson();
        int i = gson.fromJson("100", int.class); //100
        double d = gson.fromJson("\"99.99\"", double.class);  //99.99
        boolean b = gson.fromJson("true", boolean.class);     // true
        String str = gson.fromJson("String", String.class);   // String

        Assert.assertTrue(i == 100);
        Assert.assertTrue(d == 99.99);
        Assert.assertTrue(b);
    }


    @Test
    public void test2() {
        String json2 = "[\"apple\", \"pear\", \"banana\"]";
        Gson gson2 = new Gson();
        // 传入的java类型是String[].class
        String[] fruits = gson2.fromJson(json2, String[].class);
    }


    @Test
    public void test3() {
        Gson gson = new Gson();
        Student user = new Student();
        user.setName("张三");
        user.setSex("male");
        user.setBirthDate(new Date());
        String jsonObject = gson.toJson(user);

        Student student = gson.fromJson(jsonObject, Student.class);
        Assert.assertEquals(student.getName(), user.getName());
        Assert.assertEquals(student.getSex(), user.getSex());
    }

    /**
     * 范型测试
     */
    @Test
    public void test4() {
        Gson gson = new Gson();
        List<String> list = Lists.newArrayList("1", "2", "22", "99");
        String s = gson.toJson(list);

        List<Integer> r = gson.fromJson(s, new TypeToken<List<Integer>>() {
        }.getType());
        Assert.assertTrue(r.contains(1));
        Assert.assertTrue(r.contains(2));
        Assert.assertTrue(r.contains(22));
        Assert.assertTrue(r.contains(99));
    }

    @Test
    /**
     * 没有Expose注解的字段全部都要被排除序列化
     * 排除@Expose值为false的属性
     *
     */
    public void test5() {
        GsonObj g = new GsonObj();
        g.setA(22);
        g.setB("Sakura");
        g.setC(99);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        String s = gson.toJson(g);
        Assert.assertTrue(s.contains("age"));
        Assert.assertTrue(s.contains("name"));
        Assert.assertTrue(s.contains("score"));

        String replace = s.replace("name", "fullName");
        GsonObj o2 = gson.fromJson(replace, GsonObj.class);
        Assert.assertTrue(o2.getA() == null);
        Assert.assertTrue(o2.getB().equals("Sakura"));
        Assert.assertTrue(o2.getC().equals(99));

    }

    @Test
    public void test6() {
        // Map默认实例化为 LinkedTreeMap
        Gson gson = new GsonBuilder().create();
        Map<String, String> r = gson.fromJson("{\"key\":\"value\"}", new TypeToken<Map<String, String>>() {
        }.getType());
        Assert.assertTrue(r.getClass().equals(LinkedTreeMap.class));


        r = gson.fromJson("{\"key\":\"value\"}", new TypeToken<HashMap<String, String>>() {
        }.getType());
        Assert.assertTrue(r.getClass().equals(HashMap.class));
    }

    @Getter
    @Setter
    private class GsonObj {

        @Expose(serialize = true, deserialize = false)
        @SerializedName("age")
        private Integer a;

        @SerializedName(value = "name", alternate = {"fullName"})  // 可以有多个备选值
        @Expose
        private String b;

        @Expose
        @SerializedName("score")
        private Integer c;

    }
}
