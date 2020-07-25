package hw08.serialization;

import hw08.serialization.visitor.JsonVisitor;
import hw08.serialization.visitor.Visitor;

import java.lang.reflect.Array;
import java.util.Collection;

public class MyJson {
    private final Visitor visitor;

    public MyJson() {
        this(new JsonVisitor());
    }

    public MyJson(Visitor visitor) {
        this.visitor = visitor;
    }

    public String toJson(Object obj) {
        walk(obj, visitor);
        return visitor.toString();
    }

    private void walk(Object value, Visitor visitor) {
        walkRec(visitor, value, null);
    }

    private void walkRec(Visitor visitor, Object value, String name) {
        if (value == null) {
            visitor.visitNull(name);
        } else if (value instanceof Integer) {
            visitor.visit((Integer) value, name);
        } else if (value instanceof Byte) {
            visitor.visit((Byte) value, name);
        } else if (value instanceof Short) {
            visitor.visit((Short) value, name);
        } else if (value instanceof Long) {
            visitor.visit((Long) value, name);
        } else if (value instanceof Float) {
            visitor.visit((Float) value, name);
        } else if (value instanceof Double) {
            visitor.visit((Double) value, name);
        } else if (value instanceof Boolean) {
            visitor.visit((Boolean) value, name);
        } else if (value instanceof Character) {
            visitor.visit((Character) value, name);
        } else if (value instanceof String) {
            visitor.visit((String) value, name);
        } else if (value instanceof Collection) {
            visitor.visitCollectionEnter(name);
            Collection<?> coll = (Collection<?>) value;
            coll.forEach(e -> walkRec(visitor, e, null));
            visitor.visitCollectionExit();
        } else if (value.getClass().isArray()) {
            visitor.visitCollectionEnter(name);
            for (int i = 0; i < Array.getLength(value); i++) {
                walkRec(visitor, Array.get(value, i), null);
            }
            visitor.visitCollectionExit();
        } else {
            visitor.visitObjectEnter(name);
            for (var field : value.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue;
                try {
                    fieldValue = field.get(value);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
                walkRec(visitor, fieldValue, field.getName());
            }
            visitor.visitObjectExit();
        }
    }
}
