package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;

import java.sql.SQLException;

public class AccountTables {
    private static final Logger logger = LoggerFactory.getLogger(AccountTables.class);

    private AccountTables() {
    }

    public static void createTables(SessionManager sessionManager) {
        var query = "create table if not exists Account (no long(20) NOT NULL auto_increment, type varchar(255), rest number)";

        sessionManager.beginSession();
        var connection = sessionManager.getCurrentSession().getConnection();
        try (sessionManager;
             var pst = connection.prepareStatement(query)) {
            pst.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
            throw new IllegalStateException("can't create table", throwables);
        }
    }

    public static void clearTables(SessionManager sessionManager) {
        var query = "TRUNCATE TABLE Account";
        sessionManager.beginSession();
        var connection = sessionManager.getCurrentSession().getConnection();
        try (sessionManager;
             var pst = connection.prepareStatement(query)) {
            pst.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
            throw new IllegalStateException("can't clear table", throwables);
        }
    }
}
