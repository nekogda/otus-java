package hw03.annotations;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        TestCollector collector = new BasicTestCollector("hw03.annotations.AnnotationsTest");
        TestReporter reporter = new BasicTestReporter(new ColorTextBasicTemplate(), new OutputStreamWriter(System.out));

        TestRunner.run(collector, reporter);
    }

    static void run(TestCollector collector, TestReporter reporter) throws ClassNotFoundException, IOException {
        TestSuite ts = collector.scan();
        ts.run();
        reporter.run(ts);
    }
}


