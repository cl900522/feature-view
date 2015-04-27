package acme.me.designpattern.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

    public Object createProxy(Class<?> objectClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectClass);// 设置代理目标
        enhancer.setCallback(this);// 设置回调
        enhancer.setClassLoader(objectClass.getClassLoader());
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("########### Cglib 动态代理生效 ###########");
        System.out.println("obj info is:"+obj.getClass().toString());
        System.out.println("method info is:"+method.toString());
        System.out.println("proxy info is:"+proxy.toString());
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("########### function executed ########");
        return result;
    }

    public static void main(String[] args) throws Exception{
        Subject sub =(SubjectImpl) new CglibProxy().createProxy(SubjectImpl.class);
        try{
            sub.doSomeThing();
            System.out.println("sub's class is:"+sub.getClass().toString());
            System.out.println("sub's interfaces is(are):");
            for(Class<?> iface:sub.getClass().getInterfaces()){
                System.out.println("----"+iface.toString());
            }
            System.out.println("sub's subperclass is(are):"+sub.getClass().getSuperclass());
        }catch(Exception e){
            System.out.println("########################");
            e.printStackTrace();
        }
    }
}
