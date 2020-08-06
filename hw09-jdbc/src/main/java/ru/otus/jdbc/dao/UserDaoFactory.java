package ru.otus.jdbc.dao;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.*;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

public class UserDaoFactory implements DaoAbstractFactory<UserDao> {

    @Override
    public UserDao getInstance(DataSource dataSource) {
        SessionManager sessionManager = new SessionManagerJdbc(dataSource);
        EntityClassMetaData<User> classMetaData = new EntityClassMetaDataImlp<>(User.class);
        EntitySQLMetaData<User> sqlMetaData = new EntitySQLMetaDataImpl<>(classMetaData);
        DbExecutor<User> executor = new DbExecutorImpl<>();
        JdbcMapper<User> mapper = new JdbcMapperImpl<>(
                sessionManager,
                executor,
                classMetaData,
                sqlMetaData);
        return new UserDaoJdbc(mapper, sessionManager);
    }
}
