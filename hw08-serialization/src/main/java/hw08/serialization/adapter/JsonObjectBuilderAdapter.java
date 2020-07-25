package hw08.serialization.adapter;

import hw08.serialization.visitor.JsonAdapterHandler;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class JsonObjectBuilderAdapter implements BuilderAdapter {
    private final String name;
    private final JsonObjectBuilder builder;

    public JsonObjectBuilderAdapter(String name, JsonObjectBuilder builder) {
        this.builder = builder;
        this.name = name;
    }

    @Override
    public void add(JsonAdapterHandler h) {
        h.apply(builder);
    }

    @Override
    public ArrayHandler getArrayAdapterHandler() {
        return (JsonArrayBuilder j) -> j.add(builder);
    }

    @Override
    public String build() {
        return builder.build().toString();
    }

    @Override
    public ObjectHandler getObjectAdapterHandler() {
        return (JsonObjectBuilder j) -> j.add(name, builder);
    }
}