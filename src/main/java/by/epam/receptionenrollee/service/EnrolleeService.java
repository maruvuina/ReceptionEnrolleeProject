package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.dao.impl.*;
import by.epam.receptionenrollee.dao.pool.EntityTransaction;
import by.epam.receptionenrollee.entity.*;
import by.epam.receptionenrollee.exception.DaoException;
import by.epam.receptionenrollee.exception.ServiceException;
import by.epam.receptionenrollee.factory.DaoFactory;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.util.TranslatorDataType;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.validator.StringUtil.getCurrentDateTime;
import static by.epam.receptionenrollee.validator.StringUtil.randomString;
import static java.util.stream.Collectors.toMap;

public class EnrolleeService {
    private static final Logger logger = Logger.getLogger(EnrolleeService.class);
    private static final String PATH_TO_UPLOADED_FILES_APP =
            "d:\\uploaded-files\\enrollee-system";
    private static final String USER_UPLOAD_IMAGES_DIR = "user-images";

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

    private boolean registerEnrolleeEntranceExamination(ServiceParam serviceParam)
            throws DaoException, SQLException {
        SessionRequestContent sessionRequestContent = serviceParam.getSessionRequestContent();
        EntranceExaminationDaoImpl entranceExaminationDaoImpl = serviceParam.getEntranceExaminationDaoImpl();
        EntranceExamination newExamination = new EntranceExamination();
        newExamination.setIdEnrollee(serviceParam.getIdEnrollee());
        newExamination.setLanguageMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_LANGUAGE_EXAM)));
        newExamination.setFirstProfileExamMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_FIRST_PROFILE_EXAM)));
        newExamination.setSecondProfileExamMark(Integer.parseInt(sessionRequestContent.getParameter(PARAM_NAME_SECOND_PROFILE_EXAM)));
        return entranceExaminationDaoImpl.insertEntranceExamination(newExamination);
    }

    private boolean registerEnrolleeSchoolMark(ServiceParam serviceParam) throws DaoException, SQLException {
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
                        .getEnrolleeIdByUserId(serviceParam
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
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrolleeDaoImpl, entranceExaminationDaoImpl, schoolMarkDaoImpl,
                schoolSubjectDaoImpl, userDaoImpl, specialityDaoImpl);
        try {
            ServiceParam serviceParam =
                    new ServiceParam(sessionRequestContent, enrolleeDaoImpl,
                            entranceExaminationDaoImpl, schoolMarkDaoImpl, schoolSubjectDaoImpl,
                            userDaoImpl, specialityDaoImpl);
            String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
            User user = userDaoImpl.findUserByEmail(email);
            String userSpeciality = serviceParam
                    .getEnrolleeEducationParameter(sessionRequestContent
                            .getParameter(PARAM_NAME_SPECIALITY), TranslatorDataType.TRANSLATOR_SPECIALITY);
            int idUserSpeciality = specialityDaoImpl.getSpecialityIdByName(userSpeciality);
            serviceParam.setIdUser(user.getId());
            serviceParam.setIdUserSpeciality(idUserSpeciality);
            newEnrollee = registerEnrolleeValues(serviceParam);
            serviceParam.setIdEnrollee(newEnrollee.getId());
            registerEnrolleeEntranceExamination(serviceParam);
            registerEnrolleeSchoolMark(serviceParam);
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error while trying register enrollee: ", e);
            throw new ServiceException(e);
        } catch (SQLException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Error in sql when register enrollee: ", e);
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
            int idEnrollee = enrolleeDaoImpl.getEnrolleeIdByUserId(user.getId());
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
        serviceParam.setIdEnrollee(educationInformation.getIdEnrollee());
        educationInformation.setEnrolleeScore(getEnrolleeScore(serviceParam));
        educationEnrolleePosition(educationInformation, serviceParam);
        return educationInformation;
    }

    public EducationInformation getEnrolleeEducationInformation(SessionRequestContent sessionRequestContent, int idEnrolleeSpeciality) throws ServiceException {
        SpecialityDaoImpl specialityDaoImpl = DaoFactory.getInstance().getSpecialityDao();
        EnrolleeDaoImpl enrolleeDaoImpl = DaoFactory.getInstance().getEnrolleeDao();

        FacultyDaoImpl facultyDaoImpl = DaoFactory.getInstance().getFacultyDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(specialityDaoImpl, enrolleeDaoImpl, facultyDaoImpl);
        ServiceParam serviceParam = new ServiceParam(sessionRequestContent, enrolleeDaoImpl, specialityDaoImpl, facultyDaoImpl);
        serviceParam.setIdUserSpeciality(idEnrolleeSpeciality);
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
            enrolleesId = enrolleeDaoImpl.getEnrolleesIdByFacultyName(facultyName);
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
            List<Enrollee> enrollees = getListOfEnrolleesByConcreteFaculty(serviceParam);
            serviceParam.setEnrollees(enrollees);
            for (Enrollee enrollee : enrollees) {
                User user = userDaoImpl.findUserFirstLastNameEmailByUserId(enrollee.getIdUser());
                serviceParam.setIdUserSpeciality(enrollee.getIdSpeciality());
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

    private List<Enrollee> getListOfEnrolleesByConcreteFaculty(ServiceParam serviceParam) throws DaoException {
        EnrolleeDaoImpl enrolleeDaoImpl = serviceParam.getEnrolleeDaoImpl();
        String facultyName = getTranslatedFacultyName(serviceParam);
        List<Enrollee> enrolleeList = enrolleeDaoImpl.getEnrolleesByFacultyName(facultyName);
        getAvatarsForAllEnrollees(enrolleeList);
        return enrolleeList;
    }

    private void getAvatarsForAllEnrollees(List<Enrollee> enrollees) {
        for (Enrollee enrollee: enrollees) {
            enrollee.setAvatar(getEnrolleeAvatar(enrollee.getAvatar()));
        }
    }
}
