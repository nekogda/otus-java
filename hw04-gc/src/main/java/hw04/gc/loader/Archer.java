package hw04.gc.loader;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Archer implements Iterable<Pair> {
    private final double freq;
    private final double segment;
    private final int segments;
    private final long allocSize;
    private final int sndArcStartSegment;

    Archer(int rounds, double freq, int segments, long allocSize, double filterOffset) {
        this.freq = freq;
        this.segment = Math.PI / segments;
        this.segments = segments * rounds;
        this.allocSize = allocSize;
        this.sndArcStartSegment = (int) (segments * filterOffset);
    }

    @Override
    public Iterator<Pair> iterator() {
        return new ArcherIterator();
    }

    class ArcherIterator implements Iterator<Pair> {
        private double yPrevFst;
        private double yPrevSnd;
        private int currentFstSegment;
        private int currentSndSegment;
        private boolean filterOn;

        @Override
        public boolean hasNext() {
            return currentFstSegment < segments;
        }

        @Override
        public Pair next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int fst;
            {
                var x = currentFstSegment * segment;
                var y = Math.abs(Math.sin(freq * x));
                var deltaY = Math.abs(yPrevFst - y);
                yPrevFst = y;
                fst = (int) (allocSize * deltaY);
            }

            int snd = 0;
            if (filterOn || currentFstSegment == sndArcStartSegment) {
                filterOn = true;
                var x = currentSndSegment * segment;
                var y = Math.abs(Math.sin(freq * x));
                if (yPrevSnd == 0) {
                    yPrevSnd = y;
                } else {
                    var deltaY = Math.abs(yPrevSnd - y);
                    yPrevSnd = y;
                    snd = (int) (allocSize * deltaY);
                }
                ++currentSndSegment;
            }
            ++currentFstSegment;

            return new Pair(fst, snd);
        }
    }
}

class Pair {
    private int fst;
    private int snd;

    @Override
    public String toString() {
        return "Pair{" +
                "fst=" + fst +
                ", snd=" + snd +
                '}';
    }

    Pair(int fst, int snd) {
        this.fst = fst;
        this.snd = snd;
    }

    int getFst() {
        return fst;
    }

    int getSnd() {
        return snd;
    }
}