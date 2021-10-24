package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.UserDao;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.service.EducationInformation;
import by.epam.receptionenrollee.sql.SqlQuery;
import by.epam.receptionenrollee.util.HashUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    Mapper<User, PreparedStatement> mapperToDatabase = (User user, PreparedStatement preparedStatement) -> {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getMiddleName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setString(6, String.valueOf(user.getRoleEnum()));
    };

    Mapper<ResultSet, User> mapperFromDatabase = (ResultSet resultSet, User user) -> {
        user.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_USER));
        user.setFirstName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FIRST_NAME));
        user.setLastName(resultSet.getString(ColumnLabel.COLUMN_LABEL_LAST_NAME));
        user.setMiddleName(resultSet.getString(ColumnLabel.COLUMN_LABEL_MIDDLE_NAME));
        user.setEmail(resultSet.getString(ColumnLabel.COLUMN_LABEL_EMAIL));
        user.setPassword(resultSet.getString(ColumnLabel.COLUMN_LABEL_PASSWORD));
        user.setRoleEnum(RoleEnum.valueOf(resultSet.getString(ColumnLabel.COLUMN_LABEL_ROLE).toUpperCase()));
    };

    public UserDaoImpl() {
        super.setMapperToDatabase(mapperToDatabase);
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<User> findUsers() throws DaoException {
        return findAll(User.class, SqlQuery.FIND_ALL_USERS);
    }

    @Override
    public User findUserById(Integer id) throws DaoException {
        return findByValue(User.class, SqlQuery.FIND_USER_BY_ID, id);
    }

    @Override
    public boolean insertUser(User user) throws DaoException {
        return insert(user, SqlQuery.USER_INSERT);
    }

    @Override
    public User updateUser(User user) throws DaoException {
        return update(user, SqlQuery.USER_UPDATE, 7, user.getId());
    }

    @Override
    public boolean deleteUser(User user) throws DaoException {
        return deleteByValue(SqlQuery.USER_DELETE, user.getEmail());
    }

    @Override
    public User findUserByEmail(String email) throws DaoException {
        return findByValue(User.class, SqlQuery.FIND_USER_BY_EMAIL, email);
    }

    public User findUserByLoginPassword(String email, String password) throws DaoException {
        User user = findUserByEmail(email);
        if (HashUtil.isValidHash(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean isUserEmailUnique(String email) throws DaoException {
        boolean isUnique = true;
        try(var preparedStatement = connection.prepareStatement(SqlQuery.FIND_USER_ID_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isUnique = false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying checking user email uniqueness: ", e);
            throw new DaoException(e);
        }
        return isUnique;
    }
    
    public User findUserFirstLastNameEmailByUserId(int idUser) throws DaoException {
        User user = new User();
        try(var preparedStatement = connection.prepareStatement(SqlQuery.FIND_USER_FIRST_LAST_NAME_EMAIL_BY_USER_ID)) {
            preparedStatement.setInt(1, idUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setFirstName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FIRST_NAME));
                user.setLastName(resultSet.getString(ColumnLabel.COLUMN_LABEL_LAST_NAME));
                user.setEmail(resultSet.getString(ColumnLabel.COLUMN_LABEL_EMAIL));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error while trying find user first, last name, email by user id: ", e);
            throw new DaoException(e);
        }
        return user;
    }

    public EducationInformation findFirstLastNameSpecialityFacultyByEmail(String email) throws DaoException {
        EducationInformation educationInformation = new EducationInformation();
        try(var preparedStatement = connection.prepareStatement(SqlQuery.FIND_FIRST_LAST_NAME_SPECIALITY_FACULTY_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                educationInformation.setEnrolleeFirstName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FIRST_NAME));
                educationInformation.setEnrolleeLastName(resultSet.getString(ColumnLabel.COLUMN_LABEL_LAST_NAME));
                educationInformation.setSpecialityName(resultSet.getString(ColumnLabel.COLUMN_LABEL_SPECIALITY_NAME));
                educationInformation.setFacultyName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FACULTY_NAME));
            }
        }
        catch (SQLException e) {
            logger.log(Level.ERROR, "Error while trying find first, last name, speciality, faculty name by email: ", e);
            throw new DaoException(e);
        }
        return educationInformation;
    }

    public User updateUserFullName(User user) throws DaoException {
        try(var preparedStatement =
                    connection.prepareStatement(SqlQuery.USER_UPDATE_FULL_NAME)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getMiddleName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.executeUpdate();
            user = findUserByEmail(user.getEmail());
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to update row in database: ", e);
            throw new DaoException(e);
        }
        return user;
    }
}
