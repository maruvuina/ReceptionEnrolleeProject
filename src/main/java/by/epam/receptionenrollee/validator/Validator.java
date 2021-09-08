package by.epam.receptionenrollee.validator;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.exception.ServiceException;
import by.epam.receptionenrollee.mail.GoogleMail;
import by.epam.receptionenrollee.service.*;
import by.epam.receptionenrollee.manager.DatabaseManager;
import by.epam.receptionenrollee.manager.ConfigurationManager;
import by.epam.receptionenrollee.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.epam.receptionenrollee.command.PagePath.*;
import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.validator.ParamRegex.*;
import static by.epam.receptionenrollee.util.StringUtil.isParamsNotEmpty;
import static by.epam.receptionenrollee.util.StringUtil.isParamsNotNull;


public class Validator {
    private static final Logger logger = LogManager.getLogger(Validator.class);
    private EnrolleeService enrolleeService;
    private UserService userService;

    public Validator() {
        this.enrolleeService = new EnrolleeService();
        this.userService = new UserService();
    }

    public String getVerifiedUserPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String login = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getParameter(PARAM_NAME_PASSWORD);
        String userLocale = sessionRequestContent.getUserLocale();
        try {
            User user = userService.getUserByEmailPassword(login, password);
            if (isLoginParamsValid(login, password) && user != null) {
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

    private boolean isValidAddressPart(String addressPart) {
        return addressPart.matches(STRING_REGEX);
    }

    private boolean isValidAddress(SessionRequestContent sessionRequestContent) {
        return isValidAddressPart(sessionRequestContent.getParameter(PARAM_NAME_DISTRICT)) &&
                isValidAddressPart(sessionRequestContent.getParameter(PARAM_NAME_LOCALITY));
    }

    private boolean isValidSchoolMarks(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent
                .getParametersByName(PARAM_NAME_SCHOOL_MARK)
                .stream()
                .allMatch(o -> o.matches(SCHOOL_MARK_REGEX));
    }

    private boolean isValidEntranceExaminationMark(String mark) {
        return mark.matches(ENTRANCE_EXAMINATION_MARK_REGEX);
    }

    private boolean isValidEntranceExaminationMarks(SessionRequestContent sessionRequestContent) {
        return isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_LANGUAGE_EXAM)) &&
                isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_FIRST_PROFILE_EXAM)) &&
                isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_SECOND_PROFILE_EXAM));
    }

    private boolean isValidDay(String day) {
        return day.matches(DAY_REGEX);
    }

    private boolean isValidMonth(String month) {
        return month.matches(MONTH_REGEX);
    }

    private boolean isValidYear(String year) {
        return year.matches(YEAR_REGEX);
    }

    private boolean isValidBirthday(SessionRequestContent sessionRequestContent) {
        return isValidDay(sessionRequestContent.getParameter(PARAM_NAME_DAY)) &&
                isValidMonth(sessionRequestContent.getParameter(PARAM_NAME_MONTH)) &&
                isValidYear(sessionRequestContent.getParameter(PARAM_NAME_YEAR));
    }

    private boolean isCompetitionRegisterParamsValid(SessionRequestContent sessionRequestContent) {
        return isValidBirthday(sessionRequestContent) &&
                isValidAddress(sessionRequestContent) &&
                isValidSchoolMarks(sessionRequestContent) &&
                isValidEntranceExaminationMarks(sessionRequestContent);
    }

    private boolean isLoginParamsValid(String login, String password) {
        return isParamsNotNull(login, password) && isParamsNotEmpty(login, password) &&
                isValidEmail(login) && isValidPassword(password);
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    private boolean isValidPassword(String username) {
        return username.matches(PASSWORD_REGEX);
    }

    private boolean isValidName(String name) {
        return name.matches(FULL_NAME_REGEX);
    }

    private boolean isValidFullName(String firstName, String middleName, String lastName) {
        return isValidName(firstName) && isValidName(middleName) && isValidName(lastName);
    }

    private boolean isRegisterParamsValid(SessionRequestContent sessionRequestContent) {
        String firstName = sessionRequestContent.getParameter(PARAM_NAME_FULLNAME);
        String middleName = sessionRequestContent.getParameter(PARAM_NAME_MIDDLENAME);
        String lastName = sessionRequestContent.getParameter(PARAM_NAME_SURNAME);
        String email = sessionRequestContent.getParameter(PARAM_NAME_LOGIN);
        String password = sessionRequestContent.getParameter(PARAM_NAME_PASSWORD);
        return isParamsNotNull(firstName, lastName, middleName, email, password) &&
                isParamsNotEmpty(firstName, lastName, middleName, email, password) &&
                isValidFullName(firstName, middleName, lastName) && isValidEmail(email) &&
                isValidPassword(password);
    }

    public String getCompititionRegisterPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        if (isRegisterParamsValid(sessionRequestContent)) {
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
                logger.log(Level.ERROR, "Error while get compitition register page: ", e);
            }
        } else {
            setErrorRegisterMessage(sessionRequestContent, userLocale);
            page = ConfigurationManager.getProperty(GO_TO_REGISTER);
            logger.log(Level.ERROR, "Parameters are not valid.");
        }
        return page;
    }

    private void setErrorRegisterMessage(SessionRequestContent sessionRequestContent, String userLocale) {
        sessionRequestContent.setRequestAttribute(
                PARAM_NAME_ERROR_INVALIDATE_REGISTER_FIELDS,
                new MessageManager(userLocale).getProperty("message.register.error.invalid_fields"));
    }

    public String getRegisterEnrolleePage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        if (isCompetitionRegisterParamsValid(sessionRequestContent)) {
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

    private void enrolleeInformation(SessionRequestContent sessionRequestContent, Enrollee enrollee) throws ServiceException {
        String avatar = enrolleeService.getEnrolleeAvatar(enrollee.getAvatar());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_AVATAR, avatar);
        EducationInformation educationInformation = enrolleeService.getEnrolleeEducationInformation(sessionRequestContent, enrollee);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformation);
    }

    private void enrolleeInformationAfterLogin(SessionRequestContent sessionRequestContent, User user) throws ServiceException {
        Enrollee enrollee = enrolleeService.getEnrollee(user);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, enrollee);
        enrolleeInformation(sessionRequestContent, enrollee);
    }

    private boolean isValidFacultyName(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent
                .getParameter(PARAM_NAME_ENROLLEE_FACULTY)
                .matches(STRING_REGEX);
    }

    public String getEnrolleesByFacultyPage(SessionRequestContent sessionRequestContent) {
        String page = null;
        String userLocale = sessionRequestContent.getUserLocale();
        Map<Enrollee, EducationInformation> informationMap;
        if (isValidFacultyName(sessionRequestContent)) {
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

    public String getPageAfterSendingMessageToEnrollee(SessionRequestContent sessionRequestContent) {
        String page;
        String userLocale = sessionRequestContent.getUserLocale();
        String adminEmail = sessionRequestContent.getParameter(PARAM_NAME_ADMIN_EMAIL);
        String enrolleeEmail = sessionRequestContent.getParameter(PARAM_NAME_ENROLLEE_EMAIL);
        if (isValidEmail(adminEmail) && isValidEmail(enrolleeEmail)) {
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
