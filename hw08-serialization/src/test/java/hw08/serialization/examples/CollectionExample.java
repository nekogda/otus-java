package hw08.serialization.examples;

import java.util.List;
import java.util.Objects;

public class CollectionExample {
    private final List<Integer> numbers = List.of(1, 2, 3);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionExample that = (CollectionExample) o;
        return numbers.equals(that.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }
}
