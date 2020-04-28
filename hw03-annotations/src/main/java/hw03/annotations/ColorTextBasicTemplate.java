package hw03.annotations;

class ColorTextBasicTemplate implements BasicTemplate {
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_NONE = "";
    static final String FORMAT = "%4s | %-65s | %s%-6s" + ANSI_RESET + " |\n";
    static final String FORMAT_ERR = "       %s\n";

    @Override
    public String render(BasicReportData data) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(FORMAT, "#", "TEST NAME", ANSI_NONE, "STATUS"));
        for (BasicReportData.Row row : data.getRows()) {
            String color = row.status == TestCaseStatus.err ? ANSI_RED : ANSI_GREEN;
            result.append(String.format(FORMAT, row.id, row.testName, color, row.status));
            if (row.status == TestCaseStatus.err) {
                result.append(String.format(FORMAT_ERR, row.errCause));
            }
        }
        result.append("Summary: " + data.getNumOfSuccess() + "/" + data.getRows().size());
        return result.toString();
    }
}
