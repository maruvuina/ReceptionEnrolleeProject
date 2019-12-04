package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.Speciality;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.service.EducationInformation;

import java.util.List;
import java.util.Map;

/**
 * CRUD operations interface for Speciality entity
 */
public interface SpecialityDao {
    /**
     * Finds all specialities in Database
     * @return List of all specialities
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Speciality> findSpeciality() throws DaoException;

    /**
     * Finds speciality by user id
     * @param id - Speciality`s id
     * @return speciality
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Speciality findSpecialityById(Integer id) throws DaoException;

    /**
     * Adds new speciality to Database
     * @param speciality - Speciality to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertSpeciality(Speciality speciality);

    /**
     * Updates speciality in Database
     * @param speciality - Speciality to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateSpeciality(Speciality speciality);

    /**
     * Deletes speciality from Database
     * @param speciality - Speciality to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteSpeciality(Speciality speciality);

    /**
     * Finds speciality id by its name
     * @param specialityName - Required speciality name
     * @return int
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    int getSpecialityIdByName(String specialityName) throws DaoException;

    /**
     * Finds faculty and speciality name by concrete speciality id
     * @param id - Speciality id
     * @return EducationInformation of concrete enrollee faculty and speciality names
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
     EducationInformation getSpecialityNameFacultyNameBySpecialityId(int id) throws DaoException;

    /**
     * Finds student enrollment plan by faculty name
     * @param facultyName - Faculty name
     * @return int
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    int getFacultyPlan(String facultyName) throws DaoException;
}
