package ru.otus.jdbc.dao;

import javax.sql.DataSource;

public interface DaoAbstractFactory<T> {
    T getInstance(DataSource dataSource);
}
