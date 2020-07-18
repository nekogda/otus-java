package hw08.serialization.examples;

import java.util.Objects;

public class WithClassExample {
    private PrimitiveExample primitive = new PrimitiveExample();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithClassExample that = (WithClassExample) o;
        return Objects.equals(primitive, that.primitive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primitive);
    }
}
