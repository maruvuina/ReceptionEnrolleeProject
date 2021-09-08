package by.epam.receptionenrollee.dao;

import by.epam.receptionenrollee.dao.impl.*;

/**
 * Concrete Dao factory for MySQL database
 */
public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }

    public SchoolSubjectDaoImpl getSchoolSubjectDao() {
        return new SchoolSubjectDaoImpl();
    }

    public FacultyDaoImpl getFacultyDao() {
        return new FacultyDaoImpl();
    }

    public SpecialityDaoImpl getSpecialityDao() {
        return new SpecialityDaoImpl();
    }

    public EnrolleeDaoImpl getEnrolleeDao() {
        return new EnrolleeDaoImpl();
    }

    public NotificationDaoImpl getNotificationDao() {
        return new NotificationDaoImpl();
    }

    public EntranceExaminationDaoImpl getEntranceExaminationDao() {
        return new EntranceExaminationDaoImpl();
    }

    public SchoolMarkDaoImpl getSchoolMarkDao() {
        return new SchoolMarkDaoImpl();
    }
}
