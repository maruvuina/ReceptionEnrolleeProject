package by.epam.receptionenrollee.dao.pool;

import by.epam.receptionenrollee.dao.DatabaseProperties;
import by.epam.receptionenrollee.manager.DatabaseManager;
import by.epam.receptionenrollee.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public enum CustomConnectionPool {
    INSTANCE;

    private BlockingDeque<ProxyConnection> freeConnection;

    private Queue<ProxyConnection> givenAwayConnections;

    private static int DEFAULT_POOL_SIZE;

    private volatile boolean isPoolReleased = false;

    private static Logger logger;

    private static Logger logger() {
        if (logger == null) {
            logger = LogManager.getLogger(CustomConnectionPool.class);
        }
        return logger;
    }

    private static int getDefaultPoolSize() {
        DEFAULT_POOL_SIZE = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.MAX_ACTIVE));
        return DEFAULT_POOL_SIZE;
    }

    CustomConnectionPool() {
        try {
            Class.forName(DatabaseManager.getProperty(DatabaseManager.DRIVER_NAME));
            DatabaseProperties databaseProperty = new DatabaseProperties();
            Properties properties = databaseProperty.getDatabaseProperties();
            String url = DatabaseManager.getProperty(DatabaseManager.URL);
            freeConnection = new LinkedBlockingDeque<>(getDefaultPoolSize());
            givenAwayConnections = new ArrayDeque<>();
            for (int i = 0; i < getDefaultPoolSize(); i++) {
                Connection conn = DriverManager.getConnection(url, properties);
                ProxyConnection connection = new ProxyConnection(conn);
                freeConnection.offer(connection);
                givenAwayConnections.offer(connection);
            }
            if (freeConnection.size() != getDefaultPoolSize()) {
                logger().log(Level.FATAL, "Cannot create connection pool: not enough available/free connections created (expected: " +
                        getDefaultPoolSize() + ", actual: " + freeConnection.size() + ")");
                throw new RuntimeException("Not enough available/free connections created (expected: " +
                        getDefaultPoolSize() + ", actual: " + freeConnection.size() + ")");
            }
        } catch (SQLException e) {
            logger().log(Level.FATAL, "Cannot create connection pool: SQL Exception\n", e);
            throw new RuntimeException("Cannot create connection pool: ", e);
        } catch (ClassNotFoundException e) {
            logger().log(Level.ERROR, "Cannot found such class: ", e);
        }
    }

    /**
     * Provides main access to database available Connections
     * @return {@link ProxyConnection} to work with database
     * @throws PoolException if there is no free availableConnections in pool
     */
    public Connection getConnection() throws PoolException {
        if (!isPoolReleased) {
            try {
                ProxyConnection connection = freeConnection.take();
                givenAwayConnections.offer(connection);
                return connection;
            } catch (InterruptedException e) {
                logger.log(Level.WARN, "Free connection timeout is over.", e);
                throw new PoolException("Connection timeout is over.", e);
            }
        } else {
            logger.log(Level.WARN, "Trying to poll from realized pool.");
            throw new PoolException("Pool is already realized.");
        }
    }

    /**
     * Returns connection to pool to reusing it by another user,
     * ----
     * @param connection which will be returned to pool
     * @throws PoolException if cannot return connection, if connection created outside the pool, or if null passed.
     */
    public void releaseConnection(Connection connection) throws PoolException {
        if (connection == null) {
            logger().log(Level.WARN, "Someone trying to return null connection.");
            throw new PoolException("Null connection passed.");
        }
        if (connection.getClass().equals(ProxyConnection.class)) {
            givenAwayConnections.remove((ProxyConnection) connection);
            freeConnection.offer((ProxyConnection) connection);
        } else {
            logger().log(Level.WARN, "Someone trying to put connection which was created outside the pool: " + connection);
            throw new PoolException("Cannot put connection which was created outside the pool.");
        }
        deregisterDrivers();
    }

    public void destroyPool() {
        isPoolReleased = true;
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (InterruptedException e) {
                logger().log(Level.ERROR, "Cannot close connection: ", e);
            }
        }
        deregisterDrivers();
        logger().log(Level.INFO, "Connection pool successfully closed.");
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger().log(Level.ERROR, "Cannot deregister drivers: ", e);
            }
        });
    }
}
