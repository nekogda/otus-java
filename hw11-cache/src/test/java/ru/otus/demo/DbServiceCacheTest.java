package ru.otus.demo;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Демо работы с hibernate через кэш должно ")
public class DbServiceCacheTest {
    public static final Logger logger = LoggerFactory.getLogger(DbServiceCacheTest.class);
    private DBServiceUser dbServiceUser;
    UserDao userDao = null;
    private final long USER_ID = 1L;

    @BeforeEach
    public void setUp() {
        logger.info("setUp called");
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml",
                User.class,
                AddressDataSet.class,
                PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao, new MyCache<>());
    }

    @AfterEach
    void tearDown() {
        logger.info("tearDown called");
    }


    @Test
    @DisplayName(" корректно загружать пользователя из кэша по заданному id ")
    void shouldLoadCorrectUserByIdFromCache() {

        User expectedUser = new User(USER_ID, "Вася", new AddressDataSet("пр-т Ленина 1"),
                Arrays.asList(new PhoneDataSet("111-222"), new PhoneDataSet("222-333")));

        var userId = dbServiceUser.saveUser(expectedUser);

        // update user bypassing cache
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(new User(USER_ID, "Вася", new AddressDataSet("пр-т Ленина 2"),
                        Arrays.asList(new PhoneDataSet("444-555"), new PhoneDataSet("666-777"))));
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }

        var mayBeUser = dbServiceUser.getUser(userId);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }
}
