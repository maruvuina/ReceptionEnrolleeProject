package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD operations interface for User entity
 */
public interface UserDao {
    /**
     * Finds all users in Database
     * @return List of all users
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<User> findUsers() throws DaoException;

    /**
     * Finds user by user id
     * @param id - User`s id
     * @return User
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserById(Integer id) throws DaoException;

    /**
     * Adds new user to Database
     * @param user - user to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertUser(User user) throws SQLException, DaoException;

    /**
     * Updates user in Database
     * @param user - User to update in Database
     * @return true if operation success and false if fails
     */
    User updateUser(User user) throws DaoException;

    /**
     * Deletes user from Databasex`
     * @param user - User to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteUser(User user) throws DaoException;

    /**
     * Find user by email
     * @param login - User`s email
     * @return User
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    User findUserByEmail(String login) throws DaoException;
}

