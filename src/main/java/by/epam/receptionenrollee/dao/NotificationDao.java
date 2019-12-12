package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.entity.Notification;
import by.epam.receptionenrollee.exception.DaoException;

import java.util.List;

/**
 * CRUD operations interface for Notification entity
 */
public interface NotificationDao {
    /**
     * Finds all notifications in Database
     * @return List of all notifications
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    List<Notification> findNotifications() throws DaoException;

    /**
     * Finds notification by notification id
     * @param id - Notification`s id
     * @return Notification
     * @throws DaoException if connection is down, broken or unable to retrieve information for certain reasons
     */
    Notification findNotificationById(Integer id) throws DaoException;

    /**
     * Adds new notification to Database
     * @param notification - Notification to add in Database
     * @return true if operation success and false if fails
     */
    boolean insertNotification(Notification notification) throws DaoException;

    /**
     * Updates notification in Database
     * @param notification - Notification to update in Database
     * @return true if operation success and false if fails
     */
    boolean updateNotification(Notification notification);

    /**
     * Deletes notification from Database
     * @param notification - Notification to delete from Database
     * @return true if operation success and false if fails
     */
    boolean deleteNotification(Notification notification);
}
