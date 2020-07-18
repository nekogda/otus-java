package hw08.serialization.adapter;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class JsonObjectBuilderAdapter implements Adapter {
    private final String name;
    private final JsonObjectBuilder builder;

    public JsonObjectBuilderAdapter(String name, JsonObjectBuilder builder) {
        this.builder = builder;
        this.name = name;
    }

    @Override
    public void add(ArrayHandler ha, ObjectHandler ho) {
        ho.apply(builder);
    }

    @Override
    public void add(ObjectHandler h) {
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