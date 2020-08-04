package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final DbExecutor<T> executor;
    private final EntityClassMetaData<T> classMetaData;
    private final EntitySQLMetaData<T> sqlMetaData;
    private final SessionManager sessionManager;
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    public JdbcMapperImpl(
            SessionManager sessionManager,
            DbExecutor<T> executor,
            EntityClassMetaData<T> classMetaData,
            EntitySQLMetaData<T> sqlMetaData) {
        this.sessionManager = sessionManager;
        this.executor = executor;
        this.classMetaData = classMetaData;
        this.sqlMetaData = sqlMetaData;
    }

    @Override
    public long insert(T objectData) {
        var query = sqlMetaData.getInsertSql();
        var params = classMetaData.getFieldsWithoutId()
                .stream()
                .map(f -> {
                    try {
                        return f.get(objectData);
                    } catch (IllegalAccessException e) {
                        logger.error("invariant violation", e);
                        throw new UserJdbcMapperException(e);
                    }
                })
                .collect(Collectors.toList());

        long id = 0;
        var connection = sessionManager.getCurrentSession().getConnection();

        try {
            id = executor.executeInsert(connection, query, params);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new UserJdbcMapperException(e);
        }

        return id;
    }

    @Override
    public void update(T objectData) {
        var query = sqlMetaData.getUpdateSql();
        Object id;

        try {
            id = classMetaData.getIdField().get(objectData);
        } catch (IllegalAccessException e) {
            logger.error("invariant violation", e);
            throw new UserJdbcMapperException(e);
        }

        var params = classMetaData.getFieldsWithoutId()
                .stream()
                .map(f -> {
                    try {
                        return f.get(objectData);
                    } catch (IllegalAccessException e) {
                        logger.error("invariant violation", e);
                        throw new UserJdbcMapperException(e);
                    }
                })
                .collect(Collectors.toList());

        params.add(id);
        var connection = sessionManager.getCurrentSession().getConnection();

        try {
            executor.executeUpdate(connection, query, params);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new UserJdbcMapperException(e);
        }
    }

    @Override
    public long insertOrUpdate(T objectData) {
        long id = 0;
        try {
            id = (long) classMetaData.getIdField().get(objectData);
        } catch (IllegalAccessException | NullPointerException | ClassCastException throwables) {
            logger.error(throwables.getMessage());
            throw new UserJdbcMapperException(throwables);
        }

        var foundObject = findById(id);
        if (foundObject == null) {
            return insert(objectData);
        } else {
            update(objectData);
            return id;
        }
    }

    @Override
    public T findById(long id) {
        var query = sqlMetaData.getSelectByIdSql();
        var constructor = classMetaData.getConstructor();
        var connection = sessionManager.getCurrentSession().getConnection();
        Optional<T> result;
        try {
            result = executor.executeSelect(connection, query, id, (rs) -> {
                try {
                    if (rs.next()) {
                        T instance = null;
                        try {
                            instance = constructor.newInstance();
                        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                            logger.error(e.getMessage());
                            throw new UserJdbcMapperException(e);
                        }
                        var fields = classMetaData.getAllFields();
                        for (int i = 0; i < fields.size(); ++i) {
                            fields.get(i).set(instance, rs.getObject(i + 1));
                        }
                        return instance;
                    }
                } catch (SQLException | IllegalAccessException throwables) {
                    logger.error(throwables.getMessage());
                    throw new UserJdbcMapperException(throwables);
                }
                return null;
            });
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new UserJdbcMapperException(e);
        }
        return result.orElse(null);
    }
}
