package acme.me.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    private Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before executing:" + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("After executed" + method.getName());
        return result;
    }

    public static void main(String[] args) throws Exception{
        SubjectImpl proxyTarget = new SubjectImpl();
        JDKProxy jdkProxy = new JDKProxy(proxyTarget);

        Subject sub = (Subject) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { Subject.class }, jdkProxy);

        try{
            sub.doSomeThing();
            String name = sub.getName();
            System.out.println(name);
            sub.throwException();

            System.out.println("sub's class is:"+sub.getClass().toString());
            System.out.println("sub's interfaces is(are):");
            for(Class<?> iface:sub.getClass().getInterfaces()){
                System.out.println("----"+iface.toString());
            }
            System.out.println("sub's subperclass is(are):"+sub.getClass().getSuperclass());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
