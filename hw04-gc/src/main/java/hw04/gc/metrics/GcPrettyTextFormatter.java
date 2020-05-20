package hw04.gc.metrics;

public class GcPrettyTextFormatter implements LogFormatter<GcLogRecord> {
    @Override
    public String format(GcLogRecord record) {
        return String.format("ts=%s,type=%s,duration=%s," +
                        "edenBefore=%s,edenAfter=%s," +
                        "survivorBefore=%s,survivorAfter=%s," +
                        "tenuredBefore=%s,tenuredAfter=%s,cpuTime=%s\n",
                record.getTs(),
                record.getType(),
                record.getDuration(),
                record.getEdenBeforeUsed(),
                record.getEdenAfterUsed(),
                record.getSurvivorBeforeUsed(),
                record.getSurvivorAfterUsed(),
                record.getTenuredBeforeUsed(),
                record.getTenuredAfterUsed(),
                record.getCpuTime());
    }
}