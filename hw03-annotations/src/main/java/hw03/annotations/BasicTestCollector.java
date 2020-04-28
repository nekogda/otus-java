package hw03.annotations;

import java.io.IOException;
import java.lang.reflect.Method;

class BasicTestCollector implements TestCollector {
    private final String className;

    BasicTestCollector(String className) {
        this.className = className;
    }

    @Override
    public TestSuite scan() throws IOException, ClassNotFoundException {
        Class<?> klass = Class.forName(className);
        TestSuite testSuite = new TestSuite(klass);
        Method[] methods = klass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                testSuite.addTestCases(method);
            }
            if (method.isAnnotationPresent(Before.class)) {
                testSuite.addBeforeFixture(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                testSuite.addAfterFixture(method);
            }
        }
        return testSuite;
    }
}
