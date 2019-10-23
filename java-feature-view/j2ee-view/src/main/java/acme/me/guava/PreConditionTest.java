package acme.me.guava;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class PreConditionTest {
    @Test(expected = NullPointerException.class)
    public void test1() {
        Preconditions.checkNotNull(new Integer(1), "Object is null, please recheck");
        Preconditions.checkNotNull(null, "Object is null, please recheck");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test2() {
        int i = 1, j = 1;
        // 用于检测参数的入参条件，如果不满足，则抛出IllegalArgumentException
        Preconditions.checkArgument(i < j, "Expected i < j, but %s >= %s", i, j);
    }

    @Test(expected = IllegalStateException.class)
    public void test3() {
        int i = 1, j = 1;
        // 用于检测参数的入参条件，如果不满足，则抛出IllegalStateException
        Preconditions.checkState(i < j, "");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test4() {
        List<Integer> li = Lists.newArrayList(1, 2, 3, 4);

        // 0 < index < size
        Preconditions.checkElementIndex(1,  li.size(), "out of bound");
        Preconditions.checkElementIndex(4,  li.size(), "out of bound");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test5() {
        List<Integer> li = Lists.newArrayList(1, 2, 3, 4);

        // 0 < index <= size
        Preconditions.checkPositionIndex(4,  li.size(), "out of bound");
        Preconditions.checkPositionIndex(9,  li.size(), "out of bound");

    }
}
