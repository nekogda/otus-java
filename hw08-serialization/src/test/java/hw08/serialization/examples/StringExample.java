package hw08.serialization.examples;

import java.util.Objects;

public class StringExample {
    private final String name = "Joe";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringExample that = (StringExample) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
