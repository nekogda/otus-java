package ru.otus.jdbc;

import org.junit.jupiter.api.*;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.dao.FactoryProvider;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DbService must")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbServiceTest {
    private UserDao userDao;

    @BeforeAll
    void setup() {
        DataSource dataSource = new DataSourceH2();
        userDao = (UserDao) FactoryProvider
                .getFactory("User")
                .getInstance(dataSource);
        UserTables.createTables(userDao.getSessionManager());
    }

    @AfterEach
    void teardown() {
        UserTables.clearTable(userDao.getSessionManager());
    }

    @Test
    @DisplayName("save User instance")
    void UserServiceTest() {
        var dbServiceUser = new DbServiceUserImpl(userDao);

        var user = new User(0, "Mary", 42);
        var id = dbServiceUser.saveUser(user);
        Optional<User> response = dbServiceUser.getUser(id);
        assertEquals(user, response.get());
    }
}
