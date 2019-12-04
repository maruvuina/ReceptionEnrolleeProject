package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.SpecialityDao;
import by.epam.receptionenrollee.entity.Speciality;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.service.EducationInformation;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_FACULTY;
import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_SPECIALITY;

public class SpecialityDaoImpl extends AbstractDao<Speciality> implements SpecialityDao {
    private static final Logger logger = LogManager.getLogger(SpecialityDaoImpl.class);

    public SpecialityDaoImpl() {
        Mapper<Speciality, PreparedStatement> mapperToDatabase = (Speciality speciality, PreparedStatement preparedStatement) -> {
            //preparedStatement.setInt(1, speciality.getId());
            preparedStatement.setInt(1, speciality.getIdDepartment());
            preparedStatement.setString(2, speciality.getSpecialityName());
            preparedStatement.setInt(3, speciality.getPlan());
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, Speciality> mapperFromDatabase = (ResultSet resultSet, Speciality speciality) -> {
            speciality.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SPECIALITY));
            speciality.setIdDepartment(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_FACULTY_FK));
            speciality.setSpecialityName(resultSet.getString(ColumnLabel.COLUMN_LABEL_SPECIALITY_NAME));
            speciality.setPlan(resultSet.getInt(ColumnLabel.COLUMN_LABEL_PLAN));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<Speciality> findSpeciality() throws DaoException {
        return null;
    }

    @Override
    public Speciality findSpecialityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertSpeciality(Speciality speciality) {
        return false;
    }

    @Override
    public boolean updateSpeciality(Speciality speciality) {
        return false;
    }

    @Override
    public boolean deleteSpeciality(Speciality speciality) {
        return false;
    }

    @Override
    public int getSpecialityIdByName(String specialityName) throws DaoException {
        int idSpeciality = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_SPECIALITY_ID)) {
            preparedStatement.setString (1, specialityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idSpeciality = resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SPECIALITY);
            } else {
                throw new DaoException("Getting speciality ID failed by specialty name.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error while trying to get speciality id by name: ", e);
        }
        return idSpeciality;
    }

    @Override
    public EducationInformation getSpecialityNameFacultyNameBySpecialityId(int id) throws DaoException {
        EducationInformation educationInformation = new EducationInformation();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_SPECIALITY_FACULTY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                educationInformation.setIdEnrollee(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE));
                educationInformation.setFacultyName(resultSet.getString(ColumnLabel.COLUMN_LABEL_FACULTY_NAME));
                educationInformation.setSpecialityName(resultSet.getString(ColumnLabel.COLUMN_LABEL_SPECIALITY_NAME));
            } else {
                throw new DaoException("Getting specialty_name, faculty_name failed, no id_speciality obtained.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get faculty and speciality names: ", e);
        }
        return educationInformation;
    }

    @Override
    public int getFacultyPlan(String facultyName) throws DaoException {
        int facultyPlan = 0;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(SqlQuery.FIND_FACULTY_PLAN);
            preparedStatement.setString(1, facultyName);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    facultyPlan = resultSet.getInt(ColumnLabel.COLUMN_LABEL_FACULTY_PLAN);
                } else {
                    throw new DaoException("Getting faculty plan failed, no faculty name obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get faculty plan: ", e);
        }
        return facultyPlan;
    }
}
