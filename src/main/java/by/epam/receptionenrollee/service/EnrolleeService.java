package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.dao.impl.*;
import by.epam.receptionenrollee.dao.pool.EntityTransaction;
import by.epam.receptionenrollee.entity.*;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.exception.ServiceException;
import by.epam.receptionenrollee.dao.DaoFactory;
import by.epam.receptionenrollee.util.TranslatorDataType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.util.StringUtil.getCurrentDateTime;
import static by.epam.receptionenrollee.util.StringUtil.randomString;
import static java.util.stream.Collectors.toMap;

public class EnrolleeService {
    private static final Logger logger = LogManager.getLogger(EnrolleeService.class);
    private static final String PATH_TO_UPLOADED_FILES_APP =
            "d:\\uploaded-files\\enrollee-system";
    private static final String USER_UPLOAD_IMAGES_DIR = "user-images";

    private enum ChangeInformation {
        CHANGE_ONLY_PERSONAL_INFORMATION {
            {
                this.changeCase = 0;
            }
        },
        CHANGE_ONLY_EDUCATIONAL_INFORMATION {
            {
                this.changeCase = 1;
            }
        },
        CHANGE_PERSONAL_EDUCATIONAL_INFORMATION {
            {
                this.changeCase = 2;
            }
        };
        int changeCase;
    }

    private String getEnrolleeBirthday(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent.getParameter(PARAM_NAME_YEAR) + "-" +
                sessionRequestContent.getParameter(PARAM_NAME_MONTH) + "-" +
                sessionRequestContent.getParameter(PARAM_NAME_DAY);
    }

    private String getNameForEnrolleeAvatar(ServiceParam serviceParam) {
        String uploadFileDir =
                PATH_TO_UPLOADED_FILES_APP + File.separator +
                        USER_UPLOAD_IMAGES_DIR + File.separator;
        int randStringLen = 7;
        String path;
        String fileName = null;
        try {
            Part part = serviceParam.getSessionRequestContent().getPart();
            path = part.getSubmittedFileName();
            String extension = FilenameUtils.getExtension(path);
            if (!extension.isEmpty()) {
                fileName = serviceParam.getIdUser() + "_" +
                        randomString(randStringLen) + "_" +
                        getCurrentDateTime() +
                        path.substring(path.lastIndexOf("."));
            } else {
                throw new IllegalArgumentException("File extension doesn't exist.");
            }
            part.write(uploadFileDir + fileName);
        } catch (IOException e) {
            logger.error("Getting user avatar failed, no such file. " + e);
        }
        return uploadFileDir + fileName;
    }

    private List<String> getSchoolSubjectMarks(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent.getParametersByName(PARAM_NAME_SCHOOL_MARK);
    }

