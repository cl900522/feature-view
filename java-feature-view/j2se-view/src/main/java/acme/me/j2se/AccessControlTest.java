package acme.me.j2se;

import org.junit.Test;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class AccessControlTest {
    @Test
    public void test1() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {

                System.setProperty("key1", "value1");
                return null;

            }
        });
    }
}
