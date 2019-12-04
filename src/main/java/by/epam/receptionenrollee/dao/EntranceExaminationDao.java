package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.EntranceExamination;
import by.epam.receptionenrollee.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD operations interface for EntranceExamination entity
 */
public interface EntranceExaminationDao {
    /**
     * Finds all entranceExaminations in Database
     * @return List of all entranceExaminations
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<EntranceExamination> findEntranceExaminations() throws DaoException;

    /**
     * Finds entranceExamination by entranceExamination id
     * @param id - EntranceExamination`s id
     * @return EntranceExamination
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    EntranceExamination findEntranceExaminationById(Integer id) throws DaoException;

    /**
     * Adds new entranceExamination to Database
     * @param entranceExamination - EntranceExamination to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertEntranceExamination(EntranceExamination entranceExamination) throws SQLException, DaoException;

    /**
     * Updates entranceExamination in Database
     * @param entranceExamination - EntranceExamination to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateEntranceExamination(EntranceExamination entranceExamination);

    /**
     * Deletes entranceExamination from Database
     * @param entranceExamination - EntranceExamination to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteEntranceExamination(EntranceExamination entranceExamination);

    /**
     * Finds sum score examination by enrollee id
     * @param id - Enrollee id
     * @return int
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    int getSumExaminationByEnrolleeId(int id) throws DaoException;
}
