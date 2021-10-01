package by.epam.receptionenrollee.validator;

import by.epam.receptionenrollee.service.*;

import static by.epam.receptionenrollee.command.RequestParam.*;
import static by.epam.receptionenrollee.validator.ParamRegex.*;
import static by.epam.receptionenrollee.util.StringUtil.isParamsNotEmpty;
import static by.epam.receptionenrollee.util.StringUtil.isParamsNotNull;

public class Validator {
    private static boolean isValidAddressPart(String addressPart) {
        return addressPart.matches(STRING_REGEX);
    }

    private static boolean isValidAddress(SessionRequestContent sessionRequestContent) {
        return isValidAddressPart(sessionRequestContent.getParameter(PARAM_NAME_DISTRICT)) &&
                isValidAddressPart(sessionRequestContent.getParameter(PARAM_NAME_LOCALITY));
    }

    private static boolean isValidSchoolMarks(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent
                .getParametersByName(PARAM_NAME_SCHOOL_MARK)
                .stream()
                .allMatch(o -> o.matches(SCHOOL_MARK_REGEX));
    }

    private static boolean isValidEntranceExaminationMark(String mark) {
        return mark.matches(ENTRANCE_EXAMINATION_MARK_REGEX);
    }

    private static boolean isValidEntranceExaminationMarks(SessionRequestContent sessionRequestContent) {
        return isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_LANGUAGE_EXAM)) &&
                isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_FIRST_PROFILE_EXAM)) &&
                isValidEntranceExaminationMark(sessionRequestContent.getParameter(PARAM_NAME_SECOND_PROFILE_EXAM));
    }

    private static boolean isValidDay(String day) {
        return day.matches(DAY_REGEX);
    }

    private static boolean isValidMonth(String month) {
        return month.matches(MONTH_REGEX);
    }

    private static boolean isValidYear(String year) {
        return year.matches(YEAR_REGEX);
    }

    private static boolean isValidBirthday(SessionRequestContent sessionRequestContent) {
        return isValidDay(sessionRequestContent.getParameter(PARAM_NAME_DAY)) &&
                isValidMonth(sessionRequestContent.getParameter(PARAM_NAME_MONTH)) &&
                isValidYear(sessionRequestContent.getParameter(PARAM_NAME_YEAR));
    }

    public static boolean isCompetitionRegisterParamsValid(SessionRequestContent sessionRequestContent) {
        return isValidBirthday(sessionRequestContent) &&
                isValidAddress(sessionRequestContent) &&
                isValidSchoolMarks(sessionRequestContent) &&
                isValidEntranceExaminationMarks(sessionRequestContent);
    }

    public static boolean isLoginParamsValid(String login, String password) {
        return isParamsNotNull(login, password) && isParamsNotEmpty(login, password) &&
                isValidEmail(login) && isValidPassword(password);
    }

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    private static boolean isValidPassword(String username) {
        return username.matches(PASSWORD_REGEX);
    }

    private static boolean isValidName(String name) {
        return name.matches(FULL_NAME_REGEX);
    }

    private static boolean isValidFullName(String firstName, String middleName, String lastName) {
        return isValidName(firstName) && isValidName(middleName) && isValidName(lastName);
    }

    public static boolean isRegisterParamsValid(SessionRequestContent sessionRequestContent) {
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

    public static boolean isValidFacultyName(SessionRequestContent sessionRequestContent) {
        return sessionRequestContent
                .getParameter(PARAM_NAME_ENROLLEE_FACULTY)
                .matches(STRING_REGEX);
    }
}
