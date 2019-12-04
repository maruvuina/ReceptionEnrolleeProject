package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.SchoolMark;
import by.epam.receptionenrollee.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD operations interface for SchoolMark entity
 */
public interface SchoolMarkDao {
    /**
     * Finds all schoolMarks in Database
     * @return List of all schoolMarks
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<SchoolMark> findSchoolMarks() throws DaoException;

    /**
     * Finds user by schoolMark id
     * @param id - SchoolMark`s id
     * @return SchoolMark
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    SchoolMark findSchoolMarkById(Integer id) throws DaoException;

    /**
     * Adds new schoolMark to Database
     * @param schoolMark - SchoolMark to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertSchoolMark(SchoolMark schoolMark) throws SQLException, DaoException;

    /**
     * Updates schoolMark in Database
     * @param schoolMark - SchoolMark to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateSchoolMark(SchoolMark schoolMark);

    /**
     * Deletes schoolMark from Database
     * @param schoolMark - SchoolMark to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteSchoolMark(SchoolMark schoolMark);

    /**
     * Finds average school ball by enrollee id
     * @param id - Enrollee id
     * @return double
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    double getAvgSchoolMarkByEnrolleeId(int id) throws DaoException;
}
