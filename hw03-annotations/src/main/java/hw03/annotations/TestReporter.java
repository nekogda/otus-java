package hw03.annotations;

import java.io.IOException;

public interface TestReporter {
    void run(TestSuite ts) throws IOException;
}
