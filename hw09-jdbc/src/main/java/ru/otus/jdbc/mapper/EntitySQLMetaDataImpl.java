package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    private final EntityClassMetaData<T> classMetaData;
    private String querySelectAll;
    private String querySelectById;
    private String queryInsert;
    private String queryUpdate;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        if (querySelectAll != null) {
            return querySelectAll;
        }
        var colNames = classMetaData.getAllFields()
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        var tableName = classMetaData.getName();
        querySelectAll = "SELECT " + colNames + " FROM " + tableName;
        return querySelectAll;
    }

    @Override
    public String getSelectByIdSql() {
        if (querySelectById != null) {
            return querySelectById;
        }
        var colNames = classMetaData.getAllFields()
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        var tableName = classMetaData.getName();
        var pkName = classMetaData.getIdField().getName();
        querySelectById = "SELECT " + colNames + " FROM " + tableName +
                " WHERE " + pkName + " = ?";
        return querySelectById;
    }

    @Override
    public String getInsertSql() {
        if (queryInsert != null) {
            return queryInsert;
        }
        var tableName = classMetaData.getName();
        var colNames = classMetaData.getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(", ", "(", ")"));

        var placeholders = Stream
                .generate(() -> "?")
                .limit(classMetaData.getFieldsWithoutId().size())
                .collect(Collectors.joining(", ", "(", ")"));

        queryInsert = "INSERT INTO " + tableName + " " + colNames
                + " VALUES " + placeholders;
        return queryInsert;
    }

    @Override
    public String getUpdateSql() {
        if (queryUpdate != null) {
            return queryUpdate;
        }
        var tableName = classMetaData.getName();
        var colNamesWithPlaceholders = classMetaData
                .getFieldsWithoutId()
                .stream()
                .map((f) -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));
        var pkName = classMetaData.getIdField().getName();
        queryUpdate = "UPDATE " + tableName
                + " SET " + colNamesWithPlaceholders + " WHERE " + pkName + " = ?";
        return queryUpdate;
    }
}
