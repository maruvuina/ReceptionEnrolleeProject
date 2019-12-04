package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.Faculty;
import by.epam.receptionenrollee.exception.DaoException;

import java.util.List;

/**
 * CRUD operations interface for Faculty entity
 */
public interface FacultyDao {
    /**
     * Finds all faculties in Database
     * @return List of all faculties
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Faculty> findFaculties() throws DaoException;

    /**
     * Finds faculty by faculty id
     * @param id - Faculty`s id
     * @return Faculty
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Faculty findFacultyById(Integer id) throws DaoException;

    /**
     * Adds new faculty to Database
     * @param faculty - Faculty to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertFaculty(Faculty faculty);

    /**
     * Updates faculty in Database
     * @param faculty - Faculty to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateFaculty(Faculty faculty);

    /**
     * Deletes faculty from Database
     * @param faculty - Faculty to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteFaculty(Faculty faculty);
}
