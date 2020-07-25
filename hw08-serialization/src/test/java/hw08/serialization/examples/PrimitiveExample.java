package hw08.serialization.examples;

import java.util.Objects;

public class PrimitiveExample {
    private final byte byteField = 1;
    private final short shortField = 2;
    private final int intField = 3;
    private final long longField = 4L;

    private final float floatField = 5.0F;
    private final double doubleField = 6.0;

    private final boolean booleanField = true;
    private final char charField = 'x';

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveExample that = (PrimitiveExample) o;
        return byteField == that.byteField &&
                shortField == that.shortField &&
                intField == that.intField &&
                longField == that.longField &&
                Float.compare(that.floatField, floatField) == 0 &&
                Double.compare(that.doubleField, doubleField) == 0 &&
                booleanField == that.booleanField &&
                charField == that.charField;
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteField, shortField, intField, longField, floatField, doubleField, booleanField, charField);
    }
}
