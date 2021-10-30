package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;


public class UserServiceTest {
    private AutoCloseable closeable;

    @Mock
    private UserService userService;

    @Mock
    private SessionRequestContent sessionRequestContent;

    private final String userEmail = "ivan.ivanov@gmail.com";

    @BeforeMethod
    public void initParameters() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testVerifyUserEmail() throws ServiceException {
        when(userService.verifyUserEmail("ivan.ivanov@gmail.com")).thenReturn(true);
        boolean actual = userService.verifyUserEmail("ivan.ivanov@gmail.com");
        assertTrue(actual);
    }

    @Test
    public void testGetUserByEmailPassword() throws ServiceException {
        String userPassword = "Ivan1234";
        when(userService
                .getUserByEmailPassword(userEmail, userPassword)).thenReturn(new User());
        assertThat(userService.getUserByEmailPassword(userEmail, userPassword), is(notNullValue()));
    }

    @Test
    public void testRegisterUser() throws ServiceException {
        when(userService.registerUser(sessionRequestContent)).thenReturn(new User());
        assertThat(userService.registerUser(sessionRequestContent), is(notNullValue()));
    }

    @Test
    public void testGetUserById() throws ServiceException {
        int userId = 1;
        when(userService.getUserById(userId)).thenReturn(new User());
        assertThat(userService.getUserById(userId), is(notNullValue()));
    }

    @Test
    public void testGetInformationToNotifyEnrollee() throws ServiceException {
        when(userService.getInformationToNotifyEnrollee(userEmail)).thenReturn(new EducationInformation());
        assertThat(userService.getInformationToNotifyEnrollee(userEmail), is(notNullValue()));
    }

    @Test
    public void getUserByEmailIsSuccessful() throws ServiceException {
        when(userService.getUserByEmail(userEmail)).thenReturn(new User());
        assertThat(userService.getUserByEmail(userEmail), is(notNullValue()));
    }
}
