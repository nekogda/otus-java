package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class EntityClassMetaDataImlp<T> implements EntityClassMetaData<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImlp.class);
    private final Class<T> klass;

    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> FieldsWithoutId;

    public EntityClassMetaDataImlp(Class<T> klass) {
        this.klass = klass;
    }

    @Override
    public String getName() {
        return klass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor != null) {
            return constructor;
        }

        try {
            constructor = klass.getConstructor();
            return constructor;
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override
    public Field getIdField() {
        if (idField != null) {
            return idField;
        }
        idField = Arrays.stream(klass.getDeclaredFields())
                .filter((f) -> f.isAnnotationPresent(Id.class))
                .peek((f) -> f.setAccessible(true))
                .findFirst().orElseThrow();
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (allFields != null) {
            return allFields;
        }
        allFields = Arrays.stream(klass.getDeclaredFields())
                .peek((f) -> f.setAccessible(true))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (FieldsWithoutId != null) {
            return FieldsWithoutId;
        }
        var id = getIdField();
        FieldsWithoutId = getAllFields()
                .stream()
                .filter((f) -> !f.equals(id))
                .peek((f) -> f.setAccessible(true))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
        return FieldsWithoutId;
    }
}
