package hw05.aop.example;

public interface IExample {
    String getHello();

    String getGreeting(String name);

    String getGreeting(String name, String suffix);

    String withoutAnnotation();
}
