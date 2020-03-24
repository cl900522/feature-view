package acme.me.j2se8;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author: cdchenmingxuan
 * @date: 2019/9/5 15:35
 * @description: java-feature-view
 */
public class StreamTest {
    @Test
    public void test1() {
        Random random = new Random();
        IntStream intStream = random.ints().limit(100);
        intStream.filter((a) -> a > 752960102).forEach((r) -> System.out.println(r));
    }


    @Test
    public void test2() {
        List<String> strList = new ArrayList<>();
        strList.add("Java");
        strList.add("JavaScript");
        strList.add("C");
        strList.add("C++");
        strList.add("Python");
        strList.add("PHP");
        Stream<String> strStream = strList.stream();

        //过滤J开头的数据，在转换为增加H字符前缀的集合
        List<String> maped = strStream.filter((t) -> t.startsWith("J")).map((k) -> "H " + k).collect(Collectors.toList());
        Assert.assertTrue(maped.size() == 2);
        Assert.assertTrue(maped.contains("H Java"));
        Assert.assertTrue(maped.contains("H JavaScript"));

        strStream = strList.parallelStream();
        maped = strStream.filter((t) -> t.startsWith("P")).map((k) -> "H " + k).collect(Collectors.toList());
        Assert.assertTrue(maped.size() == 2);
        Assert.assertTrue(maped.contains("H Python"));
        Assert.assertTrue(maped.contains("H PHP"));
    }


    List<String[]> eggs = new ArrayList<>();

    @Before
    public void init() {
        // 第一箱鸡蛋
        eggs.add(new String[]{"鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1"});
        // 第二箱鸡蛋
        eggs.add(new String[]{"鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2"});
    }

    // 自增生成组编号
    static int group = 1;
    // 自增生成学生编号
    static int student = 1;

    /**
     * 把二箱鸡蛋分别加工成煎蛋，还是放在原来的两箱，分给2组学生
     */
    @Test
    public void map() {
        eggs.stream()
                .map(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("组" + group++ + ":" + Arrays.toString(x.toArray())));
        /*
        控制台打印：------------
        组1:[煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1]
        组2:[煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2]
         */
    }

    /**
     * 把二箱鸡蛋分别加工成煎蛋，然后放到一起【10个煎蛋】，分给10个学生
     */
    @Test
    public void flatMap() {
        eggs.stream()
                .flatMap(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("学生" + student++ + ":" + x));
        /*
        控制台打印：------------
        学生1:煎蛋_1
        学生2:煎蛋_1
        学生3:煎蛋_1
        学生4:煎蛋_1
        学生5:煎蛋_1
        学生6:煎蛋_2
        学生7:煎蛋_2
        学生8:煎蛋_2
        学生9:煎蛋_2
        学生10:煎蛋_2
         */
    }


    @Test
    public void test3() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"A", "Apple"});
        list.add(new String[]{"B", "Banana"});
        list.add(new String[]{"C", "Cat"});
        list.add(new String[]{"D", "Dog"});
        // list.add(new String[]{"D", "Dolphine"}); 加上这一行就回产生duplicate key的异常

        Collector<String[], ?, Map<String, String[]>> d = Collectors.toMap((a) -> a[0], (a) -> a);

        Map<String, String[]> collect = list.stream().collect(d);
        System.out.println(collect);
    }

    @Test
    public void test4() {
        String array[] = {"a", "b", "c"};
        String joinStr = Stream.of(array).collect(Collectors.joining(","));
        Assert.assertEquals(joinStr, "a,b,c");
    }

    @Test
    public void test5(){
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Ant");
        list.add("Banana");
        list.add("Cat");
        list.add("Dog");
        list.add("Dig");
        list.add("Dolphine");

        // list.parallelStream()...
        Map<String, List<String>> group = list.stream().collect(Collectors.groupingBy(a -> a.substring(0, 1)));
        System.out.println(JSON.toJSON(group));
    }
}
