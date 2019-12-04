package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.EntranceExaminationDao;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.entity.EntranceExamination;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EntranceExaminationDaoImpl extends AbstractDao<EntranceExamination> implements EntranceExaminationDao {
    private static final Logger logger = LogManager.getLogger(EntranceExaminationDaoImpl.class);

    public EntranceExaminationDaoImpl() {
        Mapper<EntranceExamination, PreparedStatement> mapperToDatabase = (EntranceExamination entranceExamination, PreparedStatement preparedStatement) -> {
            //preparedStatement.setInt(1, entranceExamination.getId());
            preparedStatement.setInt(1, entranceExamination.getIdEnrollee());
            preparedStatement.setInt(2, entranceExamination.getLanguageMark());
            preparedStatement.setInt(3, entranceExamination.getFirstProfileExamMark());
            preparedStatement.setInt(4, entranceExamination.getSecondProfileExamMark());

        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, EntranceExamination> mapperFromDatabase = (ResultSet resultSet, EntranceExamination entranceExamination) -> {
            entranceExamination.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENTRANCE_EXAMINATION));
            entranceExamination.setIdEnrollee(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE_FK));
            entranceExamination.setLanguageMark(resultSet.getInt(ColumnLabel.COLUMN_LABEL_LANGUAGE_MARK));
            entranceExamination.setFirstProfileExamMark(resultSet.getInt(ColumnLabel.COLUMN_LABEL_FIRST_PROFILE_EXAM_MARK));
            entranceExamination.setSecondProfileExamMark(resultSet.getInt(ColumnLabel.COLUMN_LABEL_SECOND_PROFILE_EXAM_MARK));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<EntranceExamination> findEntranceExaminations() throws DaoException {
        return null;
    }

    @Override
    public EntranceExamination findEntranceExaminationById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertEntranceExamination(EntranceExamination entranceExamination) throws SQLException, DaoException {
        return insert(entranceExamination, SqlQuery.ENTRANCE_EXAMINATION_INSERT);
    }

    @Override
    public boolean updateEntranceExamination(EntranceExamination entranceExamination) {
        return false;
    }

    @Override
    public boolean deleteEntranceExamination(EntranceExamination entranceExamination) {
        return false;
    }

    @Override
    public int getSumExaminationByEnrolleeId(int id) throws DaoException {
        int sum = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SUM_EXAMINATION)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sum = resultSet.getInt(ColumnLabel.COLUMN_LABEL_SUM_MARK);
            } else {
                throw new DaoException("Getting entrance examination sum mark failed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to get examination sum mark: ", e);
        }
        return sum;
    }
}
