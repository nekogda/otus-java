package hw04.gc.metrics;

enum GcType {
    Major, Minor
}

public class GcLogRecord {
    private final long ts;
    private final GcType type;
    private final long duration;
    private final long edenBeforeUsed;
    private final long edenAfterUsed;
    private final long survivorBeforeUsed;
    private final long survivorAfterUsed;
    private final long tenuredBeforeUsed;
    private final long tenuredAfterUsed;
    private final long cpuTime;

    public long getCpuTime() {
        return cpuTime;
    }

    public long getTs() {
        return ts;
    }

    public GcType getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

    public long getEdenBeforeUsed() {
        return edenBeforeUsed;
    }

    public long getEdenAfterUsed() {
        return edenAfterUsed;
    }

    public long getSurvivorBeforeUsed() {
        return survivorBeforeUsed;
    }

    public long getSurvivorAfterUsed() {
        return survivorAfterUsed;
    }

    public long getTenuredBeforeUsed() {
        return tenuredBeforeUsed;
    }

    public long getTenuredAfterUsed() {
        return tenuredAfterUsed;
    }

    public GcLogRecord(long ts, GcType type, long duration, long edenBeforeUsed, long edenAfterUsed, long survivorBeforeUsed, long survivorAfterUsed, long tenuredBeforeUsed, long tenuredAfterUsed, long cpuTime) {
        this.ts = ts;
        this.type = type;
        this.duration = duration;
        this.edenBeforeUsed = edenBeforeUsed;
        this.edenAfterUsed = edenAfterUsed;
        this.survivorBeforeUsed = survivorBeforeUsed;
        this.survivorAfterUsed = survivorAfterUsed;
        this.tenuredBeforeUsed = tenuredBeforeUsed;
        this.tenuredAfterUsed = tenuredAfterUsed;
        this.cpuTime = cpuTime;
    }
}
