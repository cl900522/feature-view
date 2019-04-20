package acme.unit.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * 你可以在junit-platform.properteis修改参数指定默认的实例生命周期
 * junit.jupiter.testinstance.lifecycle.default
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * Alphanumeric：根据名称和形式参数列表以字母数字的形式对测试方法进行排序。
 * OrderAnnotation：根据通过注释指定的值以数字方式对 测试方法进行排序@Order。
 * Random：伪随机命令测试方法，并支持自定义种子的配置
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/**
 *指定测试名称
 */
@DisplayName("排序方法测试")
public class OrderRunningTest extends TimerCollectTest {
    public Integer i = 0;

    @Test
    @Order(1)
    @DisplayName("写入测试")
    void testInsert() {
        Assertions.assertTrue(i == 0);
        i++;
    }

    @Test
    @Order(2)
    @DisplayName("修改测试")
    void testUpdate() {
        Assertions.assertTrue(i == 1);
        i++;
    }

    @Test
    @Order(3)
    @DisplayName("删除测试")
    void testDelete() {
        Assertions.assertTrue(i == 2);
    }
}
