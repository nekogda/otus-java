package hw05.aop.proxy;

import hw05.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicInvocationHandler implements InvocationHandler {
    private final Map<Method, String> methods = new HashMap<>();
    private final Object target;

    public DynamicInvocationHandler(Object target, Class<?> proxyInterface) {
        if (target == null) {
            throw new IllegalArgumentException("target must be not null");
        }
        if (!proxyInterface.isAssignableFrom(target.getClass())) {
            throw new IllegalArgumentException("target must implement proxyInterface");
        }
        this.target = target;
        for (var proxyMethod : proxyInterface.getMethods()) {
            Method targetMethod;
            try {
                targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("invariant violation");
            }
            var logAnnotation = targetMethod.getDeclaredAnnotation(Log.class);
            if (logAnnotation != null) {
                methods.put(proxyMethod, logAnnotation.value().toString());
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
        var logLevel = methods.get(method);
        if (logLevel != null) {
            System.out.println(formatLogRecord(method.getName(), logLevel, args));
        }
        return method.invoke(target, args);
    }
}
