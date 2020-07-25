package hw08.serialization.adapter;

import hw08.serialization.visitor.JsonAdapterHandler;

public interface BuilderAdapter {

    void add(JsonAdapterHandler h);

    String build();

    ArrayHandler getArrayAdapterHandler();

    ObjectHandler getObjectAdapterHandler();
}