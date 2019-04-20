package acme.unit.test;

import acme.unit.test.agg.MapAggregator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

public class ParamTest extends TimerCollectTest {

    @ParameterizedTest(name = "Test {0}")
    @NullSource
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void paramTest(String candidate) {
        System.out.println("Check:" + candidate);
        Assertions.assertTrue(candidate != null && !candidate.equals(""));
    }

    /**
     * 枚举去除DAYS
     *
     * @param candidate
     */
    @ParameterizedTest(name = "Unit {0}")
    @EnumSource(value = TimeUnit.class, mode = EXCLUDE, names = {"DAYS"})
    void unitTest(TimeUnit candidate) {
        System.out.println("Check:" + candidate);
    }

    /**
     * 使用方法生产
     *
     * @param argument
     */
    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(@AggregateWith(MapAggregator.class) Map person) {
        assertNotNull(person.get("name"));
        assertNotEquals(0, person.get("age"));
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void testWithArgumentsSource(String argument) {
        assertNotNull(argument);
    }

    public static class MyArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of("apple", "banana").map(Arguments::of);
        }
    }

}
