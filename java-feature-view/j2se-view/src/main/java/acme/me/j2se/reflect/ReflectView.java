package acme.me.j2se.reflect;

import acme.me.pojo.Employee;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        Employee emp = new Employee();
        Class<?> employeeClass = Employee.class;
        Field[] fields = employeeClass.getDeclaredFields();
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
}
