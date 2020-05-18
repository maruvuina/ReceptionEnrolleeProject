package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.dao.impl.UserDaoImpl;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.exception.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.testng.Assert.assertTrue;

public class UserServiceTest {
    @InjectMocks
    private UserService userServiceMock;

    //@Mock
    private UserDaoImpl userDaoImplMock = Mockito.mock(UserDaoImpl.class);

    @Mock
    private SessionRequestContent sessionRequestContent;

    private String userEmail;

    private String userPassword;

    private int userId = 1;

    @BeforeMethod
    public void initParameters() {
        MockitoAnnotations.initMocks(this);
        userEmail = "ivan.ivanov@gmail.com";
        userPassword = "Ivan1234";
    }

    @Test
    public void getUserByIdIsSuccessful() throws DaoException, ServiceException {
        Mockito.when(userDaoImplMock
                .findUserById(userId)).thenReturn(new User());
        assertThat(userServiceMock.getUserById(userId), is(notNullValue()));
    }

    @Test
    public void isUserEmailUnique() throws DaoException, ServiceException {
        Mockito.when(userDaoImplMock
                .isUserEmailUnique(userEmail)).thenReturn(true);
        assertTrue(userServiceMock.verifyUserEmail(userEmail));
    }

    @Test
    public void getUserByEmailPasswordIsSuccessful() throws DaoException, ServiceException {
        Mockito.when(userDaoImplMock
                .findUserByLoginPassword(userEmail, userPassword)).thenReturn(new User());
        assertThat(userServiceMock.getUserByEmailPassword(userEmail, userPassword), is(notNullValue()));
    }

    @Test
    public void getInformationToNotifyEnrollee() throws DaoException, ServiceException {
        Mockito.when(userDaoImplMock
                .findFirstLastNameSpecialityFacultyByEmail(userEmail))
                .thenReturn(new EducationInformation());
        assertThat(userServiceMock.getInformationToNotifyEnrollee(userEmail), is(notNullValue()));
    }

    @Test
    public void registerUser() {

    }
}