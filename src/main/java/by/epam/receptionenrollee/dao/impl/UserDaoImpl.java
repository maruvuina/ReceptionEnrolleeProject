package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.UserDao;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import by.epam.receptionenrollee.validator.HashUtil;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        Mapper<User, PreparedStatement> mapperToDatabase = (User user, PreparedStatement preparedStatement) -> {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getMiddleName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, String.valueOf(user.getRoleEnum()));
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, User> mapperFromDatabase = (ResultSet resultSet, User user) -> {
            user.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_USER));
            user.setFirstName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FIRST_NAME));
            user.setLastName(resultSet.getString(ColumnLabel.COLUMN_LABEL_LAST_NAME));
            user.setMiddleName(resultSet.getString(ColumnLabel.COLUMN_LABEL_MIDDLE_NAME));
            user.setEmail(resultSet.getString(ColumnLabel.COLUMN_LABEL_EMAIL));
            user.setPassword(resultSet.getString(ColumnLabel.COLUMN_LABEL_PASSWORD));
            user.setRoleEnum(RoleEnum.valueOf(resultSet.getString(ColumnLabel.COLUMN_LABEL_ROLE).toUpperCase()));
        };
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
        return update(user, SqlQuery.USER_UPDATE, 4, user.getId());
    }

    @Override
    public boolean deleteUser(User user) throws DaoException {
        return deleteByValue(SqlQuery.USER_DELETE, user.getEmail());
    }

//    public RoleEnum getRoleByLoginPassword(String login, String password) {
//        RoleEnum result = RoleEnum.UNKNOWN;
//        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_ROLE_BY_LOGIN_PASSWORD);
//            ResultSet resultSet = preparedStatement.executeQuery()) {
//            List<User> users = new ArrayList<>();
//            while (resultSet.next()) {
//                String userNameDatabase = resultSet.getString(ColumnLabel.COLUMN_LABEL_EMAIL);
//                String passwordDatabase = resultSet.getString(ColumnLabel.COLUMN_LABEL_PASSWORD);
//                RoleEnum roleEnumDatabase = RoleEnum.valueOf(resultSet.getString(ColumnLabel.COLUMN_LABEL_ROLE).toUpperCase());
//                users.add(new User(userNameDatabase, passwordDatabase, roleEnumDatabase));
//            }
//            for (User user : users) {
//                if (login.equals(user.getEmail()) && HashUtil.isValidHash(password, user.getPassword())) {
//                    result = user.getRoleEnum();
//                }
//            }
//        }
//        catch (SQLException e) {
//            logger.log(Level.ERROR, "Error while trying get role by login and password: ", e);
//        }
//        return result;
//    }

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

    public boolean isUserEmailUnique(String email) {
        boolean isUnique = true;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_USER_ID_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isUnique = false;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying checking user email uniqueness: ", e);
        }
        return isUnique;
    }
}
