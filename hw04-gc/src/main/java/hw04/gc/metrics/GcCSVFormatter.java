package hw04.gc.metrics;

public class GcCSVFormatter implements LogFormatter<GcLogRecord> {
    @Override
    public String format(GcLogRecord record) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
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