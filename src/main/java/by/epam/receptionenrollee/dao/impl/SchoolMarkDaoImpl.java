package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.SchoolMarkDao;
import by.epam.receptionenrollee.entity.SchoolMark;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SchoolMarkDaoImpl extends AbstractDao<SchoolMark> implements SchoolMarkDao {
    private static final Logger logger = LogManager.getLogger(SchoolMarkDaoImpl.class);

    public SchoolMarkDaoImpl() {
        Mapper<SchoolMark, PreparedStatement> mapperToDatabase = (SchoolMark schoolMark, PreparedStatement preparedStatement) -> {
            preparedStatement.setInt(1, schoolMark.getIdSubject());
            preparedStatement.setInt(2, schoolMark.getIdEmplloee());
            preparedStatement.setInt(3, schoolMark.getMark());
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, SchoolMark> mapperFromDatabase = (ResultSet resultSet, SchoolMark schoolMark) -> {
            schoolMark.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SCHOOL_MARK));
            schoolMark.setIdSubject(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SUBJECT_MARK_FK));
            schoolMark.setIdEmplloee(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE_FK));
            schoolMark.setMark(resultSet.getInt(ColumnLabel.COLUMN_LABEL_MARK));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<SchoolMark> findSchoolMarks() throws DaoException {
        return null;
    }

    @Override
    public SchoolMark findSchoolMarkById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertSchoolMark(SchoolMark schoolMark) throws DaoException {
        return insert(schoolMark, SqlQuery.SCHOOL_MARK_INSERT);
    }

    @Override
    public boolean updateSchoolMark(SchoolMark schoolMark) {
        return false;
    }

    @Override
    public boolean deleteSchoolMark(SchoolMark schoolMark) {
        return false;
    }

    @Override
    public double getAvgSchoolMarkByEnrolleeId(int id) throws DaoException {
        double avg = 0.0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.AVG_SCHOOL_MARK)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               avg = resultSet.getDouble(ColumnLabel.COLUMN_LABEL_ENROLLEE_AVG_MARK);
            } else {
                throw new DaoException("Getting enrollee avg mark failed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to gwt average school mark: ", e);
        }
        return avg;
    }
}
