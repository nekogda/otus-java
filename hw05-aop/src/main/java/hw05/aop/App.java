package hw05.aop;

import hw05.aop.example.Example;
import hw05.aop.example.IExample;
import hw05.aop.proxy.DynamicInvocationHandler;

import java.lang.reflect.Proxy;

public class App {

    public static void main(String[] args) {
        IExample e = (IExample) Proxy.newProxyInstance(
                Example.class.getClassLoader(), Example.class.getInterfaces(),
                new DynamicInvocationHandler(new Example()));

        e.getHello();
        e.getGreeting("Eve");
        e.withoutAnnotation();
    }
}
