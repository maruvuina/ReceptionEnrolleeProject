package by.epam.receptionenrollee.dao.pool;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
@Deprecated
public class StandardConnectionPool {
    private static final Logger logger = LogManager.getLogger(StandardConnectionPool.class);
    private static final String DATASOURCE_NAME = "jdbc/reception_enrollee_system";
    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            logger.log(Level.ERROR, "Get something wrong with connection pool: ", e);
        }
    }

    private StandardConnectionPool() {}

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Get something wrong with get connection: ", e);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Get something wrong with close connection: ", e);
            }
        }
    }
}
