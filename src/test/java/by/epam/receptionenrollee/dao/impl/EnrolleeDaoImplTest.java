package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.exception.DaoException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testng.Assert.*;

public class EnrolleeDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private EnrolleeDaoImpl enrolleeDao;

    private final String facultyName = "FacultyName";

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenEnrolleeIdIsProvided_thenEnrolleeIsObtained() throws DaoException {
        when(enrolleeDao.findEnrolleeById(1)).thenReturn(new Enrollee());
        assertThat(enrolleeDao.findEnrolleeById(1), is(notNullValue()));
    }

    @Test
    public void whenEnrolleeIsProvided_thenEnrolleeIsSaved() throws DaoException {
        when(enrolleeDao.insertEnrollee(new Enrollee())).thenReturn(true);
        boolean actual = enrolleeDao.insertEnrollee(new Enrollee());
        assertTrue(actual);
    }

    @Test
    public void whenUserIdIsProvided_thenEnrolleeIdIsObtained() throws DaoException {
        int expected = 1;
        when(enrolleeDao.findEnrolleeIdByUserId(1)).thenReturn(expected);
        int actual = enrolleeDao.findEnrolleeIdByUserId(1);
        assertEquals(actual, expected);
    }

    @Test
    public void whenFacultyNameIsProvided_thenEnrolleesIdAreObtained() throws DaoException {
        when(enrolleeDao.findEnrolleesIdByFacultyName(facultyName)).thenReturn(new ArrayList<>());
        assertThat(enrolleeDao.findEnrolleesIdByFacultyName(facultyName), is(notNullValue()));
    }

    @Test
    public void whenFacultyNameIsProvided_thenEnrolleesAreObtained() throws DaoException {
        when(enrolleeDao.getEnrolleesByFacultyName(facultyName)).thenReturn(new ArrayList<>());
        assertThat(enrolleeDao.getEnrolleesByFacultyName(facultyName), is(notNullValue()));
    }

    @Test
    public void whenFacultyNameIsProvided_thenNotEnrolledStudentsAreObtained() throws DaoException {
        when(enrolleeDao.getNotEnrolledStudentsByFacultyName(facultyName)).thenReturn(new ArrayList<>());
        assertThat(enrolleeDao.getNotEnrolledStudentsByFacultyName(facultyName), is(notNullValue()));
    }

    @Test
    public void whenFacultyNameIsProvided_thenEnrolledStudentsAreObtained() throws DaoException {
        when(enrolleeDao.getEnrolledStudentsByFacultyName(facultyName)).thenReturn(new ArrayList<>());
        assertThat(enrolleeDao.getEnrolledStudentsByFacultyName(facultyName), is(notNullValue()));
    }

    @Test
    public void whenEnrolleeIdIsProvided_thenEnrolleeScoreIsObtained() throws DaoException {
        int expected = 10;
        when(enrolleeDao.getEnrolleeScoreByEnrolleeId(1)).thenReturn(expected);
        int actual = enrolleeDao.getEnrolleeScoreByEnrolleeId(1);
        assertEquals(actual, expected);
    }

    @Test
    public void whenUserIdIsProvided_thenEnrolleeIsObtained() throws DaoException {
        when(enrolleeDao.findEnrolleeByUserId(1)).thenReturn(new Enrollee());
        assertThat(enrolleeDao.findEnrolleeByUserId(1), is(notNullValue()));
    }

    @Test
    public void whenEnrolleIsProvided_thenEnrolleeSpecialityIsUpdated() throws DaoException {
        when(enrolleeDao.updateEnrolleeSpeciality(new Enrollee())).thenReturn(new Enrollee());
        assertThat(enrolleeDao.updateEnrolleeSpeciality(new Enrollee()), is(notNullValue()));
    }

    @Test
    public void whenIsProvided_thenEnrolleeDateIsUpdated() throws DaoException {
        when(enrolleeDao.updateEnrolleeDate(new Enrollee())).thenReturn(new Enrollee());
        assertThat(enrolleeDao.updateEnrolleeDate(new Enrollee()), is(notNullValue()));
    }

    @Test
    public void whenEmailIsProvided_thenEnrolleeAttemptIsObtained() throws DaoException {
        int expected = 1;
        when(enrolleeDao.findEnrolleeAttemptByEmail("user@email.com")).thenReturn(expected);
        int actual = enrolleeDao.findEnrolleeAttemptByEmail("user@email.com");
        assertEquals(actual, expected);
    }

    @Test
    public void whenUpdateEnrolleeAttemptCalledVerified() throws DaoException {
        doNothing().when(enrolleeDao).updateEnrolleeAttempt(new Enrollee());
        enrolleeDao.updateEnrolleeAttempt(new Enrollee());
        verify(enrolleeDao, times(1)).updateEnrolleeAttempt(new Enrollee());
    }
}
