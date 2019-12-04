package by.epam.receptionenrollee.dao.manager;

import java.util.ResourceBundle;

/**
 * Class which provides handy access to database properties
 */
public class DatabaseManager {

    /**
     * Represents name of JDBC Driver of database used
     */
    public static final String DRIVER_NAME = "driver";

    /**
     * Represents connection url to database
     */
    public static final String URL = "url";

    /**
     * Represents username of user to access to database
     */
    public static final String USER = "user";

    /**
     * Represents password of user to access to database
     */
    public static final String PASSWORD = "password";

    /**
     * Represents maximum amount of connection which are created and used in web app
     */
    //POOL_SIZE
    public static final String MAX_ACTIVE = "maxActive";

    /**
     * Represents maximum number of connections that should be kept in the pool at all times
     */
    public static final String MAX_IDLE = "maxIdle";


    /**
     * Represents maximum time in milliseconds which the pool will wait (when there are no available connections) for a connection to be returned before throwing an exception
     */
    public static final String MAX_WAIT_MILLIS = "maxWait";

    /**
     * Represents -------------
     */
    public static final String ALLOW_PUBLIC_KEY_RETRIEVAL = "allowPublicKeyRetrieval";

    /**
     * Represents -------------
     */
    public static final String USE_SSL = "useSSL";

    /**
     * Represents if database use unicode (see {@link #ENCODING})
     */
    public static final String USE_UNICODE = "useUnicode";

    /**
     * Represents database encoding
     */
    public static final String ENCODING = "characterEncoding";

    /**
     * Represents if JDBC need to automatically reconnect to database
     */
    public static final String AUTORECONNECT = "autoReconnect";

    /**
     * Represents -----
     */
    public static final String USE_JDBC_COMPLIANT_TIMEZONE_SHIFT = "useJDBCCompliantTimezoneShift";

    /**
     * Represents -----
     */
    public static final String USE_LEGACY_DATETIME_CODE = "useLegacyDatetimeCode";

    /**
     * Represents -----
     */
    public static final String SERVER_TIMEZONE = "serverTimezone";

    public static final String RESOURCE_NAME = "database";
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private DatabaseManager() {}

    /**
     * Returns database property value with specified key
     * @param key key of the property, recommended to use constants.
     * @return value of property with the specified key
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
