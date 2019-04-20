package acme.unit.test.agg;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.HashMap;
import java.util.Map;

public class MapAggregator implements ArgumentsAggregator {
    @Override
    public Map<String, Object> aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
        Map<String, Object> p = new HashMap<>();
        p.put("name", arguments.getString(0));
        p.put("age", arguments.getInteger(1));
        return p;
    }
}
