package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;


public class EnrolleeServiceTest {
    private AutoCloseable closeable;

    @Mock
    private EnrolleeService enrolleeService;

    @Mock
    private SessionRequestContent sessionRequestContent;

    private final String email = "user@email.com";

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenSessionRequestContentIsCorrect_thenEnrolleeIsRegistered() throws ServiceException {
        when(enrolleeService.registerEnrollee(sessionRequestContent)).thenReturn(new Enrollee());
        assertThat(enrolleeService.registerEnrollee(sessionRequestContent), is(notNullValue()));
    }

    @Test
    public void whenUserIsProvided_thenRetrievedEnrolleeIsCorrect() throws ServiceException {
        when(enrolleeService.getEnrollee(new User())).thenReturn(new Enrollee());
        assertThat(enrolleeService.getEnrollee(new User()), is(notNullValue()));
    }

    @Test
    public void whenAvatarPathIsProvided_thenRetrievedAvatarAsStringIsPresent() {
        String pathToAvatar = "path\\project\\pictures\\user1.png";
        when(enrolleeService.getEnrolleeAvatar(pathToAvatar)).thenReturn(pathToAvatar);
        assertThat(enrolleeService.getEnrolleeAvatar(pathToAvatar), is(notNullValue()));
    }

    @Test
    public void whenSessionRequestContentIsProvided_thenEducationInformationIsCorrect() throws ServiceException {
        when(enrolleeService
                .getEnrolleeEducationInformation(sessionRequestContent, new Enrollee()))
                .thenReturn(new EducationInformation());
        assertThat(enrolleeService
                .getEnrolleeEducationInformation(sessionRequestContent, new Enrollee()), is(notNullValue()));
    }

    @Test
    public void whenSessionRequestContentIsProvided_thenInformationAboutEnrolleesByConcreteFacultyIsCorrect() throws ServiceException {
        when(enrolleeService
                .getInformationAboutEnrolleesByConcreteFaculty(sessionRequestContent))
                .thenReturn(new HashMap<>());
        assertThat(enrolleeService
                .getInformationAboutEnrolleesByConcreteFaculty(sessionRequestContent), is(notNullValue()));
    }

    @Test
    public void whenSessionRequestContentIsProvided_thenEnrolledStudentsListByFacultyIsCorrect() throws ServiceException {
        when(enrolleeService
                .getEnrolledStudentsListByFaculty(sessionRequestContent))
                .thenReturn(new ArrayList<>());
        assertThat(enrolleeService.getEnrolledStudentsListByFaculty(sessionRequestContent), is(notNullValue()));
    }

    @Test
    public void whenChangeEnrolleeStatusCalledVerified() throws ServiceException {
        String enrolleeStatus = "true";
        doNothing().when(enrolleeService).changeEnrolleeStatus(isA(String.class), isA(String.class));
        enrolleeService.changeEnrolleeStatus(enrolleeStatus, email);
        verify(enrolleeService, times(1)).changeEnrolleeStatus(enrolleeStatus, email);
    }

    @Test
    public void whenSessionRequestContentIsProvided_thenChangeEnrolleeInformationIsPerformed() throws ServiceException {
        when(enrolleeService.changeEnrolleeInformation(sessionRequestContent)).thenReturn(new EditInformation());
        assertThat(enrolleeService.changeEnrolleeInformation(sessionRequestContent), is(notNullValue()));
    }

    @Test
    public void whenEmailIsProvided_thenEnrolleeAttemptIsCorrect() throws ServiceException {
        when(enrolleeService.getEnrolleeAttempt(email)).thenReturn(1);
        int actual = enrolleeService.getEnrolleeAttempt(email);
        assertEquals(actual, 1);
    }
}
