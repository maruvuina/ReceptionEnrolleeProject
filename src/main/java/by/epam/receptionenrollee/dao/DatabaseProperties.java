package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.dao.manager.DatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    public Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.put(DatabaseManager.USER, DatabaseManager.getProperty(DatabaseManager.USER));
        properties.put(DatabaseManager.PASSWORD, DatabaseManager.getProperty(DatabaseManager.PASSWORD));
        properties.put(DatabaseManager.ALLOW_PUBLIC_KEY_RETRIEVAL, DatabaseManager.getProperty(DatabaseManager.ALLOW_PUBLIC_KEY_RETRIEVAL));
        properties.put(DatabaseManager.USE_SSL, DatabaseManager.getProperty(DatabaseManager.USE_SSL));
        properties.put(DatabaseManager.USE_UNICODE, DatabaseManager.getProperty(DatabaseManager.USE_UNICODE));
        properties.put(DatabaseManager.ENCODING, DatabaseManager.getProperty(DatabaseManager.ENCODING));
        properties.put(DatabaseManager.AUTORECONNECT, DatabaseManager.getProperty(DatabaseManager.AUTORECONNECT));
        properties.put(DatabaseManager.USE_JDBC_COMPLIANT_TIMEZONE_SHIFT, DatabaseManager.getProperty(DatabaseManager.USE_JDBC_COMPLIANT_TIMEZONE_SHIFT));
        properties.put(DatabaseManager.USE_LEGACY_DATETIME_CODE, DatabaseManager.getProperty(DatabaseManager.USE_LEGACY_DATETIME_CODE));
        properties.put(DatabaseManager.SERVER_TIMEZONE, DatabaseManager.getProperty(DatabaseManager.SERVER_TIMEZONE));
        return properties;
    }
}
