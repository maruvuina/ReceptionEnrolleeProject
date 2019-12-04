package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.EnrolleeDao;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolleeDaoImpl extends AbstractDao<Enrollee> implements EnrolleeDao {
    private static final Logger logger = LogManager.getLogger(EnrolleeDaoImpl.class);

    private Mapper<ResultSet, Enrollee> mapperFromDatabase = (ResultSet resultSet, Enrollee enrollee) -> {
        enrollee.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE));
        enrollee.setIdUser(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_USER_FK));
        enrollee.setIdSpeciality(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SPECIALITY_FK));
        enrollee.setBirthday(String.valueOf(resultSet.getDate(ColumnLabel.COLUMN_LABEL_BIRTHDAY)));
        enrollee.setDistrict(resultSet.getString(ColumnLabel.COLUMN_LABEL_DISTRICT));
        enrollee.setLocality(resultSet.getString(ColumnLabel.COLUMN_LABEL_LOCALITY));
        enrollee.setAvatar(resultSet.getString(ColumnLabel.COLUMN_LABEL_AVATAR));
    };

    private Mapper<Enrollee, PreparedStatement> mapperToDatabase = (Enrollee enrollee, PreparedStatement preparedStatement) -> {
        //preparedStatement.setInt(1, enrollee.getId());
        preparedStatement.setInt(1, enrollee.getIdUser());
        preparedStatement.setInt(2, enrollee.getIdSpeciality());
        preparedStatement.setDate(3, Date.valueOf(enrollee.getBirthday()));
        preparedStatement.setString(4, enrollee.getDistrict());
        preparedStatement.setString(5, enrollee.getLocality());
        preparedStatement.setString(6, enrollee.getAvatar());
    };

    public EnrolleeDaoImpl() {
        super.setMapperToDatabase(mapperToDatabase);
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<Enrollee> findEnrollees() throws DaoException {
        return findAll(Enrollee.class, SqlQuery.FIND_ALL_ENROLLEES);
    }

    @Override
    public Enrollee findEnrolleeById(Integer id) throws DaoException {
        return findByValue(Enrollee.class, SqlQuery.FIND_ENROLLEE_BY_ID, id);
    }

    @Override
    public boolean insertEnrollee(Enrollee enrollee) throws DaoException {
        return insert(enrollee, SqlQuery.ENROLLEE_INSERT);
    }

    @Override
    public boolean updateEnrollee(Enrollee enrollee) {
        return false;
    }

    @Override
    public boolean deleteEnrollee(Enrollee enrollee) {
        return false;
    }

    @Override
    public int getEnrolleeIdByUserId(int userId) throws DaoException {
        int idEnrollee = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_ENROLLEE_ID_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idEnrollee = resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE);
            } else {
                throw new DaoException("Getting enrollee ID failed by username, no ID obtained.");
            }
        } catch (SQLException e) {
            logger.error("Error while trying to get enrollee id by user id: " + e);
        }
        return idEnrollee;
    }

    @Override
    public List<Integer> getEnrolleesIdByFacultyName(String facultyName) throws DaoException {
        List<Integer> enrolleesId = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_ENROLLEES_ID_BY_FACULTY_NAME)) {
            preparedStatement.setString(1, facultyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                enrolleesId.add(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get enrollee id by faculty name: ", e);
            throw new DaoException(e);
        }
        return enrolleesId;
    }

    public List<Enrollee> getEnrolleesByFacultyName(String facultyName) throws DaoException {
        List<Enrollee> enrollees = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_ENROLLEES_BY_FACULTY_NAME)) {
            preparedStatement.setString(1, facultyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Enrollee enrollee = new Enrollee();
                mapperFromDatabase.map(resultSet, enrollee);
                enrollees.add(enrollee);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get enrollee by faculty name: ", e);
            throw new DaoException(e);
        }
        return enrollees;
    }
}
