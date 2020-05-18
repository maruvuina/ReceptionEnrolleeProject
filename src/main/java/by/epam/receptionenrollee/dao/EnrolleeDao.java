package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD operations interface for Enrollee entity
 */
public interface EnrolleeDao {
    /**
     * Finds all enrollees in Database
     * @return List of all enrollees
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Enrollee> findEnrollees() throws DaoException;

    /**
     * Finds enrollee by enrollee id
     * @param id - Enrollee`s id
     * @return Enrollee
     */
    Enrollee findEnrolleeById(Integer id) throws DaoException;

    /**
     * Adds new enrollee to Database
     * @param enrollee - Enrollee to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertEnrollee(Enrollee enrollee) throws SQLException, DaoException;

    /**
     * Updates enrollee in Database
     * @param enrollee - User to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateEnrollee(Enrollee enrollee);

    /**
     * Deletes enrollee from Database
     * @param enrollee - Enrollee to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteEnrollee(Enrollee enrollee);

    /**
     * Finds enrollee id by user id
     * @param userId - User`s id
     * @return int
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    int findEnrolleeIdByUserId(int userId) throws DaoException;

    /**
     * Finds enrollees id by faculty name
     * @param facultyName - Faculty name
     * @return List of required enrollee ids
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Integer> findEnrolleesIdByFacultyName(String facultyName) throws DaoException;
}
