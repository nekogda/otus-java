package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    public static final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private List<Object> getAppComponentsDependencies(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(paramType -> {
                    var dependency = getAppComponent(paramType);
                    if (dependency == null) {
                        throw new IllegalStateException("can't find component dependencies");
                    }
                    return dependency;
                }).collect(Collectors.toList());
    }

    private Object createAppComponent(Object configInstance, Method method) {
        try {
            var dependencies = getAppComponentsDependencies(method);
            return method.invoke(configInstance, dependencies.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("can't create component", e);
            throw new IllegalStateException(e);
        }
    }

    private void addAppComponent(Object component, String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            logger.error("component name already exists, componentName={}", componentName);
            throw new IllegalArgumentException("componentName already exists");
        }
        appComponents.add(component);
        appComponentsByName.put(componentName, component);
    }

    private Object getConfigInstance(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("expected default public constructor", e);
            throw new IllegalArgumentException("expected default public constructor", e);
        }
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Object configInstance = getConfigInstance(configClass);

        Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    var component = createAppComponent(configInstance, method);
                    addAppComponent(component, method.getAnnotation(AppComponent.class).name());
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .findFirst().orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
