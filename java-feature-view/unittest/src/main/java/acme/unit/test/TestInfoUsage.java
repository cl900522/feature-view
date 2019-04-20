package acme.unit.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("TestInfo使用")
public class TestInfoUsage {
    TestInfoUsage(TestInfo testInfo) {

    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        assertTrue(displayName.equals("名称检测") || displayName.equals("tag检测"));
    }

    @Test
    @DisplayName("名称检测")
    void nameCheck(TestInfo testInfo) {
        assertEquals("名称检测", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("tag检测")
    @Tag("my-tag")
    void tagCheck(TestInfo testInfo) {
        assertTrue(testInfo.getTags().contains("my-tag"));
    }
}
