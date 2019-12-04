package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.SchoolSubject;
import by.epam.receptionenrollee.exception.DaoException;

import java.util.List;

/**
 * CRUD operations interface for SchoolSubject entity
 */
public interface SchoolSubjectDao {
    /**
     * Finds all schoolSubjects in Database
     * @return List of all schoolSubjects
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<SchoolSubject> findSchoolSubjects() throws DaoException;

    /**
     * Finds schoolSubject by schoolSubject id
     * @param id - SchoolSubject`s id
     * @return SchoolSubject
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    SchoolSubject findSchoolSubjectById(Integer id) throws DaoException;

    /**
     * Adds new schoolSubject to Database
     * @param schoolSubject - SchoolSubject to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertUser(SchoolSubject schoolSubject);

    /**
     * Updates schoolSubject in Database
     * @param schoolSubject - SchoolSubject to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateUser(SchoolSubject schoolSubject);

    /**
     * Deletes schoolSubject from Database
     * @param schoolSubject - SchoolSubject to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteUser(SchoolSubject schoolSubject);
}
