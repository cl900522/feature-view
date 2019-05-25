package acme.anno;

import java.lang.annotation.*;

//@Retention(RetentionPolicy.SOURCE) //注解仅存在于源码中，在class字节码文件中不包含
//@Retention(RetentionPolicy.CLASS) // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface ExcelAnno {
}
