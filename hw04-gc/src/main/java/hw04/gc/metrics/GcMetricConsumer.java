package hw04.gc.metrics;

import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GcMetricConsumer<T> implements MetricConsumer<T> {
    private final BlockingQueue<T> queue;
    private final Writer writer;
    private final LogFormatter<T> formatter;

    public GcMetricConsumer(Writer writer, LogFormatter<T> formatter) {
        this.queue = new LinkedBlockingDeque<>();
        this.writer = writer;
        this.formatter = formatter;
    }

    @Override
    public void accept(T t) {
        queue.add(t);
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            try {
                T element = queue.take();
                writer.write(formatter.format(element));
                writer.flush();
            } catch (InterruptedException e) {
                while (true) {
                    T element = queue.poll();
                    if (element == null) {
                        return null;
                    }
                    writer.write(formatter.format(element));
                    writer.flush();
                }
            }
        }
    }
}
