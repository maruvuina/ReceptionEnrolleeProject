package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.entity.Notification;
import by.epam.receptionenrollee.exception.DaoException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


public class NotificationDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private NotificationDaoImpl notificationDao;

    private final Notification notification = new Notification(1, true);

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenNotificationIsProvided_thenNotificationIsSaved() throws DaoException {
        when(notificationDao.insertNotification(notification)).thenReturn(true);
        boolean actual = notificationDao.insertNotification(notification);
        assertTrue(actual);
    }

    @Test
    public void whenNotificationAndUserEmailAreProvided_thenNotificationStatusIsUpdated() throws DaoException {
        String userEmail = "username@mail.com";
        when(notificationDao
                .updateNotificationStatusByUserEmail(notification, userEmail))
                .thenReturn(true);
        boolean actual = notificationDao.updateNotificationStatusByUserEmail(notification, userEmail);
        assertTrue(actual);
    }
}
