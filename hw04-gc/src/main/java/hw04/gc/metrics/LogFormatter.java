package hw04.gc.metrics;

public interface LogFormatter<T> {
    String format(T record);
}
