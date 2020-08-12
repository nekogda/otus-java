package ru.otus.jdbc.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.AbstractHibernateTest;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с пользователями должно ")
class UserDaoHibernateTest extends AbstractHibernateTest {

    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @Test
    @DisplayName(" корректно загружать пользователя по заданному id")
    void shouldFindCorrectUserById() {
        User expectedUser = buildDefaultUser();
        saveUser(expectedUser);

        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @DisplayName(" корректно сохранять пользователя")
    @Test
    void shouldCorrectSaveUser() {
        User expectedUser = buildDefaultUser();
        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(expectedUser);
        long id = expectedUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        User actualUser = loadUser(id);
        assertThat(actualUser).isNotNull()
                .hasFieldOrPropertyWithValue("name", expectedUser.getName())
                .hasFieldOrPropertyWithValue("address", expectedUser.getAddress())
                .hasFieldOrPropertyWithValue("phones", expectedUser.getPhones());

        expectedUser = new User(id, TEST_USER_NEW_NAME,
                new AddressDataSet(TEST_ADDRESS_NEW_STREET),
                Arrays.asList(
                        new PhoneDataSet("333-444"),
                        new PhoneDataSet("444-555")));

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(expectedUser);
        long newId = expectedUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(newId).isGreaterThan(0).isEqualTo(id);
        actualUser = loadUser(newId);

        assertThat(actualUser).isNotNull()
                .hasFieldOrPropertyWithValue("name", expectedUser.getName())
                .hasFieldOrPropertyWithValue("address", expectedUser.getAddress())
                .hasFieldOrPropertyWithValue("phones", expectedUser.getPhones());

    }

    @DisplayName(" возвращать менеджер сессий")
    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }

}
