package hw05.aop;

import hw05.aop.example.Example;
import hw05.aop.example.IExample;
import hw05.aop.proxy.DynamicInvocationHandler;

import java.lang.reflect.Proxy;

public class App {

    public static void main(String[] args) {
        IExample e = (IExample) Proxy.newProxyInstance(
                Example.class.getClassLoader(), new Class[]{IExample.class},
                new DynamicInvocationHandler(new Example(), IExample.class));

        // annotated, w/o arguments
        e.getHello();
        // annotated, with arguments
        e.getGreeting("Eve");
        // not annotated, overloaded
        e.getGreeting("Eve", "!!!");
        // not annotated, w/o arguments
        e.withoutAnnotation();
    }
}
