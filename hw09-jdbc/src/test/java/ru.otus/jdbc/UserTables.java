package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;

import java.sql.SQLException;

public class UserTables {
    private static final Logger logger = LoggerFactory.getLogger(UserTables.class);

    public static void createTables(SessionManager sessionManager) {
        logger.debug("sessionManager={}", sessionManager);

        var query = "create table if not exists User (id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
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

    public static void clearTable(SessionManager sessionManager) {
        logger.debug("sessionManager={}", sessionManager);

        var query = "TRUNCATE TABLE User";
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