    private boolean registerEnrolleeEntranceExamination(ServiceParam serviceParam) throws DaoException {
        SessionRequestContent sessionRequestContent = serviceParam.getSessionRequestContent();
        EntranceExaminationDaoImpl entranceExaminationDaoImpl = serviceParam.getEntranceExaminationDaoImpl();
        EntranceExamination newExamination = new EntranceExamination();
        newExamination.setIdEnrollee(serviceParam.getIdEnrollee());
        newExamination.setLanguageMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_LANGUAGE_EXAM)));
        newExamination.setFirstProfileExamMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_FIRST_PROFILE_EXAM)));
        newExamination.setSecondProfileExamMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_SECOND_PROFILE_EXAM)));
        return entranceExaminationDaoImpl.insertEntranceExamination(newExamination);
    }

    private boolean registerEnrolleeSchoolMark(ServiceParam serviceParam) throws DaoException {
        boolean isInserted = false;
        List<String> schoolSubjectMarks = getSchoolSubjectMarks(serviceParam.getSessionRequestContent());
        SchoolSubjectDaoImpl schoolSubjectDaoImpl = serviceParam.getSchoolSubjectDaoImpl();
        List<SchoolSubject> schoolSubjectList = schoolSubjectDaoImpl.findSchoolSubjects();
        int size = schoolSubjectMarks.size();
        for (int i = 0; i < size; i++) {
            SchoolMark schoolMark = new SchoolMark();
            schoolMark.setIdSubject(schoolSubjectList.get(i).getId());
            schoolMark.setIdEmplloee(serviceParam.getIdEnrollee());
            schoolMark.setMark(Integer.parseInt(schoolSubjectMarks.get(i)));
            SchoolMarkDaoImpl schoolMarkDaoImpl = serviceParam.getSchoolMarkDaoImpl();
            isInserted = schoolMarkDaoImpl.insertSchoolMark(schoolMark);
        }
        return isInserted;
    }

    private Enrollee registerEnrolleeValues(ServiceParam serviceParam) throws DaoException {
        SessionRequestContent sessionRequestContent = serviceParam.getSessionRequestContent();
        EnrolleeDaoImpl enrolleeDaoImpl = serviceParam.getEnrolleeDaoImpl();
        Enrollee newEnrollee = new Enrollee();
        int idUser = serviceParam.getIdUser();
        newEnrollee.setIdUser(idUser);
        newEnrollee.setIdSpeciality(serviceParam.getIdUserSpeciality());
        newEnrollee.setBirthday(getEnrolleeBirthday(sessionRequestContent));
        newEnrollee.setDistrict(sessionRequestContent.getParameter(PARAM_NAME_DISTRICT));
        newEnrollee.setLocality(sessionRequestContent.getParameter(PARAM_NAME_LOCALITY));
        newEnrollee.setAvatar(getNameForEnrolleeAvatar(serviceParam));
        enrolleeDaoImpl.insertEnrollee(newEnrollee);
        newEnrollee
                .setId(enrolleeDaoImpl
                        .findEnrolleeIdByUserId(serviceParam
                                .getIdUser()));
        return newEnrollee;
    }

    public Enrollee registerEnrollee(SessionRequestContent sessionRequestContent) throws ServiceException {
        Enrollee newEnrollee;
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        EntranceExaminationDaoImpl entranceExaminationDaoImpl = DaoFactory.getInstance().getEntranceExaminationDao();
        SchoolMarkDaoImpl schoolMarkDaoImpl = DaoFactory.getInstance().getSchoolMarkDao();
        SchoolSubjectDaoImpl schoolSubjectDaoImpl = DaoFactory.getInstance().getSchoolSubjectDao();
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        NotificationDaoImpl notificationDaoImpl = DaoFactory.getInstance().getNotificationDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrolleeDaoImpl, entranceExaminationDaoImpl, schoolMarkDaoImpl,
                schoolSubjectDaoImpl, userDaoImpl, specialityDaoImpl, notificationDaoImpl);
        try {
            ServiceParam serviceParam =
                    new ServiceParam(sessionRequestContent, enrolleeDaoImpl,
                            entranceExaminationDaoImpl, schoolMarkDaoImpl, schoolSubjectDaoImpl,
                            userDaoImpl, specialityDaoImpl, notificationDaoImpl);
            String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
            User user = userDaoImpl.findUserByEmail(email);
            String userSpeciality = serviceParam
                    .getEnrolleeEducationParameter(sessionRequestContent
                            .getParameter(PARAM_NAME_SPECIALITY), TranslatorDataType.TRANSLATOR_SPECIALITY);
            String userFaculty = serviceParam
                    .getEnrolleeEducationParameter(sessionRequestContent
                            .getParameter(PARAM_NAME_DEPARTMENT), TranslatorDataType.TRANSLATOR_FACULTY);
            int idUserSpeciality = specialityDaoImpl.getSpecialityIdByName(userSpeciality, userFaculty);
            serviceParam.setIdUser(user.getId());
            serviceParam.setIdUserSpeciality(idUserSpeciality);
            newEnrollee = registerEnrolleeValues(serviceParam);
            serviceParam.setIdEnrollee(newEnrollee.getId());
            setNotificationForRegisteredEnrollee(serviceParam);
            registerEnrolleeEntranceExamination(serviceParam);
            registerEnrolleeSchoolMark(serviceParam);
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while trying register enrollee: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return newEnrollee;
    }

    public Enrollee getEnrollee(User user) throws ServiceException {
        Enrollee enrollee;
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl, enrolleeDaoImpl);
        try {
            int idEnrollee = enrolleeDaoImpl.findEnrolleeIdByUserId(user.getId());
            enrollee = enrolleeDaoImpl.findEnrolleeById(idEnrollee);
            enrollee.setId(idEnrollee);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while trying to get enrollee info:  ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return enrollee;
    }

    public String getEnrolleeAvatar(String avatarPath) {
        String b64 = null;
        try {
            BufferedImage bImage = ImageIO.read(new File(avatarPath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String extension = FilenameUtils.getExtension(avatarPath);
            ImageIO.write(bImage, extension, baos);
            baos.flush();
            byte[] imageInByteArray = baos.toByteArray();
            baos.close();
            b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b64;
    }

    private EducationInformation getEnrolleeFacultySpeciality(ServiceParam serviceParam) throws DaoException {
        int idSpeciality = serviceParam.getIdUserSpeciality();
        return serviceParam.getSpecialityDaoImpl()
                .getSpecialityNameFacultyNameBySpecialityId(idSpeciality);
    }

    private EducationInformation getEducationInformation(ServiceParam serviceParam) throws DaoException {
        EducationInformation educationInformation;
        String facultyName = getTranslatedFacultyName(serviceParam);
        educationInformation =
                getEnrolleeFacultySpeciality(serviceParam);
        serviceParam.setFacultyName(facultyName);
        educationInformation.setEnrolleeScore(getEnrolleeScore(serviceParam));
        educationEnrolleePosition(educationInformation, serviceParam);
        return educationInformation;
    }

    public EducationInformation getEnrolleeEducationInformation(SessionRequestContent sessionRequestContent, Enrollee enrollee) throws ServiceException {
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        FacultyDaoImpl facultyDaoImpl = DaoFactory.getInstance().getFacultyDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(specialityDaoImpl, enrolleeDaoImpl, facultyDaoImpl);
        ServiceParam serviceParam = new ServiceParam(sessionRequestContent, enrolleeDaoImpl, specialityDaoImpl, facultyDaoImpl);
        serviceParam.setIdUserSpeciality(enrollee.getIdSpeciality());
        serviceParam.setIdEnrollee(enrollee.getId());
        EducationInformation educationInformation;
        try {
            educationInformation = getEducationInformation(serviceParam);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while getting education for single enrollee: ", e);
            throw new ServiceException(e);
        }
        return educationInformation;
    }

    private int getEnrolleeScore(ServiceParam serviceParam) throws DaoException {
        int idEnrollee = serviceParam.getIdEnrollee();
        return serviceParam.getEnrolleeDaoImpl()
                .getEnrolleeScoreByEnrolleeId(idEnrollee);
    }

    private int getEnrolleeScoreByIdEnrollee(ServiceParam serviceParam, int idEnrollee) throws DaoException {
        serviceParam.setIdEnrollee(idEnrollee);
        return getEnrolleeScore(serviceParam);
    }

    private Map<Integer, Integer> sortMapEnrollees(Map<Integer, Integer> enrollees) {
        return enrollees
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new)
                );
    }

    private void educationEnrolleePosition(EducationInformation educationInformation,
                                                       ServiceParam serviceParam) throws DaoException {
        int idEnrollee = serviceParam.getIdEnrollee();
        String facultyName = serviceParam.getFacultyName();
        EnrolleeDaoImpl enrolleeDaoImpl = serviceParam.getEnrolleeDaoImpl();
        SpecialityDaoImpl specialityDaoImpl = serviceParam.getSpecialityDaoImpl();
        List<Integer> enrolleesId;
        if (serviceParam.getEnrollees() != null) {
            enrolleesId = serviceParam
                    .getEnrollees()
                    .stream()
                    .map(Enrollee::getId)
                    .collect(Collectors.toList());
        } else {
            enrolleesId = enrolleeDaoImpl.findEnrolleesIdByFacultyName(facultyName);
        }
        Map<Integer, Integer> enrollees = new HashMap<>();
        for (Integer enrolleeId : enrolleesId) {
            enrollees.put(enrolleeId, getEnrolleeScoreByIdEnrollee(serviceParam, enrolleeId));
        }
        Map<Integer, Integer> sortedEnrollees = sortMapEnrollees(enrollees);
        List<Integer> indexes = new ArrayList<>(sortedEnrollees.keySet());
        educationInformation.setEnrolleePosition(indexes.indexOf(idEnrollee) + 1);
        educationInformation.setEnrolleesCount(indexes.size());
        educationInformation.setFacultyPlan(specialityDaoImpl.getFacultyPlan(facultyName));
    }

    private String getTranslatedFacultyName(ServiceParam serviceParam) throws DaoException {
        String facultyNameToTranslate = serviceParam.getSessionRequestContent()
                .getParameter(PARAM_NAME_ENROLLEE_FACULTY);
        String enrolleeFaculty;
        if (facultyNameToTranslate != null) {
            enrolleeFaculty = serviceParam
                    .getEnrolleeEducationParameter(facultyNameToTranslate, TranslatorDataType.TRANSLATOR_FACULTY);
        } else {
            enrolleeFaculty = serviceParam
                    .getFacultyDaoImpl()
                    .getFacultyNameByEnrolleeSpecialityId(serviceParam.getIdUserSpeciality());
        }
        return enrolleeFaculty;
    }

    public Map<Enrollee, EducationInformation> getInformationAboutEnrolleesByConcreteFaculty(SessionRequestContent sessionRequestContent) throws ServiceException {
        Map<Enrollee, EducationInformation> informationMap = new HashMap<>();
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrolleeDaoImpl, specialityDaoImpl, userDaoImpl);
        ServiceParam serviceParam =
                new ServiceParam(sessionRequestContent, enrolleeDaoImpl, specialityDaoImpl);
        try {
            List<Enrollee> enrollees = getListOfNotEnrolleesByConcreteFaculty(serviceParam);
            serviceParam.setEnrollees(enrollees);
            for (Enrollee enrollee : enrollees) {
                User user = userDaoImpl.findUserFirstLastNameEmailByUserId(enrollee.getIdUser());
                serviceParam.setIdUserSpeciality(enrollee.getIdSpeciality());
                serviceParam.setIdEnrollee(enrollee.getId());
                EducationInformation information = getEducationInformation(serviceParam);
                information.setEnrolleeFirstName(user.getFirstName());
                information.setEnrolleeLastName(user.getLastName());
                information.setEnrolleeEmail(user.getEmail());
                informationMap.put(enrollee, information);
            }
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while rollback get information education about enrollees: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return informationMap;
    }

    private List<Enrollee> getListOfNotEnrolleesByConcreteFaculty(ServiceParam serviceParam) throws DaoException {
        EnrolleeDaoImpl enrolleeDaoImpl = serviceParam.getEnrolleeDaoImpl();
        String facultyName = getTranslatedFacultyName(serviceParam);
        List<Enrollee> enrolleeList = enrolleeDaoImpl.getNotEnrolledStudentsByFacultyName(facultyName);
        getAvatarsForAllEnrollees(enrolleeList);
        return enrolleeList;
    }

    private void getAvatarsForAllEnrollees(List<Enrollee> enrollees) {
        for (Enrollee enrollee: enrollees) {
            enrollee.setAvatar(getEnrolleeAvatar(enrollee.getAvatar()));
        }
    }

    private void setNotificationForRegisteredEnrollee(ServiceParam serviceParam) throws DaoException {
        Notification notification = new Notification();
        notification.setIdEnrollee(serviceParam.getIdEnrollee());
        notification.setEnrolment(false);
        serviceParam.getNotificationDaoImpl().insertNotification(notification);
    }

    public List<EducationInformation> getEnrolledStudentsListByFaculty(SessionRequestContent sessionRequestContent) throws ServiceException {
        List<EducationInformation> educationInformationList;
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrolleeDaoImpl, userDaoImpl, specialityDaoImpl);
        try {
            ServiceParam serviceParam = new ServiceParam(sessionRequestContent, enrolleeDaoImpl, userDaoImpl, specialityDaoImpl);
            educationInformationList = getEnrolledStudents(serviceParam);
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while trying get information about enrolled students: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return educationInformationList;
    }

    private List<EducationInformation> getEnrolledStudents(ServiceParam serviceParam) throws DaoException {
        List<EducationInformation> educationInformationList = new ArrayList<>();
        String facultyName = getTranslatedFacultyName(serviceParam);
        List<Enrollee> enrollees = serviceParam.getEnrolleeDaoImpl()
                .getEnrolledStudentsByFacultyName(facultyName);
        for (Enrollee enrollee : enrollees) {
            enrollee.setAvatar(getEnrolleeAvatar(enrollee.getAvatar()));
            User user = serviceParam.getUserDaoImpl()
                    .findUserFirstLastNameEmailByUserId(enrollee.getIdUser());
            Speciality speciality = serviceParam.getSpecialityDaoImpl()
                    .findSpecialityById(enrollee.getIdSpeciality());
            EducationInformation educationInformation = new EducationInformation();
            educationInformation.setEnrolleeFirstName(user.getFirstName());
            educationInformation.setEnrolleeLastName(user.getLastName());
            educationInformation.setAvatarEnrollee(enrollee.getAvatar());
            educationInformation.setFacultyName(facultyName);
            educationInformation.setSpecialityName(speciality.getSpecialityName());
            educationInformationList.add(educationInformation);
        }
        return educationInformationList;
    }

    public void changeEnrolleeStatus(String enrolleeStatus, String enrolleeEmail) throws ServiceException {
        NotificationDaoImpl notificationDaoImpl = DaoFactory.getInstance().getNotificationDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(notificationDaoImpl);
        try {
            Notification notification = new Notification();
            notification.setEnrolment(Boolean.parseBoolean(enrolleeStatus));
            notificationDaoImpl.updateNotificationStatusByUserEmail(notification, enrolleeEmail);
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while trying get information about enrolled students: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    private EditInformation changeEnrolleePersonalInformation(ServiceParam serviceParam) throws DaoException {
        EditInformation editInformation = new EditInformation();
        Enrollee updatedEnrollee = new Enrollee();
        User updatedUser = new User();
        updatedUser.setFirstName(serviceParam.getSessionRequestContent()
                .getParameter(FIRST_NAME_TO_CHANGE));
        updatedUser.setLastName(serviceParam.getSessionRequestContent()
                .getParameter(LAST_NAME_TO_CHANGE));
        updatedUser.setMiddleName(serviceParam.getSessionRequestContent()
                .getParameter(MIDDLE_NAME_TO_CHANGE));
        updatedUser.setEmail(serviceParam.getSessionRequestContent()
                .getParameter(PARAM_NAME_LOGIN));
        updatedUser = serviceParam.getUserDaoImpl().updateUserFullName(updatedUser);
        updatedEnrollee.setIdUser(updatedUser.getId());
        updatedEnrollee.setBirthday(getEnrolleeBirthday(serviceParam.getSessionRequestContent()));

        Enrollee enrollee = serviceParam.getEnrolleeDaoImpl().updateEnrolleeDate(updatedEnrollee);
        editInformation.setUser(updatedUser);

        List<Enrollee> enrollees = new ArrayList<>();
        enrollees.add(enrollee);
        serviceParam.setEnrollees(enrollees);
        serviceParam.setIdEnrollee(enrollee.getId());

        return editInformation;
    }

    private User getUpdatedUserAfterChangeInformation(ServiceParam serviceParam) throws DaoException {
        return serviceParam
                .getUserDaoImpl()
                .findUserByEmail(serviceParam.getSessionRequestContent()
                .getParameter(PARAM_NAME_LOGIN));
    }

    private ServiceParam changeEnrolleeEducationInformation(ServiceParam serviceParam) throws DaoException {
        String userSpeciality = serviceParam
                .getEnrolleeEducationParameter(serviceParam.getSessionRequestContent()
                        .getParameter(PARAM_NAME_SPECIALITY), TranslatorDataType.TRANSLATOR_SPECIALITY);
        String userFaculty = serviceParam
                .getEnrolleeEducationParameter(serviceParam.getSessionRequestContent()
                        .getParameter(PARAM_NAME_DEPARTMENT), TranslatorDataType.TRANSLATOR_FACULTY);
        int idUserSpeciality = serviceParam.getSpecialityDaoImpl().getSpecialityIdByName(userSpeciality, userFaculty);
        User updatedUser = getUpdatedUserAfterChangeInformation(serviceParam);
        Enrollee updatedEnrollee = new Enrollee();
        updatedEnrollee.setIdUser(updatedUser.getId());
        updatedEnrollee.setIdSpeciality(idUserSpeciality);
        updatedEnrollee = serviceParam.getEnrolleeDaoImpl().updateEnrolleeSpeciality(updatedEnrollee);
        serviceParam.setIdEnrollee(updatedEnrollee.getId());
        serviceParam.setFacultyName(userFaculty);
        serviceParam.setSpecialityName(userSpeciality);
        List<Enrollee> enrollee = new ArrayList<>();
        enrollee.add(updatedEnrollee);
        serviceParam.setEnrollees(enrollee);
        return serviceParam;
    }

    private Enrollee getUpdatedEnrolleeAfterChangeInforamtion(ServiceParam updatedServiceParam) {
        return updatedServiceParam
                .getEnrollees()
                .stream()
                .filter(o -> o.getClass().equals(Enrollee.class))
                .findFirst()
                .orElse(null);
    }

    private EducationInformation getUpdatedEducationInformationAfterChangeInforamtion(ServiceParam updatedServiceParam) throws DaoException {
        EducationInformation educationInformation = new EducationInformation();
        educationInformation.setFacultyName(updatedServiceParam.getFacultyName());
        educationInformation.setSpecialityName(updatedServiceParam.getSpecialityName());
        educationInformation.setEnrolleeScore(getEnrolleeScore(updatedServiceParam));
        educationEnrolleePosition(educationInformation, updatedServiceParam);
        return educationInformation;
    }

    public EditInformation changeEnrolleeInformation(SessionRequestContent sessionRequestContent) throws ServiceException {
        EditInformation editInformation = new EditInformation();
        UserDaoImpl userDaoImpl = DaoFactory.getInstance().getUserDao();
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        FacultyDaoImpl facultyDaoImpl = DaoFactory.getInstance().getFacultyDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(userDaoImpl, specialityDaoImpl, enrolleeDaoImpl, facultyDaoImpl);
        try {
            ServiceParam serviceParam =
                    new ServiceParam(sessionRequestContent, enrolleeDaoImpl,
                            userDaoImpl, specialityDaoImpl, facultyDaoImpl);
            ServiceParam updatedServiceParam;
            EducationInformation updatedEducationInformation;
            int changeCaseInformation = Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_CHANGE_CASE_INFORMATION));
            if (changeCaseInformation == ChangeInformation.CHANGE_ONLY_PERSONAL_INFORMATION.changeCase) {
                editInformation = changeEnrolleePersonalInformation(serviceParam);
                updatedEducationInformation = facultyDaoImpl.getSpecialityNameFacultyNameBySpecialityId(editInformation.getUser().getId());
                updatedEducationInformation.setEnrolleeScore(getEnrolleeScore(serviceParam));
                serviceParam.setFacultyName(updatedEducationInformation.getFacultyName());
                educationEnrolleePosition(updatedEducationInformation, serviceParam);
                editInformation.setEnrollee(getUpdatedEnrolleeAfterChangeInforamtion(serviceParam));
                editInformation.setEducationInformation(updatedEducationInformation);
            } else if (changeCaseInformation == ChangeInformation.CHANGE_ONLY_EDUCATIONAL_INFORMATION.changeCase) {
                updatedServiceParam = changeEnrolleeEducationInformation(serviceParam);
                updateEnrolleeAttempt(updatedServiceParam);
                editInformation.setEnrollee(getUpdatedEnrolleeAfterChangeInforamtion(updatedServiceParam));
                editInformation.setUser(getUpdatedUserAfterChangeInformation(updatedServiceParam));
                updatedEducationInformation = getUpdatedEducationInformationAfterChangeInforamtion(updatedServiceParam);
                editInformation.setEducationInformation(updatedEducationInformation);
            } else if (changeCaseInformation == ChangeInformation.CHANGE_PERSONAL_EDUCATIONAL_INFORMATION.changeCase) {
                editInformation = changeEnrolleePersonalInformation(serviceParam);
                updatedServiceParam = changeEnrolleeEducationInformation(serviceParam);
                updateEnrolleeAttempt(updatedServiceParam);
                editInformation.setEnrollee(getUpdatedEnrolleeAfterChangeInforamtion(updatedServiceParam));
                updatedEducationInformation = getUpdatedEducationInformationAfterChangeInforamtion(updatedServiceParam);
                editInformation.setEducationInformation(updatedEducationInformation);
            }
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while change user full name: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return editInformation;
    }

    private void updateEnrolleeAttempt(ServiceParam serviceParam) throws DaoException {
        Enrollee enrollee = serviceParam.getEnrollees().get(0);
        serviceParam.getEnrolleeDaoImpl().updateEnrolleeAttempt(enrollee);
    }

    public int getEnrolleeAttempt(String email) throws ServiceException {
        int attempt;
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrolleeDaoImpl);
        try {
            attempt = enrolleeDaoImpl.findEnrolleeAttemptByEmail(email);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while change user full name: ", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return attempt;
    }
}
