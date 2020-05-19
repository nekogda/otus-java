package hw04.gc.loader;

import java.util.ArrayDeque;
import java.util.Deque;

public class LoadRunner {
    private final double freq;
    private final int segments;
    private final long allocSize;
    private final double filterOffset;
    private final Deque<byte[]> data;

    public LoadRunner(double freq, int segments, long allocSize, double eraserOffset) {
        this.freq = freq;
        this.segments = segments;
        this.allocSize = allocSize;
        this.filterOffset = eraserOffset;
        this.data = new ArrayDeque<>();
    }

    public void run(int rounds) {
        var archer = new Archer(rounds, freq, segments , allocSize , filterOffset);
        var spike = new Marker(1024);
        var filter = new Eraser(1024, 250);
        for (var pair : archer) {
            spike.handle(data, pair.getFst());
            filter.handle(data, pair.getSnd());
        }
        data.clear();
    }
}
