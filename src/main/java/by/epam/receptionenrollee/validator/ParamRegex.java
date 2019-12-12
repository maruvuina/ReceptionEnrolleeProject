package by.epam.receptionenrollee.validator;

public class ParamRegex {
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+\\@[a-z0-9.-]+\\.[a-z]{2,4}$";
    public static final String FULL_NAME_REGEX = "^([А-Я][а-яё]{1,23}|[A-Z][a-z]{1,23})$";
    public static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
    public static final String DAY_REGEX = "(0[1-9]|[12]\\d|3[01])";
    public static final String MONTH_REGEX = "^(0?[1-9]|1[012])$";
    public static final String YEAR_REGEX = "^\\d{4}$";
    public static final String SCHOOL_MARK_REGEX = "^([1-9]|10)$";
    public static final String ENTRANCE_EXAMINATION_MARK_REGEX = "^(?:100|[1-9]?[0-9])$";
    public static final String STRING_REGEX = "^([^0-9]*)$";

}
