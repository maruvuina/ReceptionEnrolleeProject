package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.service.EducationInformation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class UserDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private UserDaoImpl userDao;

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
    public void whenUserIsProvided_thenUserIaSaved() throws DaoException {
        when(userDao.insertUser(new User())).thenReturn(true);
        boolean actual = userDao.insertUser(new User());
        assertTrue(actual);
    }

    @Test
    public void whenUserIsProvided_thenUserIsUpdated() throws DaoException {
        when(userDao.updateUser(new User())).thenReturn(new User());
        assertThat(userDao.updateUser(new User()), is(notNullValue()));
    }

    @Test
    public void whenUserIsProvided_thenUserIsDeleted() throws DaoException {
        when(userDao.deleteUser(new User())).thenReturn(true);
        boolean actual = userDao.deleteUser(new User());
        assertTrue(actual);
    }

    @Test
    public void whenUserEmailIsProvided_thenUserIsObtained() throws DaoException {
        when(userDao.findUserByEmail(isA(String.class))).thenReturn(new User());
        assertThat(userDao.findUserByEmail(email), is(notNullValue()));
    }

    @Test
    public void whenEmailAndPasswordAreProvided_thenUserIsObtained() throws DaoException {
        when(userDao.findUserByLoginPassword(isA(String.class), isA(String.class)))
                .thenReturn(new User());
        assertThat(userDao.findUserByLoginPassword(email, "pass"), is(notNullValue()));
    }

    @Test
    public void isUserEmailUnique() throws DaoException {
        when(userDao.isUserEmailUnique(isA(String.class))).thenReturn(true);
        boolean actual = userDao.isUserEmailUnique(email);
        assertTrue(actual);
    }

    @Test
    public void whenUserIdIsProvided_thenUserIsObtained() throws DaoException {
        when(userDao.findUserFirstLastNameEmailByUserId(1)).thenReturn(new User());
        assertThat(userDao.findUserFirstLastNameEmailByUserId(1), is(notNullValue()));
    }

    @Test
    public void whenEmailIsProvided_thenEducationInformationIsObtained() throws DaoException {
        when(userDao.findFirstLastNameSpecialityFacultyByEmail(isA(String.class)))
                .thenReturn(new EducationInformation());
        assertThat(userDao.findFirstLastNameSpecialityFacultyByEmail(email), is(notNullValue()));
    }

    @Test
    public void whenUserIsProvided_thenUserFullNameIsUpdated() throws DaoException {
        when(userDao.updateUserFullName(new User())).thenReturn(new User());
        assertThat(userDao.updateUserFullName(new User()), is(notNullValue()));
    }
}
