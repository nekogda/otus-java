package hw08.serialization.adapter;

import javax.json.JsonArrayBuilder;

@FunctionalInterface
public interface ArrayHandler {
    void apply(JsonArrayBuilder builder);
}
