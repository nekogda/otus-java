package hw08.serialization.examples;

import java.util.Arrays;

public class ArrayExample {
    private final int[] codes = {1, 2, 3};

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        ArrayExample that = (ArrayExample) o;
        return Arrays.equals(codes, that.codes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(codes);
    }
}
