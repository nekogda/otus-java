package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.UserDaoException;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final JdbcMapper<User> mapper;
    private final SessionManager sessionManager;

    public UserDaoJdbc(JdbcMapper<User> mapper, SessionManager sessionManager) {
        this.mapper = mapper;
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        var user = mapper.findById(id);
        return Optional.ofNullable(user);
    }

    @Override
    public long insertUser(User user) {
        try {
            return mapper.insert(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            mapper.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(User user) {
        try {
            mapper.insertOrUpdate(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
