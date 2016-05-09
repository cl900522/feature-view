package acme.me.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacsonView {
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private Student hoojo = null;

    @Before
    public void init() {
        hoojo = new Student("hoojo");
        hoojo.setAddress("china-Guangzhou");
        hoojo.setEmail("hoojo_@126.com");
        hoojo.setId(1);
        hoojo.setBirthday(new Birthday("2014-10-10"));

        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeMap2JSON() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", hoojo.getName());
            map.put("account", hoojo);
            hoojo = new Student("hoojo");
            hoojo.setAddress("china-Beijin");
            hoojo.setEmail("hoojo@qq.com");
            map.put("account2", hoojo);

            System.out.println("jsonGenerator");
            jsonGenerator.writeObject(map);
            System.out.println();

            System.out.println("objectMapper");
            objectMapper.writeValue(System.out, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeEntity2JSON() {
        try {
            System.out.println("jsonGenerator");
            // writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
            jsonGenerator.writeObject(hoojo);
            System.out.println();

            System.out.println("ObjectMapper");
            // writeValue具有和writeObject相同的功能
            objectMapper.writeValue(System.out, hoojo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeEnum2JSON() {
        try {
            WeekDay day = WeekDay.STA;

            System.out.println("jsonGenerator");
            // writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
            jsonGenerator.writeObject(day);
            System.out.println();

            System.out.println("ObjectMapper");
            // writeValue具有和writeObject相同的功能
            String json = objectMapper.writeValueAsString(day);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJson2Entity() {
        String json = "{\"address\":\"address\",\"name\":\"hoojo\",\"id\":1,\"email\":\"email\"}";
        try {
            Student acc = objectMapper.readValue(json, Student.class);
            System.out.println(acc.getName());
            System.out.println(acc);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJson2Enum() {
        String json = "1";
        try {
            WeekDay day = objectMapper.readValue(json, WeekDay.class);
            System.out.println(day.toString());
            Assert.assertEquals(day, WeekDay.SUN);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJson2List() {
        String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"}," + "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
        try {
            List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Set<String> set = map.keySet();
                for (Iterator<String> it = set.iterator(); it.hasNext();) {
                    String key = it.next();
                    System.out.println(key + ":" + map.get(key));
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJson2Array() {
        String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"}," + "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
        try {
            Student[] arr = objectMapper.readValue(json, Student[].class);
            System.out.println(arr.length);
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readJson2Map() {
        String json = "{\"success\":true,\"A\":{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"
            + "\"B\":{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}}";
        try {
            Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
            System.out.println(maps.size());
            Set<String> key = maps.keySet();
            Iterator<String> iter = key.iterator();
            while (iter.hasNext()) {
                String field = iter.next();
                System.out.println(field + ":" + maps.get(field));
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeObject2Xml() {
        // stax2-api-3.0.2.jar
        System.out.println("XmlMapper");
        XmlMapper xml = new XmlMapper();

        try {
            // javaBean转换成xml
            // xml.writeValue(System.out, bean);
            StringWriter sw = new StringWriter();
            xml.writeValue(sw, hoojo);
            System.out.println(sw.toString());
            // List转换成xml
            List<Student> list = new ArrayList<Student>();
            list.add(hoojo);
            list.add(hoojo);
            System.out.println(xml.writeValueAsString(list));

            // Map转换xml文档
            Map<String, Student> map = new HashMap<String, Student>();
            map.put("hoojo1", hoojo);
            map.put("hoojo2", hoojo);
            System.out.println(xml.writeValueAsString(map));
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            hoojo = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
