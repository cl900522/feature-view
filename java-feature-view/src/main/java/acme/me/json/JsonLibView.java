package acme.me.json;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class JsonLibView {
    private static String object2JsonString(Object beanData) {
        return JSONObject.fromObject(beanData).toString();
    }

    private static String objectArray2JsonString(Object beanData) {
        return JSONArray.fromObject(beanData).toString();
    }

    private static String bean2JsonString(Object beanData) {
        return JSONSerializer.toJSON(beanData).toString();
    }

    private static String bean2xml(Object beanData) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(beanData)).toString();
    }

    /********************************************************/

    private static Object jsonString2Object(String jsonString, Class<?> beanClass) {
        JSONObject tempObject = JSONObject.fromObject(jsonString);
        return JSONObject.toBean(tempObject, beanClass);
    }

    private static Object jsonString2ObjectArray(String jsonString, Class<?> beanClass) {
        JSONArray tempObject = JSONArray.fromObject(jsonString);
        return JSONArray.toArray(tempObject, beanClass);
    }

    private static Object jsonString2Bean(String jsonString) {
        JSON tempObject = JSONSerializer.toJSON(jsonString);
        return JSONSerializer.toJava(tempObject);
    }

    public static void main(String[] args) {
        Student liming = new Student("liming");
        liming.setAddress("中国.四川");
        liming.setEmail("ming.li@gmail.com");
        liming.setId(001);
        Birthday birthday = new Birthday("2001-01-01");
        liming.setBirthday(birthday);
        Student[] classMates = new Student[] { liming, null, null };

        System.out.println("####################object to json##########################");
        System.out.println(object2JsonString(liming));
        System.out.println(objectArray2JsonString(liming));
        System.out.println(bean2JsonString(liming));
        System.out.println(objectArray2JsonString(WeekDay.STA));

        // 将数组通过JSONObject装换json字符出错
        // System.out.println(Object2JsonString(classMates));
        // 自动检测object转换为数组形式json
        System.out.println(objectArray2JsonString(classMates));
        System.out.println(bean2JsonString(classMates));

        System.out.println("####################json to object##########################");
        liming = (Student) jsonString2Object(object2JsonString(liming), Student.class);
        System.out.println(liming.toString());

        Object proxyedObject = jsonString2Bean(bean2JsonString(liming));
        System.out.println(proxyedObject.getClass().toString());

        classMates = (Student[]) jsonString2ObjectArray(objectArray2JsonString(classMates), Student.class);
        printStudentArray(classMates);
        System.out.println("####################object to xml##########################");
        System.out.println(bean2xml(classMates));
    }

    private static void printStudentArray(Student[] classMates) {
        for (Student i : classMates) {
            if (i == null) {
                System.out.print("null");
            } else {
                System.out.print(i.toString());
            }
            System.out.print("\n");
        }
    }
}
