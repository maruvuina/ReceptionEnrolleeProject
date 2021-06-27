package by.epam.receptionenrollee.factory;

import by.epam.receptionenrollee.manager.DatabaseManager;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
@Deprecated
public class MySqlDataSourceFactory {
    private static final Logger logger = LogManager.getLogger(MySqlDataSourceFactory.class);

    public static MysqlDataSource createMysqlDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setURL(DatabaseManager.getProperty(DatabaseManager.URL));
            dataSource.setUser(DatabaseManager.getProperty(DatabaseManager.USER));
            dataSource.setPassword(DatabaseManager.getProperty(DatabaseManager.PASSWORD));
            dataSource.setInitialTimeout(Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.MAX_WAIT_MILLIS)));
            dataSource.setAllowPublicKeyRetrieval(Boolean.parseBoolean(DatabaseManager.getProperty(DatabaseManager.ALLOW_PUBLIC_KEY_RETRIEVAL)));
            dataSource.setUseSSL(Boolean.parseBoolean(DatabaseManager.getProperty(DatabaseManager.USE_SSL)));
            dataSource.setCharacterEncoding(DatabaseManager.getProperty(DatabaseManager.ENCODING));
            dataSource.setAutoReconnect(Boolean.parseBoolean(DatabaseManager.getProperty(DatabaseManager.AUTORECONNECT)));
            dataSource.setServerTimezone(DatabaseManager.getProperty(DatabaseManager.SERVER_TIMEZONE));
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error while get data source: ", e);
        }
        return dataSource;
    }
}
