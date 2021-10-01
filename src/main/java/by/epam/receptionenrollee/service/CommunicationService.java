package by.epam.receptionenrollee.service;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.ServiceException;
import by.epam.receptionenrollee.mail.GoogleMail;
import by.epam.receptionenrollee.manager.ConfigurationManager;
import by.epam.receptionenrollee.manager.DatabaseManager;
import by.epam.receptionenrollee.manager.MessageManager;
import by.epam.receptionenrollee.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.epam.receptionenrollee.command.PagePath.*;
import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.command.RequestParam.PARAM_NAME_ERROR_EMAIL_EXISTS_MESSAGE;

public class CommunicationService {
    private static final Logger logger = LogManager.getLogger(CommunicationService.class);
    private EnrolleeService enrolleeService;
    private UserService userService;

    public CommunicationService() {
        this.enrolleeService = new EnrolleeService();
        this.userService = new UserService();
    }

    public String getCompetitionRegisterPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        if (Validator.isRegisterParamsValid(sessionRequestContent)) {
            String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
            try {
                if (userService.verifyUserEmail(email)) {
                    User registeredUser = userService.registerUser(sessionRequestContent);
                    if (registeredUser != null) {
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, registeredUser);
                        sessionRequestContent.setSessionAttribute(PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER, email);
                        page = ConfigurationManager.getProperty(COMPITITION_REGISTER);
                    }
                } else {
                    sessionRequestContent.setRequestAttribute(
                            PARAM_NAME_ERROR_EMAIL_EXISTS_MESSAGE,
                            new MessageManager(userLocale).getProperty("message.register.error.email"));
                    page = ConfigurationManager.getProperty(GO_TO_REGISTER);
                    logger.log(Level.WARN, "User with such email: \"" + email + "\" already exists");
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while get competition register page: ", e);
            }
        } else {
            setErrorRegisterMessage(sessionRequestContent, userLocale);
            page = ConfigurationManager.getProperty(GO_TO_REGISTER);
            logger.log(Level.ERROR, "Parameters are not valid.");
        }
        return page;
    }

    private void setErrorRegisterMessage(SessionRequestContent sessionRequestContent, String userLocale) {
        sessionRequestContent.setRequestAttribute(PARAM_NAME_ERROR_INVALIDATE_REGISTER_FIELDS,
                new MessageManager(userLocale).getProperty("message.register.error.invalid_fields"));
    }

    public String getPageToNotifyEnrollee(SessionRequestContent sessionRequestContent) {
        String page = null;
        String enrolleeEmail = sessionRequestContent.getParameter(PARAM_NAME_ENROLLEE_EMAIL);
        sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
        sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
        sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
        sessionRequestContent.setSessionAttribute(PARAM_NAME_ENROLLEE_EMAIL, enrolleeEmail);
        try {
            EducationInformation educationInformation = userService.getInformationToNotifyEnrollee(enrolleeEmail);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformation);
            page = ConfigurationManager.getProperty(NOTIFICATION_ENROLLEE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while trying get page to notify enrollee: ", e);
        }
        return page;
    }

    public String getVerifiedUserPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String login = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getParameter(PARAM_NAME_PASSWORD);
        String userLocale = sessionRequestContent.getUserLocale();
        try {
            User user = userService.getUserByEmailPassword(login, password);
            if (Validator.isLoginParamsValid(login, password) && user != null) {
                RoleEnum userRole = user.getRoleEnum();
                if (!(userRole == null || userRole.equals(RoleEnum.UNKNOWN))) {
                    if (userRole.equals(RoleEnum.ADMIN)) {
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER_FIRST_NAME, user.getFirstName());
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER_LAST_NAME, user.getLastName());
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_ROLE, userRole);
                        page = ConfigurationManager.getProperty(ADMIN);
                    } else if (userRole.equals(RoleEnum.USER)) {
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
                        sessionRequestContent.setRequestAttribute(PARAM_NAME_ROLE, userRole);
                        enrolleeInformationAfterLogin(sessionRequestContent, user);
                        page = ConfigurationManager.getProperty(USER);
                    }
                } else {
                    sessionRequestContent.setRequestAttribute(
                            PARAM_NAME_ERROR_UNKNOWN_USER, new MessageManager(userLocale)
                                    .getProperty("message.unknown_user"));
                    page = ConfigurationManager.getProperty(LOGIN);
                }
            } else {
                sessionRequestContent.setRequestAttribute(
                        PARAM_NAME_ERROR_LOGIN_MESSAGE, new MessageManager(userLocale)
                                .getProperty("message.loginerror"));
                page = ConfigurationManager.getProperty(LOGIN);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while verified user page: ", e);
        }
        return page;
    }

    private void enrolleeInformationAfterLogin(SessionRequestContent sessionRequestContent, User user) throws ServiceException {
        Enrollee enrollee = enrolleeService.getEnrollee(user);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, enrollee);
        enrolleeInformation(sessionRequestContent, enrollee);
    }

    private void enrolleeInformation(SessionRequestContent sessionRequestContent, Enrollee enrollee) throws ServiceException {
        String avatar = enrolleeService.getEnrolleeAvatar(enrollee.getAvatar());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_AVATAR, avatar);
        EducationInformation educationInformation = enrolleeService.getEnrolleeEducationInformation(sessionRequestContent, enrollee);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformation);
    }

    public String setDataBeforeGoToEdit(SessionRequestContent sessionRequestContent) {
        String page = null;
        try {
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_AVATAR, sessionRequestContent.getParameter(PARAM_NAME_AVATAR));
            String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
            sessionRequestContent.setSessionAttribute(PARAM_NAME_LOGIN, email);
            int attempt = enrolleeService.getEnrolleeAttempt(email);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_ATTEMPT, attempt);
            page = ConfigurationManager.getProperty(GO_TO_EDIT_ENROLLEE);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while trying set data before go to edit page: ", e);
        }
        return page;
    }

    public String getEnrolleesByFacultyPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        Map<Enrollee, EducationInformation> informationMap;
        if (Validator.isValidFacultyName(sessionRequestContent)) {
            try {
                informationMap = enrolleeService.getInformationAboutEnrolleesByConcreteFaculty(sessionRequestContent);
                sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
                sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
                sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
                sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEES_INFORMATION_MAP, informationMap);
                sessionRequestContent.setRequestAttribute(PARAM_NAME_FACULTY_NAME, sessionRequestContent.getParameter(PARAM_NAME_ENROLLEE_FACULTY));
                page = ConfigurationManager.getProperty(ENROLLEES_BY_FACULTY);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while get enrollees by faculty page: ", e);
            }
        } else {
            sessionRequestContent.setRequestAttribute(
                    PARAM_NAME_ERROR_INVALID_FACULTY_NAME,
                    new MessageManager(userLocale).getProperty("message.admin.error.invalid_faculty"));
            page = ConfigurationManager.getProperty(ADMIN);
        }
        return page;
    }

    public String getPageEnrolledStudents(SessionRequestContent sessionRequestContent) {
        String page = null;
        try {
            List<EducationInformation> educationInformationList =
                    enrolleeService.getEnrolledStudentsListByFaculty(sessionRequestContent);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformationList);
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
            page = ConfigurationManager.getProperty(ENROLLED_STUDENTS_BY_FACULTY);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while trying get enrolled students page: ", e);
        }
        return page;
    }

    public String editEnrolleeData(SessionRequestContent sessionRequestContent) {
        String page = null;
        try {
            EditInformation editInformation = enrolleeService.changeEnrolleeInformation(sessionRequestContent);
            User updatedUser = editInformation.getUser();
            sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, updatedUser);
            sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
            Enrollee updatedEnrollee = editInformation.getEnrollee();
            sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, updatedEnrollee);
            String avatar = enrolleeService.getEnrolleeAvatar(updatedEnrollee.getAvatar());
            sessionRequestContent.setRequestAttribute(PARAM_NAME_AVATAR, avatar);
            EducationInformation educationInformation = editInformation.getEducationInformation();
            sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformation);
            page = ConfigurationManager.getProperty(USER);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while trying update student data: ", e);
        }
        return page;
    }

    public String getPageAfterSendingMessageToEnrollee(SessionRequestContent sessionRequestContent) {
        String page;
        String userLocale = sessionRequestContent.getUserLocale();
        String adminEmail = sessionRequestContent.getParameter(PARAM_NAME_ADMIN_EMAIL);
        String enrolleeEmail = sessionRequestContent.getParameter(PARAM_NAME_ENROLLEE_EMAIL);
        if (Validator.isValidEmail(adminEmail) && Validator.isValidEmail(enrolleeEmail)) {
            String title = sessionRequestContent.getParameter(PARAM_NAME_MESSAGE_SUBJECT);
            String message = sessionRequestContent.getParameter(PARAM_NAME_MESSAGE);
            GoogleMail googleMail = new GoogleMail(adminEmail, DatabaseManager.getProperty(DatabaseManager.PASSWORD), title, enrolleeEmail, message);
            googleMail.sendNotificationToEnrollee();
            try {
                String enrolleeStatus = sessionRequestContent.getParameter(ENROLLEE_STATUS);
                enrolleeService.changeEnrolleeStatus(enrolleeStatus, enrolleeEmail);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while trying change enrollee status: ", e);
            }
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
            page = ConfigurationManager.getProperty(CONFIRMATION);
        } else {
            setErrorRegisterMessage(sessionRequestContent, userLocale);
            page = ConfigurationManager.getProperty(NOTIFICATION_ENROLLEE);
        }
        return page;
    }

    public String getRegisterEnrolleePage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        if (Validator.isCompetitionRegisterParamsValid(sessionRequestContent)) {
            try {
                Enrollee enrollee = enrolleeService.registerEnrollee(sessionRequestContent);
                User user = userService.getUserById(enrollee.getIdUser());
                RoleEnum userRole = user.getRoleEnum();
                sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
                sessionRequestContent.setRequestAttribute(PARAM_NAME_ROLE, userRole);
                sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, enrollee);
                enrolleeInformation(sessionRequestContent, enrollee);
                page = ConfigurationManager.getProperty(USER);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while get register enrollee page: ", e);
            }
        } else {
            setErrorRegisterMessage(sessionRequestContent, userLocale);
            page = ConfigurationManager.getProperty(GO_TO_REGISTER);
            logger.log(Level.ERROR, "Parameters are not valid.");
        }
        return page;
    }

    public String setDataForBackToHomePageEnrollee(SessionRequestContent sessionRequestContent) {
        String page = null;
        try {
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_FIRST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_FIRST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_USER_LAST_NAME, sessionRequestContent.getParameter(PARAM_NAME_USER_LAST_NAME));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_ROLE, sessionRequestContent.getParameter(PARAM_NAME_ROLE));
            sessionRequestContent.setSessionAttribute(PARAM_NAME_AVATAR, sessionRequestContent.getParameter(PARAM_NAME_AVATAR));
            String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
            sessionRequestContent.setSessionAttribute(PARAM_NAME_LOGIN, email);
            User user = userService.getUserByEmail(email);
            enrolleeInformationAfterLogin(sessionRequestContent, user);
            page = ConfigurationManager.getProperty(USER);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while trying set data for back to home page enrollee: ", e);
        }
        return page;
    }
}
