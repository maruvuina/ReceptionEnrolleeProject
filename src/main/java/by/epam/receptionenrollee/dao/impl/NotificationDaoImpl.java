package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.dao.AbstractDao;
import by.epam.receptionenrollee.dao.ColumnLabel;
import by.epam.receptionenrollee.dao.Mapper;
import by.epam.receptionenrollee.dao.NotificationDao;
import by.epam.receptionenrollee.entity.Notification;
import by.epam.receptionenrollee.exception.DaoException;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NotificationDaoImpl extends AbstractDao<Notification> implements NotificationDao {
    private static final Logger logger = LogManager.getLogger(NotificationDaoImpl.class);

    public NotificationDaoImpl() {
        Mapper<Notification, PreparedStatement> mapperToDatabase = (Notification notification, PreparedStatement preparedStatement) -> {
            //preparedStatement.setInt(1, notification.getId());
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
    public List<Notification> findNotifications() throws DaoException {
        return null;
    }

    @Override
    public Notification findNotificationById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean insertNotification(Notification notification) {
        return false;
    }

    @Override
    public boolean updateNotification(Notification notification) {
        return false;
    }

    @Override
    public boolean deleteNotification(Notification notification) {
        return false;
    }
}
