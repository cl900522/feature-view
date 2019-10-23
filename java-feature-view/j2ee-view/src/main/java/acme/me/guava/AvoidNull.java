package acme.me.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

public class AvoidNull {
    @Test
    public void test1() {
        Optional<Integer> possible = Optional.of(5);
        Assert.assertTrue(possible.isPresent());
        Assert.assertEquals(possible.get(), new Integer(5));
        Assert.assertEquals(possible.orNull(), new Integer(5));
        Assert.assertEquals(possible.or(new Integer(1)), new Integer(5));
    }

    @Test(expected = NullPointerException.class)
    public void test2() {
        Optional<Integer> possible = Optional.of(null);
    }

    @Test(expected = IllegalStateException.class)
    public void test3() {
        Optional<Integer> possible = Optional.fromNullable(null);
        Assert.assertFalse(possible.isPresent());

        Assert.assertEquals(possible.orNull(), null);
        Assert.assertEquals(possible.or(0), new Integer(0));
        possible.get(); //throw NullPointerException.class
    }

    @Test
    public void test4() {
        Optional<Integer> possible = Optional.absent();
        Assert.assertFalse(possible.isPresent());

        Assert.assertEquals(possible.orNull(), null);
        Assert.assertEquals(possible.or(0), new Integer(0));
    }

    @Test
    public void test5() {
        Integer val1 = MoreObjects.firstNonNull(null, new Integer(1));
        Assert.assertEquals(val1, new Integer(1));

        Integer val2 = MoreObjects.firstNonNull(new Integer(0), new Integer(1));
        Assert.assertEquals(val2, new Integer(0));
    }

    @Test
    public void test6 (){
        String aNull = Strings.emptyToNull("");
        Assert.assertEquals(aNull,null);

        boolean nullOrEmpty = Strings.isNullOrEmpty("");
        Assert.assertTrue(nullOrEmpty);

        String empty = Strings.nullToEmpty("");
        Assert.assertEquals(empty, "");
    }
}
