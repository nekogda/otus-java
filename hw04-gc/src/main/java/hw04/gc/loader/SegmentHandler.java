package hw04.gc.loader;

import java.util.Deque;

public interface SegmentHandler {
    /**
     * @param data        collection of objects references
     * @param segmentSize size of arc segment in bytes
     */
    void handle(Deque<byte[]> data, int segmentSize);

    static int getBlockCount(int blockSize, int segmentSize) {
        return Math.floorMod(segmentSize, blockSize) > 0 ? segmentSize / blockSize + 1 : segmentSize / blockSize;
    }
}
