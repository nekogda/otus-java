package hw03.annotations;

import java.io.IOException;
import java.io.Writer;

class BasicTestReporter implements TestReporter {

    private final ColorTextBasicTemplate tmpl;
    private final Writer out;

    BasicTestReporter(ColorTextBasicTemplate tmpl, Writer out) {
        this.tmpl = tmpl;
        this.out = out;
    }

    private BasicReportData makeReport(TestSuite ts) {
        BasicReportData data = new BasicReportData();
        int i = 1;
        int numOfErrors = 0;
        for (TestCase tc : ts) {
            if (tc.getStatus() == TestCaseStatus.err) {
                numOfErrors++;
            }
            data.addRow(i, tc.getTestName(), tc.getStatus(), tc.getErrCause() == null ? null : tc.getErrCause().toString());
            i++;
        }
        data.setNumOfSuccess(ts.size() - numOfErrors);
        return data;
    }

    @Override
    public void run(TestSuite ts) throws IOException {
        BasicReportData report = makeReport(ts);
        String data = tmpl.render(report);
        out.write(data);
        out.flush();
    }
}
