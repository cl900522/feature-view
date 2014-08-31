package acme.me.designpattern;

import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Test;

import acme.me.designpattern.brige.*;
import acme.me.designpattern.component.*;
import acme.me.designpattern.proxy.*;
import acme.me.designpattern.factory.AbstractCakeFactory;
import acme.me.designpattern.factory.Cake;
import acme.me.designpattern.factory.CakeEnum;
import acme.me.designpattern.factory.MilkyCake;
import acme.me.designpattern.factory.MilkyCakeFactory;
import acme.me.designpattern.factory.SimpleCakeFactory;
import acme.me.designpattern.factory.StrawberyCake;
import acme.me.designpattern.factory.StrawberyCakeFactory;
import acme.me.designpattern.singleton.SingletonFactoryKit;


public class DesignPatternTest {
    /**
     * 抽象工厂设计模式
     */
    @Test
    public void testAbstructFactory() {
        AbstractCakeFactory factory = new StrawberyCakeFactory();
        Cake mycake = factory.createCake();
        Assert.assertEquals(StrawberyCake.class, mycake.getClass());
        Assert.assertEquals(CakeEnum.STRAWBERY.getName(), mycake.getName());

        factory = new MilkyCakeFactory();
        mycake = factory.createCake();
        Assert.assertEquals(MilkyCake.class, mycake.getClass());
        Assert.assertEquals(CakeEnum.MILKY.getName(), mycake.getName());
    }

    /**
     * 普通工厂设计模式
     */
    @Test
    public void testSimpleFactory() {
        SimpleCakeFactory factory = new SimpleCakeFactory();
        Cake mycake = factory.createStrawberyCake();
        Assert.assertEquals(StrawberyCake.class, mycake.getClass());
        Assert.assertEquals(CakeEnum.STRAWBERY.getName(), mycake.getName());

        mycake = factory.createMilkyCake();
        Assert.assertEquals(MilkyCake.class, mycake.getClass());
        Assert.assertEquals(CakeEnum.MILKY.getName(), mycake.getName());
    }

    /**
     * 单例模式
     */
    @Test
    public void testSingleton() {
        AbstractCakeFactory factory1 = SingletonFactoryKit.instance();
        AbstractCakeFactory factory2 = SingletonFactoryKit.instance();
        Assert.assertEquals(factory1, factory2);
    }

    /**
     * 桥接模式
     */
    @Test
    public void testBrige() {
        Window window = new ApplicationWindow();
        window.setLocation(new Coords(0, 0));
        window.setSize(new Size(100, 100));
        window.drawArea();
        window.refeash();
    }

    /**
     * 组件模式
     */
    @Test
    public void testComponent() {
        Equipment floppyDisk = new FloppyDisk();
        floppyDisk.setName("KingStone FloppyDisk");
        floppyDisk.setCurrency(new Currency(150, "$"));

        Equipment hardDisk = new HardDisk();
        hardDisk.setName("WestData HardDisk");
        hardDisk.setCurrency(new Currency(500, "$"));

        Equipment mainBoard = new MainBoard();
        mainBoard.setName("技嘉主板");
        mainBoard.setCurrency(new Currency(750, "$"));

        mainBoard.add(floppyDisk);
        mainBoard.add(hardDisk);

        Assert.assertTrue(mainBoard.getTotalCurrency().getCount() > 650 && mainBoard.getTotalCurrency().getCount() <= 1400);
    }

    /**
     * 代理模式
     * */
    @Test
    public void testProxy() {
        Subject subject = new SubjectImpl();
        JDKProxy proxy = new JDKProxy(subject);
        Subject proxiedSubject = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), proxy);
        /**TODO-001 solve this bug
        proxiedSubject.doSomeThing();
        */
    }
}
