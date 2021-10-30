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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SpecialityDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private SpecialityDaoImpl specialityDao;

    private final int idSpeciality = 1;

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
    public void whenSpecialityNameAndFacultyNameAreProvided_thenSpecialityIdIsObtained() throws DaoException {
        when(specialityDao.getSpecialityIdByName(isA(String.class), isA(String.class))).thenReturn(idSpeciality);
        int actual = specialityDao.getSpecialityIdByName("SpecialityName", facultyName);
        assertEquals(actual, idSpeciality);
    }

    @Test
    public void whenSpecialityIdIsProvided_thenSpecialityNameAndFacultyNameAreObtained() throws DaoException {
        when(specialityDao.getSpecialityNameFacultyNameBySpecialityId(idSpeciality))
                .thenReturn(new EducationInformation());
        assertThat(specialityDao.getSpecialityNameFacultyNameBySpecialityId(idSpeciality), is(notNullValue()));
    }

    @Test
    public void whenFacultyNameIsProvided_thenFacultyPlanIsObtained() throws DaoException {
        int expected = 10;
        when(specialityDao.getFacultyPlan(facultyName)).thenReturn(expected);
        int actual = specialityDao.getFacultyPlan(facultyName);
        assertEquals(actual, expected);
    }
}
