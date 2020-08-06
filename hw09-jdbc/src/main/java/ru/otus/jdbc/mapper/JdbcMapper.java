package ru.otus.jdbc.mapper;

public interface JdbcMapper<T> {
    long insert(T objectData);

    void update(T objectData);

    long insertOrUpdate(T objectData);

    T findById(long id);
}
