package hw08.serialization.adapter;

import javax.json.JsonObjectBuilder;

@FunctionalInterface
public interface ObjectHandler {
    void apply(JsonObjectBuilder builder);
}

