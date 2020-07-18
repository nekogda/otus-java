package hw08.serialization.adapter;

public interface Adapter {
    default void add(ArrayHandler h) {
        throw new UnsupportedOperationException();
    }

    default void add(ObjectHandler h) {
        throw new UnsupportedOperationException();
    }

    void add(ArrayHandler ha, ObjectHandler ho);

    String build();

    ArrayHandler getArrayAdapterHandler();

    ObjectHandler getObjectAdapterHandler();
}