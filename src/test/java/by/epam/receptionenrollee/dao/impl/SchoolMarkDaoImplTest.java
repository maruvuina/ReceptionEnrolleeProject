package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.entity.SchoolMark;
import by.epam.receptionenrollee.exception.DaoException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SchoolMarkDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private SchoolMarkDaoImpl schoolMarkDao;

    private final SchoolMark schoolMark = new SchoolMark();

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenSchoolMarkIsProvided_thenSchoolMarkIsSaved() throws DaoException {
        when(schoolMarkDao.insertSchoolMark(schoolMark)).thenReturn(true);
        boolean actual = schoolMarkDao.insertSchoolMark(schoolMark);
        assertTrue(actual);
    }

    @Test
    public void whenEnrolleeIdIsProvided_thenAvgSchoolMarkIsObtained() throws DaoException {
        double expected = 1.0;
        when(schoolMarkDao.getAvgSchoolMarkByEnrolleeId(1)).thenReturn(expected);
        double actual = schoolMarkDao.getAvgSchoolMarkByEnrolleeId(1);
        assertEquals(actual, expected);
    }
}
