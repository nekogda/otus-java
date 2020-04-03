package hw01.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloOtusTest {
    @Test public void testAppHasAGreeting() {
        HelloOtus classUnderTest = new HelloOtus();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
