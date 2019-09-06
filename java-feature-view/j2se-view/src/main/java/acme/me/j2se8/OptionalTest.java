package acme.me.j2se8;

import java.util.Optional;

/**
 * @author: cdchenmingxuan
 * @date: 2019/9/6 11:58
 * @description: java-feature-view
 */
public class OptionalTest {
    public void test1() {
        String value = "hello Optional ";
        Optional.of(value); //如果value==null 这里会抛 NullPointerException
        Optional<String> op = Optional.ofNullable(value); //允许接受value==null;
        String v1 = op.get(); // 如果value==null 则会导致 NullPointerException
        String v2 = op.isPresent() ? op.get() : "defaultValue"; //isPresent()等同于 if(value!=null);
        String v3 = op.orElseGet(() -> "defaultValue"); //如果value为null。返回defaultValue

    }
}
