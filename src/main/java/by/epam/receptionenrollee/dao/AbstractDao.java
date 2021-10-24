package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.Entity;
import by.epam.receptionenrollee.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger(AbstractDao.class);
    protected Connection connection;
    protected AbstractDao() {}

    private Mapper<T, PreparedStatement> mapperToDatabase;

    private Mapper<ResultSet, T> mapperFromDatabase;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected void setMapperToDatabase(Mapper<T, PreparedStatement> mapperToDatabase) {
        this.mapperToDatabase = mapperToDatabase;
    }

    protected void setMapperFromDatabase(Mapper<ResultSet, T> mapperFromDatabase) {
        this.mapperFromDatabase = mapperFromDatabase;
    }

    protected List<T> findAll(Class clazz, String sqlGetAllQuery) throws DaoException {
        List<T> items = new ArrayList<>();
        try(var preparedStatement = connection.prepareStatement(sqlGetAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                T item = getItemInstance(clazz);
                mapperFromDatabase.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException e) {
           logger.log(Level.ERROR,"Error while trying find rows in database: ", e);
           throw new DaoException(e);
        }
        return items;
    }

    protected <V> List<T> findAllByValue(Class clazz, String sqlSelectByParameter, V value) throws DaoException {
        List<T> items = new ArrayList<>();
        try(var preparedStatement = connection.prepareStatement(sqlSelectByParameter)) {
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T item = getItemInstance(clazz);
                mapperFromDatabase.map(resultSet, item);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying find rows in database: ", e);
            throw new DaoException(e);
        }
        return items;
    }

    protected <V> T findByValue(Class clazz, String sqlSelectByParameter, V value) throws DaoException {
        T item = getItemInstance(clazz);
        try(var preparedStatement = connection.prepareStatement(sqlSelectByParameter)) {
            addParameterToPreparedStatement(preparedStatement, 1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mapperFromDatabase.map(resultSet, item);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to find entity in database: ", e);
            throw new DaoException(e);
        }
        return item;
    }

    protected <V> boolean deleteByValue(String sqlDeleteQuery, V value) throws DaoException {
        boolean result;
        try(var preparedStatement = connection.prepareStatement(sqlDeleteQuery)) {
            addParameterToPreparedStatement(preparedStatement, 1, value);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to delete row in database: ", e);
            throw new DaoException(e);
        }
        return result;
    }

    protected boolean insert(T entity, String sqlInsertQuery) throws DaoException {
        boolean result;
        try(var preparedStatement = connection.prepareStatement(sqlInsertQuery, Statement.RETURN_GENERATED_KEYS)) {
            mapperToDatabase.map(entity, preparedStatement);
            int affectedRows = preparedStatement.executeUpdate();
            if(result = affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Getting user ID failed by getGeneratedKeys() function, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to insert row in database: ", e);
            throw new DaoException(e);
        }
        return result;
    }

    protected <V> T update(T entity, String sqlUpdateQuery, Integer paramNum, V value) throws DaoException {
        T result = null;
        try(var preparedStatement = connection.prepareStatement(sqlUpdateQuery)) {
            mapperToDatabase.map(entity, preparedStatement);
            addParameterToPreparedStatement(preparedStatement, paramNum, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = getItemInstance(entity.getClass());
                mapperFromDatabase.map(resultSet, result);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to update row in database: ", e);
            throw new DaoException(e);
        }
        return result;
    }

    /** Private method witch returns a concrete instance of entity */
    @SuppressWarnings("unchecked")
    private T getItemInstance(Class clazz) {
        T item = null;
        try {
            item = (T) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            logger.log(Level.ERROR,"Error while get a concrete instance on entity: ", e);
        }
        return item;
    }

    private <V> void addParameterToPreparedStatement(PreparedStatement preparedStatement, Integer paramNum, V value)
            throws SQLException {
        if (value instanceof String) {
            preparedStatement.setString(paramNum, (String) value);
        }
        if (value instanceof Integer) {
            preparedStatement.setInt(paramNum, (Integer) value);
        }
        if (value instanceof Boolean) {
            preparedStatement.setBoolean(paramNum, (Boolean) value);
        }
    }
}
