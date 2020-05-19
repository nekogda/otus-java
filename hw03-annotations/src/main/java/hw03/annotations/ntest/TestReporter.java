package hw03.annotations.ntest;

import java.io.IOException;

public interface TestReporter {
    void run(TestSuite ts) throws IOException;
}
