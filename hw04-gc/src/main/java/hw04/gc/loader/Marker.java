package hw04.gc.loader;

import java.util.Deque;

public class Marker implements SegmentHandler {
    public static long count = 0;
    private int blockSize;

    public Marker(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public void handle(Deque<byte[]> data, int segmentSize) {
        var blocks = SegmentHandler.getBlockCount(blockSize, segmentSize);
        for (int i = 0; i < blocks; i++) {
            data.addLast(new byte[blockSize]);
            ++Marker.count;
        }
    }
}
