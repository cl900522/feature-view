package acme.me.j2se.concurrent;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 16:47
 * @description: java-feature-view
 */
public class ListTest {

    @Test
    /***
     * CopyOnWriteArrayList 逐个进行增长和移除，过程中不停的生成新数组，拷贝原数组到新数组。效率太低
     */
    public void test1() throws Exception {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();
        Field declaredField = CopyOnWriteArrayList.class.getDeclaredField("array");
        declaredField.setAccessible(true);
        list.add("a1");
        System.out.println(((Object[]) declaredField.get(list)).length);
        list.add("a2");
        System.out.println(((Object[]) declaredField.get(list)).length);
        list.add("a3");
        System.out.println(((Object[]) declaredField.get(list)).length);
        list.add("a4");
        System.out.println(((Object[]) declaredField.get(list)).length);
        list.add("a5");
        System.out.println(((Object[]) declaredField.get(list)).length);
    }
}
