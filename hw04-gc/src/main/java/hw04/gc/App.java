package hw04.gc;

import hw04.gc.loader.LoadRunner;
import hw04.gc.metrics.GcCSVFormatter;
import hw04.gc.metrics.GcMetricConsumer;
import hw04.gc.metrics.MetricCollector;

import javax.management.*;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws InstanceNotFoundException, ExecutionException, InterruptedException {
        // configure metric collector, subscribe to gc events and start metric collection/reporting
        var gcReporter = new GcMetricConsumer<>(
                new OutputStreamWriter(System.out),
                // new GcPrettyTextFormatter());
                new GcCSVFormatter());
        var metricCollector = new MetricCollector(gcReporter);
        metricCollector.start();

        // 4096m heap load
        var loadRunner = new LoadRunner(0.65, 2000, 1536 * 1048576L, 0.35);
        // 256m  heap load
        // var loadRunner = new LoadRunner(0.65, 2000, 196 * 1048576L, 0.35);
        loadRunner.run(50);

        metricCollector.stop();
    }
}