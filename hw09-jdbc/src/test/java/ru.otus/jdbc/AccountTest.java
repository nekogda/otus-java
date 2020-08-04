package ru.otus.jdbc;

import org.junit.jupiter.api.*;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.*;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JdbcMapper must")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountTest {
    private final DataSource dataSource = new DataSourceH2();
    private final SessionManager sessionManager = new SessionManagerJdbc(dataSource);
    private JdbcMapperImpl<Account> mapper;

    @BeforeAll
    void setup() {
        EntityClassMetaData<Account> classMetaData = new EntityClassMetaDataImlp<>(Account.class);
        EntitySQLMetaData<Account> sqlMetaData = new EntitySQLMetaDataImpl<>(classMetaData);
        DbExecutor<Account> executor = new DbExecutorImpl<>();
        mapper = new JdbcMapperImpl<>(
                sessionManager,
                executor,
                classMetaData,
                sqlMetaData);

        AccountTables.createTables(sessionManager);
    }

    @AfterEach
    void teardown() {
        AccountTables.clearTables(sessionManager);
    }

    @Test
    @DisplayName("insert Account")
    void AccountInsertTest() {
        Account account = new Account(0, "type", BigDecimal.valueOf(12));
        Account result;
        sessionManager.beginSession();
        try (sessionManager) {
            var id = mapper.insert(account);
            account.setNo(id);
            result = mapper.findById(id);
            sessionManager.commitSession();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(account, result);
    }

    @Test
    @DisplayName("update Account")
    void AccountUpdateTest() {
        Account account = new Account(0, "type", BigDecimal.valueOf(12));
        Account result;
        sessionManager.beginSession();
        try (sessionManager) {
            var id = mapper.insert(account);
            account.setNo(id);
            account.setType("update");
            mapper.update(account);
            result = mapper.findById(id);
            sessionManager.commitSession();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(account.getType(), result.getType());
    }

    @Test
    @DisplayName("find Account by id")
    void AccountFindTest() {
        Account account = new Account(0, "type", BigDecimal.valueOf(12));
        Account result;
        sessionManager.beginSession();
        try (sessionManager) {
            var id = mapper.insert(account);
            result = mapper.findById(id);
            sessionManager.commitSession();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(account, result);
    }

    @Test
    @DisplayName("update Account")
    void AccountInsertOrUpdateTest() {
        Account account = new Account(0, "type", BigDecimal.valueOf(12));
        Account result;
        sessionManager.beginSession();
        try (sessionManager) {
            var id = mapper.insert(account);
            account.setNo(id);
            account.setType("update");
            mapper.insertOrUpdate(account);
            result = mapper.findById(id);
            sessionManager.commitSession();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(account, result);
    }

    @Test
    @DisplayName("insert Account")
    void AccountUpdateOrInsertTest2() {
        Account account = new Account(0, "type", BigDecimal.valueOf(12));
        long id;
        sessionManager.beginSession();
        try (sessionManager) {
            id = mapper.insertOrUpdate(account);
            sessionManager.commitSession();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e.getMessage());
        }
        assertEquals(1, id);
    }
}
