package acme.me.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/12 11:35
 * @Description: microservice-view
 */
public class EncryptUtilTest {
    @Test
    public void test1() {
        String md5_1 = EncryptUtil.md5("abc");
        String md5_2 = EncryptUtil.md5(new StringBuffer("abc").toString());
        Assert.assertEquals(md5_1, md5_2);
    }
}
