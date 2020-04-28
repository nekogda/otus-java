package hw03.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class TestSuite implements Iterable<TestCase> {

    private final ArrayList<Method> beforeFixtures = new ArrayList<>();
    private final ArrayList<Method> afterFixtures = new ArrayList<>();
    private final ArrayList<TestCase> testCases = new ArrayList<>();
    private final Class<?> klass;

    public TestSuite(Class<?> klass) {
        this.klass = klass;
    }

    public void addBeforeFixture(Method fixture) {
        beforeFixtures.add(fixture);
    }

    public void addAfterFixture(Method fixture) {
        afterFixtures.add(fixture);
    }

    public void addTestCases(Method test) {
        testCases.add(new TestCase(test));
    }

    void run() {
        for (TestCase tc : testCases) {
            try {
                Object instance = klass.getDeclaredConstructor().newInstance();
                tc.run(instance, beforeFixtures, afterFixtures);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return testCases.size();
    }

    @Override
    public Iterator<TestCase> iterator() {
        return testCases.iterator();
    }
}
