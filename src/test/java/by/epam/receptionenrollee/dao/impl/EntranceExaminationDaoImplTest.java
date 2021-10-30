package by.epam.receptionenrollee.dao.impl;

import by.epam.receptionenrollee.exception.DaoException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class EntranceExaminationDaoImplTest {
    private AutoCloseable closeable;

    @Mock
    private EntranceExaminationDaoImpl entranceExaminationDao;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void whenEnrolleeIdIsProvided_thenSumExaminationIsObtained() throws DaoException {
        int expected = 1;
        when(entranceExaminationDao.getSumExaminationByEnrolleeId(isA(Integer.class))).thenReturn(expected);
        int actual = entranceExaminationDao.getSumExaminationByEnrolleeId(1);
        assertEquals(actual, expected);
    }
}
