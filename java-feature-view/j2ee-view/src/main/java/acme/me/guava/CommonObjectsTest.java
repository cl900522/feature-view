package acme.me.guava;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CommonObjectsTest {
    @Test
    /**
     * @see java.util.Objects.equals
     */
    public void test1() {
        boolean equal = Objects.equal("Good", "Good");
        Assert.assertTrue(equal);
        equal = Objects.equal(null, null);
        Assert.assertTrue(equal);
    }

    @Test
    /**
     * @see java.util.Objects.hash(Object...)
     */
    public void test2() {
        int hash = Objects.hashCode("Json", 1, 12.0f);
    }

    @Test
    public void test3() {
        // Returns "ClassName{x=1}"
        MoreObjects.toStringHelper(this).add("x", 1).toString();

        // Returns "MyObject{x=1}"
        MoreObjects.toStringHelper("MyObject").add("x", 1).toString();
    }

    @Test
    // ComparisonChain做了一个很懒的对比，他会一直进行对比，直到发现一个非0的结果，后面的输入都会被忽略
    public void test4() {
        int result = ComparisonChain.start().compare("Json", "Kil").result();
        Assert.assertTrue(result < 0);
        result = ComparisonChain.start().compare("Json", "Kil").compare(100, 2).result();
        Assert.assertTrue(result < 0);

        result = ComparisonChain.start().compare(null, 1, Ordering.natural().nullsLast()).result();
        Assert.assertTrue(result > 0);

        result = ComparisonChain.start().compare(null, 1, Ordering.natural().nullsFirst()).result();
        Assert.assertTrue(result < 0);
    }

    @Test
    public void test5() {
        Ordering<Integer> from = Ordering.from((t1, t2) -> t1 - t2);
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        // 同byLengthOrdering的结果
        Ordering<Comparable> comparableOrdering = Ordering.natural().nullsFirst();
        Ordering<String> ordering = comparableOrdering.onResultOf(new Function<String, Integer>() {
            public Integer apply(String foo) {
                return foo.length();
            }
        });

        //内部做了装饰，进行反转排序
        Ordering<String> reverseLengthOrder = byLengthOrdering.reverse();

        String min = byLengthOrdering.min("Ab2112", "Ddd", "Ckdjf");
        Assert.assertEquals("Ddd", min);
        String max = byLengthOrdering.max("Ab1112", "Ddd", "Ckdjf");
        Assert.assertEquals("Ab1112", max);

        ArrayList<String> strings = Lists.newArrayList("Ab1112", "Ddd", "Ckdjf", "3351234512", "88");
        Boolean inOrder = byLengthOrdering.isOrdered(strings);
        Assert.assertFalse(inOrder);

        //取最大两个元素
        byLengthOrdering.greatestOf(strings, 2);
        //取最大两小元素
        byLengthOrdering.leastOf(strings, 2);
        //获取排序的副本
        List<String> sortedStrings = byLengthOrdering.sortedCopy(strings);
        Assert.assertTrue(byLengthOrdering.isOrdered(sortedStrings));
    }

}
