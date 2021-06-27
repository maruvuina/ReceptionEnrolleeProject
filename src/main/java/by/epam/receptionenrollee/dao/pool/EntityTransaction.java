package by.epam.receptionenrollee.dao.pool;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private static final Logger logger = LogManager.getLogger(EntityTransaction.class);
    private Connection connection;

    public void begin(AbstractDao dao, AbstractDao ... daos) {
        if (connection == null) {
            try {
                connection = CustomConnectionPool.INSTANCE.getConnection();
            } catch (PoolException e) {
                logger.log(Level.ERROR, "Get connection is failed: ", e);
            }
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Set auto commit is failed: ", e);
            }
            dao.setConnection(connection);
            for (AbstractDao daoElement : daos) {
                daoElement.setConnection(connection);
            }
        }
    }

    public void end() {
        ProxyConnection proxyConnection = new ProxyConnection(connection);
        try {
            proxyConnection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Set auto commit is failed: ", e);
        }
        proxyConnection.close();
        connection = null;
    }

    public void commit() {
        ProxyConnection proxyConnection = new ProxyConnection(connection);
        try {
            proxyConnection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Commit is failed: ", e);
        }
    }

    public void rollback() {
        ProxyConnection proxyConnection = new ProxyConnection(connection);
        try {
            proxyConnection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Rollback is failed: ", e);
        }
    }
}
