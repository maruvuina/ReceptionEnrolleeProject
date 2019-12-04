package by.epam.receptionenrollee.validator;

import by.epam.receptionenrollee.entity.Enrollee;
import by.epam.receptionenrollee.entity.RoleEnum;
import by.epam.receptionenrollee.entity.User;
import by.epam.receptionenrollee.logic.SessionRequestContent;
import by.epam.receptionenrollee.resource.ConfigurationManager;
import by.epam.receptionenrollee.resource.MessageManager;
import by.epam.receptionenrollee.service.EducationInformation;
import by.epam.receptionenrollee.service.EnrolleeService;
import by.epam.receptionenrollee.service.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Map;

import static by.epam.receptionenrollee.command.PagePath.*;
import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.validator.ParamRegex.*;
import static by.epam.receptionenrollee.validator.StringUtil.isParamsNotEmpty;
import static by.epam.receptionenrollee.validator.StringUtil.isParamsNotNull;

public class Validator {
    private static final Logger logger = Logger.getLogger(Validator.class);
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
        User user = userService.getUserByEmailPassword(login, password);
        if (isLoginParamsValid(login, password) && user != null) {
            RoleEnum userRole = user.getRoleEnum();
            if (!(userRole == null || userRole.equals(RoleEnum.UNKNOWN))) {
                if (userRole.equals(RoleEnum.ADMIN)) {
                    sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
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
        return page;
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
            //проверка полей на валидность
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
            }
        } else {
            logger.log(Level.ERROR, "Parameters are not valid.");
        }
        return page;
    }

    public String getRegisterEnrolleePage(SessionRequestContent sessionRequestContent) {
        String page;
        String userLocale = sessionRequestContent.getUserLocale();
        System.out.println("EMAIL enrollee-----> " + sessionRequestContent.getSessionParameter(PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER));
        Enrollee enrollee = enrolleeService.registerEnrollee(sessionRequestContent);
        //System.out.println("REGISTED enrollee-----> " + enrollee.toString());
        User user = userService.getUserById(enrollee.getIdUser());
        RoleEnum userRole = user.getRoleEnum();
        //проверка полей на валидность вместо null
        if (enrollee != null) {
            sessionRequestContent.setRequestAttribute(PARAM_NAME_USER, user);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_ROLE, userRole);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, enrollee);
            enrolleeInformation(sessionRequestContent, enrollee);
            page = ConfigurationManager.getProperty(USER);
        } else {
            //другой message
            sessionRequestContent.setRequestAttribute(
                    PARAM_NAME_ERROR_EMAIL_EXISTS_MESSAGE,
                    new MessageManager(userLocale).getProperty("message.register.error.email"));
            page = ConfigurationManager.getProperty(COMPITITION_REGISTER);
        }
        return page;
    }

    private void enrolleeInformation(SessionRequestContent sessionRequestContent, Enrollee enrollee) {
        String avatar = enrolleeService.getEnrolleeAvatar(enrollee.getAvatar());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_AVATAR, avatar);
        EducationInformation educationInformation = enrolleeService.getEnrolleeEducationInformation(sessionRequestContent, enrollee.getIdSpeciality());
        sessionRequestContent.setRequestAttribute(PARAM_NAME_EDUCATION_INFORMATION, educationInformation);
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_FACULTY, education.get(0));
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_SPECIALITY, education.get(1));
//        List<Integer> scoreRating = enrolleeService.getScoreRating(enrollee.getId(), education.get(0));
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_SCORE, scoreRating.get(2));
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_RATING, scoreRating.get(0));
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_WHOLE_RATING, scoreRating.get(1));
    }

    private void enrolleeInformationAfterLogin(SessionRequestContent sessionRequestContent, User user) {
        Enrollee enrollee = enrolleeService.getEnrollee(user);
        sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEE, enrollee);
        enrolleeInformation(sessionRequestContent, enrollee);
    }

    public String getEnrolleesByFacultyPage(SessionRequestContent sessionRequestContent) {
        String page;
        String userLocale = sessionRequestContent.getUserLocale();
        Map<Enrollee, EducationInformation> informationMap = enrolleeService.getInformationAboutEnrolleesByConcreteFaculty(sessionRequestContent);
        //List<Enrollee> enrollees = enrolleeService.getListOfEnrolleesByConcreteFaculty(sessionRequestContent);
        //User user = userService.getUserByEmail(sessionRequestContent.getParameter(PARAM_NAME_LOGIN_FROM_COMPITITION_REGISTER));
        //RoleEnum userRole = user.getRoleEnum();
        //проверка полей на валидность вместо null
        System.out.println("**********************");
        //enrollees.forEach(System.out::println);
        System.out.println("**********************");
        if (informationMap != null) {
            //enrolleesInformation(sessionRequestContent, enrollees);
            sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEES_INFORMATION_MAP, informationMap);
            page = ConfigurationManager.getProperty(ENROLLEES_BY_FACULTY);
        } else {
            //другой message
            sessionRequestContent.setRequestAttribute(
                    PARAM_NAME_ERROR_EMAIL_EXISTS_MESSAGE,
                    new MessageManager(userLocale).getProperty("message.register.error.email"));
            page = ConfigurationManager.getProperty(ADMIN);
        }
        return page;
    }
//    private void infoAboutEnrolleesForAdmin(SessionRequestContent sessionRequestContent) {
//        List<Enrollee> enrollees = enrolleeService.getListOfEnrollees();
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_ENROLLEES_LIST, enrollees);
//        List<String> faculties =
//                enrolleeService
//                        .getFacultiesOrSpecialitiesForEnrollees(enrollees, PARAM_NAME_FACULTY);
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_FACULTIES, faculties);
//        List<String> specialities =
//                enrolleeService
//                        .getFacultiesOrSpecialitiesForEnrollees(enrollees, PARAM_NAME_SPECIALITY);
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_SPECIALITIES, specialities);
//        List<Integer> scores =
//                enrolleeService.getScoreForEachEnrollee(enrollees);
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_SCORES, scores);
//        List<Integer> positions =
//                enrolleeService.getRatingForEachEnrollee(enrollees, "position");
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_POSITIONS, positions);
//        List<Integer> allSizeOfCurrentEnrollees =
//                enrolleeService.getRatingForEachEnrollee(enrollees, "all_size");
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_ALL_SIZE_OF_CURRENT_ENROLLEES, allSizeOfCurrentEnrollees);
//        List<Integer> facultyPlan =
//                enrolleeService.getRatingForEachEnrollee(enrollees, "faculty_plan");
//        sessionRequestContent.setRequestAttribute(PARAM_NAME_FACULTY_PLAN, facultyPlan);
////        List<Boolean> statuses = enrolleeService.getStatusForEachEnrollee(enrollees, faculties);
////        List<String> entered = new ArrayList<>();
////        for (Boolean status: statuses) {
////            if (status) {
////                entered.add("Entered");
////            }
////            else {
////                entered.add("No entered");
////            }
////        }
////        request.setAttribute("status", entered);
//    }
}
