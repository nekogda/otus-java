package hw08.serialization.visitor;

import hw08.serialization.adapter.Adapter;
import hw08.serialization.adapter.ArrayHandler;
import hw08.serialization.adapter.JsonArrayBuilderAdapter;
import hw08.serialization.adapter.JsonObjectBuilderAdapter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.ArrayDeque;

public class JsonVisitor implements Visitor {
    private final ArrayDeque<Adapter> stack = new ArrayDeque<>();

    @Override
    public String toString() {
        if (stack.size() == 0) {
            return null;
        }
        assert stack.size() == 1;
        return stack.pop().build();
    }

    @Override
    public void visit(Integer value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Long value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Double value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Float value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Byte value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Short value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Character value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, String.valueOf(value)));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(String.valueOf(value)));
        }
    }

    @Override
    public void visit(String value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visit(Boolean value, String name) {
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.add(name, value));
        } else {
            last.add((JsonArrayBuilder j) -> j.add(value));
        }
    }

    @Override
    public void visitNull(String name) {
        if (stack.isEmpty()) {
            return;
        }
        var last = stack.getFirst();
        if (name != null) {
            last.add((JsonObjectBuilder j) -> j.addNull(name));
        } else {
            last.add((ArrayHandler) JsonArrayBuilder::addNull);
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
        assert stack.size() > 0;
        if (stack.size() == 1) {
            return;
        }
        merge();
    }

    private void merge() {
        var last = stack.pop();
        var prev = stack.getFirst();
        prev.add(last.getArrayAdapterHandler(), last.getObjectAdapterHandler());
    }
}
