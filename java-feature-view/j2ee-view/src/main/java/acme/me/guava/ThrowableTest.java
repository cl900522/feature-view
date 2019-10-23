package acme.me.guava;

import com.google.common.base.Throwables;
import org.junit.Test;

import javax.validation.constraints.Null;
import java.util.List;

public class ThrowableTest {
    @Test(expected = RuntimeException.class)
    public void test1() {
        try {
            throw new NumberFormatException();
        } catch (Throwable t) {

            Throwables.throwIfInstanceOf(t, NullPointerException.class);
            throw Throwables.propagate(t);
        }
    }

    @Test(expected = NumberFormatException.class)
    public void test2() {
        try {
            throw new NumberFormatException();
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, NumberFormatException.class);
            throw Throwables.propagate(t);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test3() {
        try {
            throw new NumberFormatException();
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t);
        }
    }

    @Test(expected = RuntimeException.class)
    public void test4() {
        try {
            throw new NumberFormatException();
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, NullPointerException.class);
        }
    }

    @Test(expected = NumberFormatException.class)
    public void test5() {
        try {
            throw new NumberFormatException();
        } catch (Throwable t) {
            Throwables.propagateIfPossible(t, NumberFormatException.class);
        }
    }

    @Test(expected = Error.class)
    public void test6() {
        try {
            throw new Error("Fail");
        } catch (Throwable t) {
            System.out.println("Error");
            Throwables.propagateIfPossible(t, NumberFormatException.class);
        }
    }

    @Test(expected = Error.class)
    public void test7() {
        try {
            int t = 1 / 0;
        } catch (Throwable t) {
            Throwables.getRootCause(t);
            List<Throwable> causalChain = Throwables.getCausalChain(t);
            String stackTraceAsString = Throwables.getStackTraceAsString(t);
        }
    }

}
