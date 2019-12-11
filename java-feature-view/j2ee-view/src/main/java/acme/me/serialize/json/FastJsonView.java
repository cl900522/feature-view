package acme.me.serialize.json;

import acme.me.serialize.joo.Student;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
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


        student = JSON.parseObject(o, Student.class, Feature.AllowArbitraryCommas);
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

    /** 解析的feature
     * AutoCloseSource 即允许自动关闭流
     * AllowComment 允许存在备注
     * AllowUnQuotedFieldNames 决定parser是否将允许使用非双引号属性名字
     * AllowSingleQuotes 决定parser是否允许单引号来包住属性名称和字符串值
     * InternFieldNames 该特性决定JSON对象属性名称是否可以被String#intern 规范化表示。
     * AllowISO8601DateFormat 这个设置为true则遇到字符串符合ISO8601格式的日期时，会直接转换成日期类
     * AllowArbitraryCommas 允许多重逗号,如果设为true,则遇到多个逗号会直接跳过;
     * UseBigDecimal 这个设置为true则用BigDecimal类来装载数字，否则用的是double；
     * IgnoreNotMatch 忽略不匹配
     * SortFeidFastMatch 如果你用fastjson序列化的文本，输出的结果是按照fieldName排序输出的，parser时也能利用这个顺序进行优化读取。这种情况下，parser能够获得非常好的性能
     * DisableASM
     * DisableCircularReferenceDetect  禁用循环引用检测
     * InitStringFieldAsEmpty 对于没有值得字符串属性设置为空串
     * SupportArrayToBean 支持数组to对象
     * OrderedField 属性保持原来的顺序
     * DisableSpecialKeyDetect  禁用特殊字符检查
     * UseObjectArray 使用对象数组
     */
    @Test
    public void test3(){
        Student student = JSON.parseObject("{'name':'jon'}", Student.class, Feature.AllowSingleQuotes);
        Assert.assertTrue(student.getName().equals("jon"));

        try {
            student = JSON.parseObject("{'name':jon'}", Student.class);
        }catch (JSONException e) {
            Assert.assertTrue(e.getClass() == JSONException.class);
        }

        student = JSON.parseObject("{\"name\":\"jon\"//Great}", Student.class, Feature.AllowComment);
        Assert.assertTrue(student.getName().equals("jon"));

        try {
            student = JSON.parseObject("{\"name\":\"jon\"", Student.class);
        }catch (JSONException e) {
            Assert.assertTrue(e.getClass() == JSONException.class);
        }

    }

    /**  序列化的feature
     *     QuoteFieldNames,
     *     UseSingleQuotes,
     *     WriteMapNullValue,
     *     WriteEnumUsingToString,
     *     UseISO8601DateFormat,
     *     WriteNullListAsEmpty,
     *     WriteNullStringAsEmpty,
     *     WriteNullNumberAsZero,
     *     WriteNullBooleanAsFalse,
     *     SkipTransientField,
     *     SortField,
     *     WriteTabAsSpecial,
     *     PrettyFormat,
     *     WriteClassName,
     *     DisableCircularReferenceDetect,
     *     WriteSlashAsSpecial,
     *     BrowserCompatible,
     *     WriteDateUseDateFormat,
     *     NotWriteRootClassName,
     *     DisableCheckSpecialChar;
     */
    public void test4(){
        Student student = new Student();
        String json = JSON.toJSONString(student, SerializerFeature.BrowserCompatible);


    }

    private class FstJsonObj {
        private String name;
    }

}
