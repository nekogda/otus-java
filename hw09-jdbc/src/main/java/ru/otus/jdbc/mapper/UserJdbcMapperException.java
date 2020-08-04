package ru.otus.jdbc.mapper;

public class UserJdbcMapperException extends RuntimeException {
    public UserJdbcMapperException(Exception ex) {
        super(ex);
    }
}
