package hw03.annotations.ntest;

import java.lang.reflect.Method;
import java.util.Collection;

class TestCase {
    private final Method test;
    private TestCaseStatus status;
    private Exception errCause;

    TestCase(Method test) {
        this.test = test;
    }

    TestCaseStatus getStatus() {
        return status;
    }

    String getTestName() {
        return test.getName();
    }

    void run(Object instance, Collection<? extends Method> before, Collection<? extends Method> after) {
        Exception beforeCause = runBefore(instance, before);
        updateStatus(beforeCause);

        Exception testCause = runTest(instance);
        updateStatus(testCause);

        Exception afterCause = runAfter(instance, after);
        updateStatus(afterCause);

        if (status != TestCaseStatus.err) {
            status = TestCaseStatus.ok;
        }
    }

    private Exception runTest(Object instance) {
        if (status != TestCaseStatus.err) {
            return call(instance, test);
        }
        return null;
    }

    private void updateStatus(Exception cause) {
        if (this.errCause == null && cause != null) {
            this.errCause = cause;
            status = TestCaseStatus.err;
        }
    }

    private Exception runAfter(Object instance, Collection<? extends Method> after) {
        Exception result = null;
        for (Method a : after) {
            Exception err = call(instance, a);
            if (err != null && result == null) {
                result = err;
            }
        }
        return result;
    }

    private Exception runBefore(Object instance, Collection<? extends Method> before) {
        Exception result = null;
        for (Method b : before) {
            result = call(instance, b);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private Exception call(Object instance, Method method) {
        try {
            method.invoke(instance);
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    Exception getErrCause() {
        return errCause;
    }
}
