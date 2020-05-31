package hw05.aop.proxy;

import hw05.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicInvocationHandler implements InvocationHandler {
    private final Map<String, String> methods = new HashMap<>();
    private final Object target;

    public DynamicInvocationHandler(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("target must be not null");
        }
        this.target = target;
        for (var method : target.getClass().getMethods()) {
            var logAnnotation = method.getDeclaredAnnotation(Log.class);
            if (logAnnotation != null) {
                methods.put(method.getName(), logAnnotation.value().toString());
            }
        }
    }

    private String formatLogRecord(String methodName, String logLevel, Object[] args) {
        var format = "[%s] Method %s called with %s";
        String arguments;
        if (args == null) {
            arguments = ("no arguments");
        } else {
            arguments = Arrays.stream(args)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "arguments: ", ""));
        }
        return String.format(format, logLevel, methodName, arguments);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var methodName = method.getName();
        var logLevel = methods.get(methodName);
        if (logLevel != null) {
            System.out.println(formatLogRecord(methodName, logLevel, args));
        }
        return method.invoke(target, args);
    }
}
