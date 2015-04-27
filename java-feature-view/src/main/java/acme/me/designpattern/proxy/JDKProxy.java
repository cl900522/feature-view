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
        System.out.println("Before executing");
        Object result = method.invoke(target, args);
        System.out.println("After executed");
        return result;
    }

    public static void main(String[] args) throws Exception{
        SubjectImpl ori = new SubjectImpl();
        JDKProxy jdkP = new JDKProxy(ori);

        Subject sub = (Subject) Proxy.newProxyInstance(SubjectImpl.class.getClassLoader(), SubjectImpl.class.getInterfaces(), jdkP);
        /**Subject sub = (SubjectImpl) Proxy.newProxyInstance(SubjectImpl.class.getClassLoader(), SubjectImpl.class.getInterfaces(), jdkP);*/
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
