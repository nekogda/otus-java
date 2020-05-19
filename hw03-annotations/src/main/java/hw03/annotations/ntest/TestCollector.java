package hw03.annotations.ntest;

import java.io.IOException;

public interface TestCollector {
    TestSuite scan() throws IOException, ClassNotFoundException;
}
