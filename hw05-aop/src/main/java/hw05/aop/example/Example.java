package hw05.aop.example;

import hw05.aop.annotations.Log;
import hw05.aop.annotations.LogLevel;

public class Example implements IExample {
    @Log(LogLevel.Debug)
    @Override
    public String getHello() {
        return "Hello";
    }

    @Log(LogLevel.Info)
    @Override
    public String getGreeting(String name) {
        return "Hello " + name;
    }

    @Override
    public String withoutAnnotation() {
        return "without annotation";
    }
}
