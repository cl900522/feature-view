package acme.me.j2se8;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.*;

/**
 * @author: cdchenmingxuan
 * @date: 2019/9/5 15:35
 * @description: java-feature-view
 */
public class LambdaTest {
    @Test
    public void test1() {
        Function<Integer, Integer> fun = (a) -> a + 1;
        Integer funRes = fun.apply(12);
        Assert.assertTrue(funRes == 13);


        BiFunction<Integer, Integer, Integer> plus = (a, b) -> a + b;
        Integer plusRes = plus.apply(1, 2);
        Assert.assertTrue(plusRes == 3);

        /*创建一个先相加，再加1的函数*/
        BiFunction<Integer, Integer, Integer> plusThenFun = plus.andThen(fun);
        plusRes = plusThenFun.apply(2, 3);
        Assert.assertTrue(plusRes == 6);

        Supplier<String> supplier = () -> "Age";
        String supplierRes = supplier.get();
        Assert.assertEquals(supplierRes, "Age");

        Consumer<String> consumer = (a) -> System.out.println("Hello " + a);
        consumer.accept("Nil");

        BiConsumer<String, Integer> consumer2 = (a, b) -> System.out.println("Hello " + a + ", we have not see each other for " + b + " years!");
        consumer2.accept("Json", 1);

        Predicate<Integer> is = (a) -> a % 4 == 0;
        boolean test = is.test(2012);
        Assert.assertTrue(test);

        Predicate<Integer> and = is.and((b) -> b % 3 == 0);
        Assert.assertTrue(and.test(12));
        Assert.assertFalse(and.test(9));

    }


    @Test
    /**
     * 方法引用通过方法的名字来指向一个方法。
     * 方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
     * 方法引用使用一对冒号 :: 。
     */
    public void test2() {
        Converter<String, Integer> t = (a) -> Integer.parseInt(a);
        Function<String, Integer> convertFun = t::convert;
        Integer apply = convertFun.apply("1222");
        Assert.assertTrue(apply == 1222);


        Function<String, Double> t1 = DoubleCon::staConvert;
        Function<String, Double> t2 = new DoubleCon()::convert;
        Assert.assertEquals(t1.apply("827"), t2.apply("827"));
    }


    @FunctionalInterface
    public interface Converter<T1, T2> {
        T2 convert(T1 i);
    }

    public static class DoubleCon implements Converter<String, Double> {

        public static Double staConvert(String in) {
            return Double.valueOf(in);
        }

        @Override
        public Double convert(String i) {
            return staConvert(i);
        }
    }


    @Test
    /**
     * 函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
     * 函数式接口可以被隐式转换为 lambda 表达式。
     */
    public void test3(){

    }
}
