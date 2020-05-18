package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.dao.impl.*;
import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.util.LanguageParam;
import by.epam.receptionenrollee.util.Translator;
import by.epam.receptionenrollee.util.TranslatorDataType;

import java.util.List;

public class ServiceParam {
    private SessionRequestContent sessionRequestContent;
    private EnrolleeDaoImpl enrolleeDaoImpl;
    private EntranceExaminationDaoImpl entranceExaminationDaoImpl;
    private SchoolMarkDaoImpl schoolMarkDaoImpl;
    private SchoolSubjectDaoImpl schoolSubjectDaoImpl;
    private UserDaoImpl userDaoImpl;
    private SpecialityDaoImpl specialityDaoImpl;
    private FacultyDaoImpl facultyDaoImpl;
    private NotificationDaoImpl notificationDaoImpl;
    private int idUser;
    private int idUserSpeciality;
    private int idEnrollee;
    private String facultyName;
    private String specialityName;
    private List<Enrollee> enrollees;

    public ServiceParam(){}

    public ServiceParam(SessionRequestContent sessionRequestContent, EnrolleeDaoImpl enrolleeDaoImpl,
                        EntranceExaminationDaoImpl entranceExaminationDaoImpl, SchoolMarkDaoImpl schoolMarkDaoImpl,
                        SchoolSubjectDaoImpl schoolSubjectDaoImpl, UserDaoImpl userDaoImpl,
                        SpecialityDaoImpl specialityDaoImpl, NotificationDaoImpl notificationDaoImpl) {
        this.sessionRequestContent = sessionRequestContent;
        this.enrolleeDaoImpl = enrolleeDaoImpl;
        this.entranceExaminationDaoImpl = entranceExaminationDaoImpl;
        this.schoolMarkDaoImpl = schoolMarkDaoImpl;
        this.schoolSubjectDaoImpl = schoolSubjectDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.specialityDaoImpl = specialityDaoImpl;
        this.notificationDaoImpl = notificationDaoImpl;
    }

    public ServiceParam(SessionRequestContent sessionRequestContent, EnrolleeDaoImpl enrolleeDaoImpl,
                        SpecialityDaoImpl specialityDaoImpl) {
        this.sessionRequestContent = sessionRequestContent;
        this.enrolleeDaoImpl = enrolleeDaoImpl;
        this.specialityDaoImpl = specialityDaoImpl;
    }

    public ServiceParam(SessionRequestContent sessionRequestContent, EnrolleeDaoImpl enrolleeDaoImpl,
                        SpecialityDaoImpl specialityDaoImpl, FacultyDaoImpl facultyDaoImpl) {
        this.sessionRequestContent = sessionRequestContent;
        this.enrolleeDaoImpl = enrolleeDaoImpl;
        this.specialityDaoImpl = specialityDaoImpl;
        this.facultyDaoImpl = facultyDaoImpl;
    }

    public ServiceParam(SessionRequestContent sessionRequestContent, EnrolleeDaoImpl enrolleeDaoImpl,
                        UserDaoImpl userDaoImpl, SpecialityDaoImpl specialityDaoImpl) {
        this.sessionRequestContent = sessionRequestContent;
        this.enrolleeDaoImpl = enrolleeDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.specialityDaoImpl = specialityDaoImpl;
    }

    public ServiceParam(SessionRequestContent sessionRequestContent, EnrolleeDaoImpl enrolleeDaoImpl,
    UserDaoImpl userDaoImpl, SpecialityDaoImpl specialityDaoImpl, FacultyDaoImpl facultyDaoImpl) {
        this.sessionRequestContent = sessionRequestContent;
        this.enrolleeDaoImpl = enrolleeDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.specialityDaoImpl = specialityDaoImpl;
        this.facultyDaoImpl = facultyDaoImpl;
    }

    public SessionRequestContent getSessionRequestContent() {
        return sessionRequestContent;
    }

    public EnrolleeDaoImpl getEnrolleeDaoImpl() {
        return enrolleeDaoImpl;
    }

    public EntranceExaminationDaoImpl getEntranceExaminationDaoImpl() {
        return entranceExaminationDaoImpl;
    }

    public SchoolMarkDaoImpl getSchoolMarkDaoImpl() {
        return schoolMarkDaoImpl;
    }

    public SchoolSubjectDaoImpl getSchoolSubjectDaoImpl() {
        return schoolSubjectDaoImpl;
    }

    public UserDaoImpl getUserDaoImpl() {
        return userDaoImpl;
    }

    public SpecialityDaoImpl getSpecialityDaoImpl() {
        return specialityDaoImpl;
    }

    public void setFacultyDaoImpl(FacultyDaoImpl facultyDaoImpl) {
        this.facultyDaoImpl = facultyDaoImpl;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserSpeciality() {
        return idUserSpeciality;
    }

    public void setIdUserSpeciality(int idUserSpeciality) {
        this.idUserSpeciality = idUserSpeciality;
    }

    public int getIdEnrollee() {
        return idEnrollee;
    }

    public void setIdEnrollee(int idEnrollee) {
        this.idEnrollee = idEnrollee;
    }

    public void setEnrolleeDaoImpl(EnrolleeDaoImpl enrolleeDaoImpl) {
        this.enrolleeDaoImpl = enrolleeDaoImpl;
    }

    public void setEntranceExaminationDaoImpl(EntranceExaminationDaoImpl entranceExaminationDaoImpl) {
        this.entranceExaminationDaoImpl = entranceExaminationDaoImpl;
    }

    public void setSchoolMarkDaoImpl(SchoolMarkDaoImpl schoolMarkDaoImpl) {
        this.schoolMarkDaoImpl = schoolMarkDaoImpl;
    }

    public void setSchoolSubjectDaoImpl(SchoolSubjectDaoImpl schoolSubjectDaoImpl) {
        this.schoolSubjectDaoImpl = schoolSubjectDaoImpl;
    }

    public void setSpecialityDaoImpl(SpecialityDaoImpl specialityDaoImpl) {
        this.specialityDaoImpl = specialityDaoImpl;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public void setSessionRequestContent(SessionRequestContent sessionRequestContent) {
        this.sessionRequestContent = sessionRequestContent;
    }

    public FacultyDaoImpl getFacultyDaoImpl() {
        return facultyDaoImpl;
    }

    public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    public List<Enrollee> getEnrollees() {
        return enrollees;
    }

    public void setEnrollees(List<Enrollee> enrollees) {
        this.enrollees = enrollees;
    }

    public NotificationDaoImpl getNotificationDaoImpl() {
        return notificationDaoImpl;
    }

    public String getEnrolleeEducationParameter(String educationParameter, TranslatorDataType translatorDataType) {
        String userLocaleLanguage = sessionRequestContent.getUserLocale();
        Translator translator = new Translator(translatorDataType);
        LanguageParam languageParam = LanguageParam.valueOf(userLocaleLanguage.toUpperCase());
        switch (languageParam) {
            case RU_RU:
                break;
            case BE_BY:
            case EN_US:
                educationParameter = translator.translate(userLocaleLanguage, educationParameter);
                break;
        }
        return educationParameter;
    }
}
