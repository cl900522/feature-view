package acme.me.j2se.classloader;

import acme.domain.User;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author: cdchenmingxuan
 * @date: 2019/6/11 11:53
 * @description: java-feature-view
 */
public class JavassistView {
    @Test
    public void test1() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        pool.makePackage(JavassistView.class.getClassLoader(), "org.tmp");
        CtClass ctClass = pool.makeClass("org.tmp.Bean", pool.get("java.util.Date"));

        CtMethod joke = CtNewMethod.make("public void joke(){int i=0;}", ctClass);
        ctClass.addMethod(joke);
        joke.setBody("System.out.println(12);");
        Class aClass = ctClass.toClass();

        Object o = aClass.newInstance();
        System.out.println(o.getClass());
        Method[] methods = o.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals("joke")) {
                method.invoke(o);
            }
        }
    }


    @Test
    public void test2() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("acme.domain.User");

        CtMethod getAge = ctClass.getDeclaredMethod("getAge");
        getAge.setBody("return Long.valueOf(\"100\");");

        ctClass.toClass();

        User u = new User();
        u.setAge(12L);
        Long age = u.getAge();

        System.out.println(age);
    }
}
