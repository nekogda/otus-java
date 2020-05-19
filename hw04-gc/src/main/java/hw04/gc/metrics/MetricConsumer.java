package hw04.gc.metrics;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public interface MetricConsumer<T> extends Consumer<T>, Callable<Void> {}
