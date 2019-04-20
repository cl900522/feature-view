package acme.unit.test;

import acme.unit.test.extention.TimingExtension;
import org.junit.jupiter.api.*;

import java.util.logging.Logger;

public class RepeatTest {
    private static final Logger logger = Logger.getLogger(TimingExtension.class.getName());

    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        logger.info(String.format("About to execute repetition %d of %d for %s", //
                currentRepetition, totalRepetitions, methodName));
    }

    @DisplayName("Go")
    @RepeatedTest(value = 10, name = "{displayName} :: repetition {currentRepetition}/{totalRepetitions}")
    void repeatedTest() {

    }
}
