package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.SchoolSubjectDao;
import by.epam.receptionenrollee.entity.SchoolSubject;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SchoolSubjectDaoImpl extends AbstractDao<SchoolSubject> implements SchoolSubjectDao {

    public SchoolSubjectDaoImpl() {
        Mapper<SchoolSubject, PreparedStatement> mapperToDatabase = (SchoolSubject schoolSubject, PreparedStatement preparedStatement) ->
                preparedStatement.setString(1, schoolSubject.getSubjectName());
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
    public SchoolSubject findSchoolSubjectById(Integer id) {
        throw new UnsupportedOperationException("Invalid operation for school subject.");
    }

    @Override
    public boolean insertUser(SchoolSubject schoolSubject) {
        throw new UnsupportedOperationException("Invalid operation for school subject.");
    }

    @Override
    public boolean updateUser(SchoolSubject schoolSubject) {
        throw new UnsupportedOperationException("Invalid operation for school subject.");
    }

    @Override
    public boolean deleteUser(SchoolSubject schoolSubject) {
        throw new UnsupportedOperationException("Invalid operation for school subject.");
    }
}
