package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;
    private HwCache<String, User> cache = null;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public DbServiceUserImpl(UserDao userDao, HwCache<String, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                long userId = user.getId();
                sessionManager.commitSession();
                logger.info("created user: {}", userId);
                cachePut(String.valueOf(userId), user);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        var cachedUser = cacheGet(String.valueOf(id));

        if (cachedUser.isPresent()) {
            return cachedUser;
        }

        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id).flatMap(
                        (user) -> {
                            cachePut(String.valueOf(user.getId()), user);
                            return Optional.of(user);
                        });
                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    private Optional<User> cacheGet(String id) {
        if (cache == null) {
            return Optional.empty();
        }
        var user = cache.get(id);
        return Optional.ofNullable(user);
    }

    private void cachePut(String id, User user) {
        if (cache != null) {
            cache.put(id, user);
        }
    }
}
