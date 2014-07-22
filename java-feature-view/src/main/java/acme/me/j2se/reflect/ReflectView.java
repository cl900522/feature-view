package acme.me.j2se.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectView {
    private static Logger slf4jLogger = LoggerFactory.getLogger(ReflectView.class);

    public static void main(String[] args) {
        Class<?> employeeClass1 = Employee.class;
        Employee emp = new Employee();
        emp.setMonthSalary(3000);
        Class<?> employeeClass2 = emp.getClass();
        Class<?> employeeClass3 = null;
        try {
            String employeeClassName = employeeClass1.getName();
            System.out.println(employeeClassName);
            employeeClass3 = Class.forName(employeeClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (employeeClass1 == employeeClass2 && employeeClass2 == employeeClass3) {
            System.out.println("The same!");
        }

        Field[] a = employeeClass2.getDeclaredFields();
        for (Field field : employeeClass2.getDeclaredFields()) {
            System.out.println("\'" + field.getName() + "\' field related methods is(are):");
            for (Method method : employeeClass2.getDeclaredMethods()) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    System.out.println(method.getName());
                }
            }
            if(field.getName().equals("name")){
                try{
                    /*here will throws exception because the field is private*/
                    field.set(emp, "Elice");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        for (Method method : employeeClass2.getDeclaredMethods()) {
            if (method.getName().equals("getYearSalary")) {
                try {
                    /*Core of dynamic Proxy*/
                    Object result = method.invoke(emp, null);
                    System.out.println("Employee's year salary is:"+(Double)result);
                } catch (Exception e) {
                    e.printStackTrace();
                    slf4jLogger.error("Reflect to execute method error!");
                }
            }
        }
    }
}
