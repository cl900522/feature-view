package acme.unit.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AssumptionTest {

    @Test
    void testOnlyOnCiServer() {
        String path = System.getenv("PATH");
        assumeTrue(path.contains("System"));
        // remainder of test
    }

    @Test
    void testOnlyOnDeveloperWorkstation() {
        assumeTrue("DEV".equals(System.getenv("PATH")),
                () -> "Aborting test: not on developer workstation");
        // remainder of test
    }
}
