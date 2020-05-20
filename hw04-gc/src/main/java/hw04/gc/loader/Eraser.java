package hw04.gc.loader;

import java.util.Deque;

class Eraser implements SegmentHandler {
    private final int range;
    private final int blockSize;

    Eraser(int blockSize, int range) {
        this.blockSize = blockSize;
        this.range = range;
    }

    @Override
    public void handle(Deque<byte[]> data, int segmentSize) {
        if (segmentSize == 0) {
            return;
        }
        var blocks = SegmentHandler.getBlockCount(blockSize, segmentSize);
        for (int i = 0; i < blocks; i++) {
            var removeIdx = i % range;
            for (int j = 0; j < removeIdx; j++) {
                var fst = data.pollFirst();
                if (fst == null) {
                    continue;
                }
                data.addLast(fst);
            }
            data.pollFirst(); // remove element from q
        }
    }
}
