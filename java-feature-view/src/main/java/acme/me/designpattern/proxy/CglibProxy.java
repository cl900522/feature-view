package acme.me.designpattern.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

    public Object createProxy(Class<?> objectClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Subject.class);// 设置代理目标
        enhancer.setCallback(this);// 设置回调
        enhancer.setClassLoader(objectClass.getClassLoader());
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Cglib 代理生效");
        System.out.println(obj.getClass().toString());
        System.out.println(method.toString());
        System.out.println(proxy.toString());
        Object result = proxy.invokeSuper(obj, args);
        return result;
    }


    public static void main(String[] args) throws Exception{
        Subject sub =(Subject) new CglibProxy().createProxy(SubjectImpl.class);
        try{
            sub.doSomeThing();
        }catch(Exception e){
            System.out.println("########################");
            e.printStackTrace();
        }
    }
}
