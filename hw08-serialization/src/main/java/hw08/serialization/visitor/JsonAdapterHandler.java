package hw08.serialization.visitor;

import hw08.serialization.adapter.ArrayHandler;
import hw08.serialization.adapter.Handler;
import hw08.serialization.adapter.ObjectHandler;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class JsonAdapterHandler implements Handler {
    private final ArrayHandler arrayHandler;
    private final ObjectHandler objectHandler;

    public JsonAdapterHandler(ArrayHandler handler) {
        this(null, handler);
    }

    public JsonAdapterHandler(ObjectHandler handler) {
        this(handler, null);
    }

    public JsonAdapterHandler(ObjectHandler objectHandler, ArrayHandler arrayHandler) {
        this.arrayHandler = arrayHandler;
        this.objectHandler = objectHandler;
    }

    @Override
    public void apply(JsonArrayBuilder builder) {
        arrayHandler.apply(builder);
    }

    @Override
    public void apply(JsonObjectBuilder builder) {
        objectHandler.apply(builder);
    }
}
