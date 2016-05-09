package acme.me.j2se;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class AnnotationView {
    public static void main(String[] args) throws ReflectiveOperationException {
        MyAnnotation myAnnotation = AnnotationP.class.getAnnotation(MyAnnotation.class);
        System.out.println(myAnnotation);
        for (Method method : myAnnotation.annotationType().getDeclaredMethods()) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            Object invoke = method.invoke(myAnnotation);
            System.out.println("invoke methd " + method.getName() + " result:" + invoke);
            if (invoke.getClass().isArray()) {
                Object[] temp = (Object[]) invoke;
                for (Object o : temp) {
                    System.out.println(o);
                }
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    private @interface MyAnnotation {
        String value() default "";
        int num() default 100;
        String[] address() default {};
    }

    @MyAnnotation(value = "AnnotationP", num = 12, address = { "1", "2" })
    private static class AnnotationP {
    }
}
