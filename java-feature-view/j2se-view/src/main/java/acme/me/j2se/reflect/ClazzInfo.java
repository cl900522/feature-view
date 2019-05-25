package acme.me.j2se.reflect;

import org.junit.Test;

import java.util.logging.Logger;

public class ClazzInfo {
    private static Logger logger = Logger.getLogger(ClazzInfo.class.getName());

    @Test
    public void test1() throws Exception {
        Integer[] nums = new Integer[]{2, 3};
        if (nums.getClass().getComponentType() != null) {
            logger.info("nums is an array, It's element type is:" + nums.getClass().getComponentType().getName());
        }
        Integer i = 12;
        if (i.getClass().getComponentType() == null) {
            logger.info("i is and object class:" + i.getClass().toString());
        }
    }
}
