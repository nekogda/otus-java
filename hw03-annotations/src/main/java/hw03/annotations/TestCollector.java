package hw03.annotations;

import java.io.IOException;

public interface TestCollector {
    TestSuite scan() throws IOException, ClassNotFoundException;
}
