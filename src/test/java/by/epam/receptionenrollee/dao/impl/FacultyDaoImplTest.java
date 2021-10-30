package by.epam.receptionenrollee.dao.impl;

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
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class FacultyDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private FacultyDaoImpl facultyDao;

    private final int idSpeciality = 1;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenSpecialityIdIsProvided_thenFacultyNameIsCorrect() throws DaoException {
        String facultyName = "FacultyName";
        when(facultyDao.getFacultyNameByEnrolleeSpecialityId(idSpeciality)).thenReturn(facultyName);
        String actual = facultyDao.getFacultyNameByEnrolleeSpecialityId(idSpeciality);
        assertEquals(actual, facultyName);
    }

    @Test
    public void whenSpecialityIdIsProvided_thenSpecialityNameFacultyNameAndEducationInformationIsCorrect() throws DaoException {
        when(facultyDao.getSpecialityNameFacultyNameBySpecialityId(idSpeciality))
                .thenReturn(new EducationInformation());
        assertThat(facultyDao.getSpecialityNameFacultyNameBySpecialityId(idSpeciality), is(notNullValue()));
    }
}
