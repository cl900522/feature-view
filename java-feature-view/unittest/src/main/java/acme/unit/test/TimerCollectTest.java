package acme.unit.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class TimerCollectTest {
    private static Long start = null;
    private static Long end = null;

    @BeforeAll
    public static void startTime() {
        start = System.currentTimeMillis();
    }

    @AfterAll
    public static void endTime() {
        end = System.currentTimeMillis();
        System.out.println("Run all tests cost:" + (end - start) + "ms");
    }
}
