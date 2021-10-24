package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.NotificationDao;
import by.epam.receptionenrollee.entity.Notification;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.sql.SqlQuery;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NotificationDaoImpl extends AbstractDao<Notification> implements NotificationDao {
    private static final Logger logger = LogManager.getLogger(NotificationDaoImpl.class);

    public NotificationDaoImpl() {
        Mapper<Notification, PreparedStatement> mapperToDatabase = (Notification notification, PreparedStatement preparedStatement) -> {
            preparedStatement.setInt(1, notification.getIdEnrollee());
            preparedStatement.setBoolean(2, notification.isEnrolment());
        };
        super.setMapperToDatabase(mapperToDatabase);
        Mapper<ResultSet, Notification> mapperFromDatabase = (ResultSet resultSet, Notification notification) -> {
            notification.setId(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_NOTIFICATION));
            notification.setIdEnrollee(resultSet.getInt(ColumnLabel.COLUMN_LABEL_ID_ENROLLEE_FK));
            notification.setEnrolment(resultSet.getBoolean(ColumnLabel.COLUMN_LABEL_NOTIFICATION));
        };
        super.setMapperFromDatabase(mapperFromDatabase);
    }

    @Override
    public List<Notification> findNotifications() {
        throw new UnsupportedOperationException("Invalid operation for notification.");
    }

    @Override
    public Notification findNotificationById(Integer id) {
        throw new UnsupportedOperationException("Invalid operation for notification.");
    }

    @Override
    public boolean insertNotification(Notification notification) throws DaoException {
        return insert(notification, SqlQuery.NOTIFICATION_INSERT);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        throw new UnsupportedOperationException("Invalid operation for notification.");
    }

    @Override
    public boolean deleteNotification(Notification notification) {
        throw new UnsupportedOperationException("Invalid operation for notification.");
    }

    public boolean updateNotificationStatusByUserEmail(Notification notification, String userEmail) throws DaoException {
        boolean result;
        try(var preparedStatement = connection.prepareStatement(SqlQuery.UPDATE_ENROLLEE_STATUS)) {
            preparedStatement.setBoolean(1, notification.isEnrolment());
            preparedStatement.setString(2, userEmail);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount > 0) {
                result = true;
            } else {
                throw new DaoException("Update notification failed.");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR,"Error while trying to update row in database: ", e);
            throw new DaoException(e);
        }
        return result;
    }
}
