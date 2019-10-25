package acme.me.spring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFeatureTest {
    @Test
    public void test01LifeCycle() {
        AbstractApplicationContext apc = new ClassPathXmlApplicationContext("spring-lifecycle.xml");
        apc.registerShutdownHook();
    }

    @Test
    public void test02Aop() {
        ApplicationContext apc = new ClassPathXmlApplicationContext("spring-aop.xml");

        Sleepable sleeper = (Sleepable) apc.getBean("humanProxy");
        sleeper.sleep();
    }

    @Test
    public void test03Inherite() {
        ApplicationContext apc = new ClassPathXmlApplicationContext("spring-view.xml");

        Human child = (Human) apc.getBean("childOnly");
        child.listRelatives();

        Human child1 = (Human) apc.getBean("child");
        Human child2 = (Human) apc.getBean("child");
        Assert.assertNotEquals(child1,child2);

        SpelUtil bean = apc.getBean(SpelUtil.class);
        Object o = bean.resolveExpression("#{child.relatives[0]}");
        String resolve = (String) o;
        System.out.println(resolve);
    }
}
