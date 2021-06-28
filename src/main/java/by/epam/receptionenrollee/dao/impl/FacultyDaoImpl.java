package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.FacultyDao;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.entity.Faculty;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.service.EducationInformation;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FacultyDaoImpl extends AbstractDao<Faculty> implements FacultyDao {
    private static final Logger logger = LogManager.getLogger(FacultyDaoImpl.class);

    public FacultyDaoImpl() {
        Mapper<Faculty, PreparedStatement> mapperToDatabase = (Faculty faculty, PreparedStatement preparedStatement) -> {
            preparedStatement.setString(1, faculty.getFacultyName());
            preparedStatement.setString(2, faculty.getFirstExam());
            preparedStatement.setString(3, faculty.getSecondExam());
            preparedStatement.setString(4, faculty.getThridExam());
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, Faculty> mapperFromDatabase = (ResultSet resultSet, Faculty faculty) -> {
            faculty.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_FACULTY ));
            faculty.setFacultyName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FACULTY_NAME));
            faculty.setFirstExam(resultSet.getString(ColumnLabel.COLUMN_LABEL_TEST_ONE));
            faculty.setSecondExam(resultSet.getString(ColumnLabel.COLUMN_LABEL_TEST_TWO));
            faculty.setThridExam(resultSet.getString(ColumnLabel.COLUMN_LABEL_TEST_THREE));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<Faculty> findFaculties() throws DaoException {
        return null;
    }

    @Override
    public Faculty findFacultyById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertFaculty(Faculty faculty) {
        return false;
    }

    @Override
    public boolean updateFaculty(Faculty faculty) {
        return false;
    }

    @Override
    public boolean deleteFaculty(Faculty faculty) {
        return false;
    }

    public String getFacultyNameByEnrolleeSpecialityId(int idSpeciality) throws DaoException {
        String facultyName = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_FACULTY_NAME_BY_ENROLLEE_ID_SPECIALITY)) {
            preparedStatement.setInt(1, idSpeciality);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                facultyName = resultSet.getString(ColumnLabel.COLUMN_LABEL_FACULTY_NAME);
            } else {
                throw new DaoException("Getting faculty_name failed, no id_speciality obtained.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get faculty and speciality names: ", e);
        }
        return facultyName;
    }

    public EducationInformation getSpecialityNameFacultyNameBySpecialityId(int id) throws DaoException {
        EducationInformation educationInformation = new EducationInformation();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_SPECIALITY_FACULTY_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                educationInformation.setFacultyName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FACULTY_NAME));
                educationInformation.setSpecialityName(resultSet.getString(ColumnLabel.COLUMN_LABEL_SPECIALITY_NAME));
            } else {
                throw new DaoException("Getting speciality_name, faculty_name failed, no id_user obtained.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get faculty and speciality names: ", e);
        }
        return educationInformation;
    }
}
