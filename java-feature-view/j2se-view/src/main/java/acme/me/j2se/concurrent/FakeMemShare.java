package acme.me.j2se.concurrent;

import org.junit.Test;
import sun.misc.Contended;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/19 10:46
 * @description: java-feature-view
 */
public class FakeMemShare {
    private int DIM_1 = 1000;
    private int DIM_2 = 1000;

    @Test
    public void test1() {
        Long start = System.currentTimeMillis();
        Integer[][] arrayDim = new Integer[DIM_1][DIM_2];
        for (int i = 0; i < DIM_1; i++) {
            for (int j = 0; j < DIM_2; j++) {
                arrayDim[i][j] = i * 10 + j;
            }
        }
        System.out.println("test1:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test2() {
        Long start = System.currentTimeMillis();
        Integer[][] arrayDim = new Integer[DIM_1][DIM_2];
        for (int i = 0; i < DIM_2; i++) {
            for (int j = 0; j < DIM_1; j++) {
                arrayDim[i][j] = i * 10 + j;
            }
        }
        System.out.println("test2:" + (System.currentTimeMillis() - start));
    }

    @sun.misc.Contended
    private Long objectMem;

    @Test
    public void test3() {
        System.out.println(objectMem);
    }
}
