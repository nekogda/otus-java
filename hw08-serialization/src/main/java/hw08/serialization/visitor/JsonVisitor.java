package hw08.serialization.visitor;

import hw08.serialization.adapter.ArrayHandler;
import hw08.serialization.adapter.BuilderAdapter;
import hw08.serialization.adapter.JsonArrayBuilderAdapter;
import hw08.serialization.adapter.JsonObjectBuilderAdapter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.ArrayDeque;

public class JsonVisitor implements Visitor {
    private final ArrayDeque<BuilderAdapter> stack = new ArrayDeque<>();
    private String result;

    private <T> boolean processScalar(T value) {
        if (stack.isEmpty()) {
            result = value.toString();
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (stack.isEmpty()) {
            return result;
        }
        return stack.pop().build();
    }

    @Override
    public void visit(Integer value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Long value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Double value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Float value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Byte value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Short value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Character value, String name) {
        visit(value.toString(), name);
    }

    @Override
    public void visit(String value, String name) {
        if (processScalar("\"" + value + "\"")) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visit(Boolean value, String name) {
        if (processScalar(value)) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.add(name, value)));
        } else {
            last.add(new JsonAdapterHandler((JsonArrayBuilder j) -> j.add(value)));
        }
    }

    @Override
    public void visitNull(String name) {
        if (processScalar("null")) return;
        var last = stack.getFirst();
        if (name != null) {
            last.add(new JsonAdapterHandler((JsonObjectBuilder j) -> j.addNull(name)));
        } else {
            last.add(new JsonAdapterHandler((ArrayHandler) JsonArrayBuilder::addNull));
        }
    }

    @Override
    public void visitCollectionEnter(String name) {
        stack.push(new JsonArrayBuilderAdapter(name, Json.createArrayBuilder()));
    }

    @Override
    public void visitCollectionExit() {
        closeCompoundObject();
    }

    @Override
    public void visitObjectEnter(String name) {
        stack.push(new JsonObjectBuilderAdapter(name, Json.createObjectBuilder()));
    }

    @Override
    public void visitObjectExit() {
        closeCompoundObject();
    }

    private void closeCompoundObject() {
        if (stack.size() == 1) {
            return;
        }
        merge();
    }

    private void merge() {
        var last = stack.pop();
        var prev = stack.getFirst();
        prev.add(new JsonAdapterHandler(last.getObjectAdapterHandler(), last.getArrayAdapterHandler()));
    }
}
