package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.SchoolSubjectDao;
import by.epam.receptionenrollee.entity.SchoolSubject;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SchoolSubjectDaoImpl extends AbstractDao<SchoolSubject> implements SchoolSubjectDao {
    private static final Logger logger = LogManager.getLogger(SchoolSubjectDaoImpl.class);

    public SchoolSubjectDaoImpl() {
        Mapper<SchoolSubject, PreparedStatement> mapperToDatabase = (SchoolSubject schoolSubject, PreparedStatement preparedStatement) -> {
            //preparedStatement.setInt(1, schoolSubject.getId());
            preparedStatement.setString(1, schoolSubject.getSubjectName());
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, SchoolSubject> mapperFromDatabase = (ResultSet resultSet, SchoolSubject schoolSubject) -> {
            schoolSubject.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_SCHOOL_SUBJECT));
            schoolSubject.setSubjectName(resultSet.getString(ColumnLabel.COLUMN_LABEL_SUBJECT_NAME));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<SchoolSubject> findSchoolSubjects() throws DaoException {
        return findAll(SchoolSubject.class, SqlQuery.FIND_SCHOOL_SUBJECTS);
    }

    @Override
    public SchoolSubject findSchoolSubjectById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertUser(SchoolSubject schoolSubject) {
        return false;
    }

    @Override
    public boolean updateUser(SchoolSubject schoolSubject) {
        return false;
    }

    @Override
    public boolean deleteUser(SchoolSubject schoolSubject) {
        return false;
    }
}
