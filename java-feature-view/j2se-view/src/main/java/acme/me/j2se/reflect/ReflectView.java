package acme.me.j2se.reflect;

import acme.domain.Employee;
import acme.domain.Person;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

public class ReflectView {
    private static Logger slf4jLogger = LoggerFactory.getLogger(ReflectView.class);

    @Test
    public void test1() throws Exception {
        Class<Employee> employeeClass = Employee.class;
        Employee employee = employeeClass.newInstance();
        employee.setJoinDate(new Date());
        slf4jLogger.info("employee.joinDate:{}", employee.getJoinDate());
    }

    @Test
    public void test2() {
        Class<?> employeeClass1 = Employee.class;
        Employee emp = new Employee();
        emp.setMonthSalary(3000);
        Class<?> employeeClass2 = emp.getClass();
        Class<?> employeeClass3 = null;
        try {
            String employeeClassName = employeeClass1.getName();
            slf4jLogger.info("Employee.getName is:{}", employeeClassName);
            slf4jLogger.info("Employee.getSimpleName is:{}", employeeClass1.getSimpleName());
            slf4jLogger.info("Employee.getCanonicalName is:{}", employeeClass1.getCanonicalName());
            slf4jLogger.info("Employee.getTypeName is:{}", employeeClass1.getTypeName());
            employeeClass3 = Class.forName(employeeClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (employeeClass1 == employeeClass2 && employeeClass2 == employeeClass3) {
            System.out.println("The same!");
        }
    }

    @Test
    public void test3() {
        Person emp = new Person();
        Class<?> employeeClass = Person.class;
        Field[] fields = employeeClass.getDeclaredFields();
        Field[] publicFields = employeeClass.getFields();
        for (Field field : employeeClass.getDeclaredFields()) {
            System.out.println("\'" + field.getName() + "\' field related methods is(are):");
            for (Method method : employeeClass.getDeclaredMethods()) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    System.out.println(method.getName());
                }
            }
            if (field.getName().equals("name")) {
                try {
                    /*here will throws exception because the field is private*/
                    field.set(emp, "Elice");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void test4() {
        Employee emp = new Employee();
        Class<?> employeeClass = Employee.class;
        Method[] methods = employeeClass.getMethods();
        for (Method method : employeeClass.getDeclaredMethods()) {
            if (method.getName().equals("getYearSalary")) {
                try {
                    /*Core of dynamic Proxy*/
                    Object result = method.invoke(emp, null);
                    System.out.println("Employee's year salary is:" + (Double) result);
                } catch (Exception e) {
                    e.printStackTrace();
                    slf4jLogger.error("Reflect to execute method error!");
                }
            }
        }
    }

    private class R implements Runnable {
        @Override
        public void run() {
        }
    }

    @Test
    public void test5() {
        Runnable  r = new Runnable(){
            @Override
            public void run() {
            }
        };
        class PhoneNumber{
            String num;
        }

        Class<? extends Runnable> rClazz = r.getClass();
        checkClazzType("r Type", rClazz);
        checkClazzType("R", R.class);
        checkClazzType("PhoneNumber", PhoneNumber.class);

        getEnclosingInfo("r Typer ", r.getClass());
        getEnclosingInfo("PhoneNumber", PhoneNumber.class);
        getEnclosingInfo("R", R.class);
    }

    private void getEnclosingInfo(String name, Class<?> clazz) {
        Class<?> enclosingClass = clazz.getEnclosingClass();
        slf4jLogger.info("{} enclosing class is {}", name, enclosingClass);
        Method enclosingMethod = clazz.getEnclosingMethod();
        slf4jLogger.info("{} enclosing method is {}", name, enclosingMethod);
    }

    @Test
    public void test6() {
        Class<?>[] interfaces = ArrayList.class.getInterfaces();
        slf4jLogger.info(interfaces.toString());
        Class<? super ArrayList> superclass = ArrayList.class.getSuperclass();
        slf4jLogger.info(superclass.toString());
        AnnotatedType annotatedSuperclass = ArrayList.class.getAnnotatedSuperclass();
        slf4jLogger.info(annotatedSuperclass.toString());
        AnnotatedType[] annotatedInterfaces = ArrayList.class.getAnnotatedInterfaces();
        slf4jLogger.info(annotatedInterfaces.toString());
    }

    @Test
    public void test7() {
        Annotation[] annotations = Person.class.getAnnotations();
        slf4jLogger.info(annotations.toString());
        Annotation[] declaredAnnotations = Person.class.getDeclaredAnnotations();
        slf4jLogger.info(declaredAnnotations.toString());
        Deprecated deprecated = Person.class.getDeclaredAnnotation(Deprecated.class);
        slf4jLogger.info(deprecated.toString());
    }

    private void checkClazzType(String name, Class<?> rClazz) {
        boolean anonymousClass = rClazz.isAnonymousClass();
        if (anonymousClass) {
            slf4jLogger.info("{} is an anonymous class", name);
        } else {
            slf4jLogger.info("{} is not an anonymous class", name);
        }
        boolean memberClass = rClazz.isMemberClass();
        if (memberClass) {
            slf4jLogger.info("{} is an member class", name);
        } else {
            slf4jLogger.info("{} is not an member class", name);
        }
        boolean synthetic = rClazz.isSynthetic();
        if (synthetic) {
            slf4jLogger.info("{} is an synthetic class", name);
        } else {
            slf4jLogger.info("{} is not an synthetic class", name);
        }
        boolean localClazz = rClazz.isLocalClass();
        if (localClazz) {
            slf4jLogger.info("{} is an local class", name);
        } else {
            slf4jLogger.info("{} is not an local class", name);
        }
    }
}
