package by.epam.receptionenrollee.sql;

import org.intellij.lang.annotations.Language;

public class SqlQuery {
    @Language("MySQL")
    public static final String ENROLLEE_INSERT =
            "INSERT INTO enrollee " +
                    "(id_user_fk, id_speciality_fk, " +
                    "birthday, district, locality, avatar) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    @Language("MySQL")
    public static final String FIND_ENROLLEE_ID_BY_USER_ID =
            "SELECT id_enrollee FROM enrollee WHERE id_user_fk = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEE_BY_ID =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, birthday, district, locality, avatar, attempt " +
            "FROM enrollee " +
            "WHERE id_enrollee = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEES_ID_BY_FACULTY_NAME =
            "SELECT id_enrollee " +
            "FROM enrollee AS E " +
            "INNER JOIN speciality AS S ON E.id_speciality_fk = S.id_speciality " +
            "INNER JOIN faculty AS F ON S.id_faculty_fk = F.id_faculty " +
            "WHERE F.faculty_name = ?";

    @Language("MySQL")
    public static final String FIND_ALL_ENROLLEES =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, " +
                    "first_name, last_name, middle_name, email, " +
                    "birthday, district, locality, avatar " +
            "FROM enrollee AS E INNER JOIN user AS U ON E.id_user_fk = U.id_user";

    @Language("MySQL")
    public static final String ENTRANCE_EXAMINATION_INSERT =
            "INSERT INTO entrance_examination " +
                    "(id_enrollee_fk, language_mark, first_profile_exam_mark, second_profile_exam_mark) " +
                    "VALUES (?, ?, ?, ?)";

    @Language("MySQL")
    public static final String SUM_EXAMINATION =
            "SELECT (language_mark + first_profile_exam_mark + second_profile_exam_mark) AS sum_mark " +
                    "FROM entrance_examination " +
                    "WHERE id_enrollee_fk = ?";

    @Language("MySQL")
    public static final String SCHOOL_MARK_INSERT =
            "INSERT INTO school_mark (id_subject_mark_fk, id_enrollee_fk, mark) VALUES (?, ?, ?)";

    @Language("MySQL")
    public static final String AVG_SCHOOL_MARK =
            "SELECT AVG(mark)*10 AS enrollee_avg_mark " +
                    "FROM school_mark " +
                    "WHERE id_enrollee_fk = ?";

    @Language("MySQL")
    public static final String FIND_SCHOOL_SUBJECTS =
            "SELECT id_school_subject, subject_name FROM school_subject";

    @Language("MySQL")
    public static final String FIND_SPECIALITY_ID_BY_SPECIALITY_NAME_FACULTY_NAME =
            "SELECT id_speciality " +
            "FROM speciality AS s " +
            "INNER JOIN faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "WHERE speciality_name = ? AND faculty_name = ?";

    @Language("MySQL")
    public static final String FIND_SPECIALITY_FACULTY_BY_SPECIALITY_ID =
            "SELECT id_enrollee, speciality_name, faculty_name " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "WHERE id_speciality = ?";

    @Language("MySQL")
    public static final String FIND_FACULTY_PLAN =
            "SELECT (SUM(plan)) AS faculty_plan " +
                    "FROM speciality " +
                    "WHERE id_faculty_fk = " +
                    "(SELECT id_faculty " +
                    "FROM faculty " +
                    "WHERE faculty_name = ?)";

    @Language("MySQL")
    public static final String FIND_ALL_USERS =
            "SELECT id_user, email, password, role FROM user";

    @Language("MySQL")
    public static final String FIND_USER_ID_BY_EMAIL =
            "SELECT id_user FROM user WHERE email = ?";

    @Language("MySQL")
    public static final String USER_INSERT =
            "INSERT INTO user (first_name, last_name, middle_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

    @Language("MySQL")
    public static final String USER_UPDATE =
            "UPDATE user SET `first_name` = ?, `last_name` = ?, `middle_name` = ?, `email` = ?, `password` = ?, `role` = ? WHERE `id_user` = ?";

    @Language("MySQL")
    public static final String USER_DELETE =
            "DELETE FROM user WHERE email = ?";

    @Language("MySQL")
    public static final String FIND_USER_BY_ID =
            "SELECT id_user, first_name, last_name, middle_name, email, password, role FROM user WHERE id_user = ?";

    @Language("MySQL")
    public static final String FIND_USER_BY_EMAIL =
            "SELECT id_user, first_name, last_name, middle_name, email, password, role FROM user WHERE email = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEES_BY_FACULTY_NAME =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, birthday, district, locality, avatar " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "WHERE `faculty_name` = ?";

    @Language("MySQL")
    public static final String FIND_FACULTY_NAME_BY_ENROLLEE_ID_SPECIALITY =
            "SELECT faculty_name " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "WHERE id_speciality = ?";

    @Language("MySQL")
    public static final String FIND_USER_FIRST_LAST_NAME_EMAIL_BY_USER_ID =
            "SELECT first_name, last_name, email " +
            "FROM user " +
            "WHERE id_user = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEE_SCORE_BY_ID =
            "SELECT FLOOR(AVG(mark)*10 + (language_mark + first_profile_exam_mark + second_profile_exam_mark)) AS score " +
            "FROM enrollee AS e " +
            "INNER JOIN school_mark AS shM ON shM.id_enrollee_fk = e.id_enrollee " +
            "INNER JOIN entrance_examination AS enExam ON shM.id_enrollee_fk = enExam.id_enrollee_fk " +
            "WHERE id_enrollee = ?";

    @Language("MySQL")
    public static final String FIND_FIRST_LAST_NAME_SPECIALITY_FACULTY_BY_EMAIL =
            "SELECT first_name, last_name, speciality_name, faculty_name " +
            "FROM user AS u " +
            "INNER JOIN enrollee AS e ON u.id_user = e.id_user_fk " +
            "INNER JOIN speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "WHERE email = ?";

    @Language("MySQL")
    public static final String NOTIFICATION_INSERT =
            "INSERT INTO notification (id_enrollee_fk, notification) " +
            "VALUES (?, ?)";

    @Language("MySQL")
    public static final String FIND_ENROLLED_STUDENTS_BY_FACULTY_NAME =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, birthday, district, locality, avatar " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "INNER JOIN " +
            "notification AS n ON n.id_enrollee_fk = e.id_enrollee " +
            "WHERE faculty_name = ? AND notification = 1";

    @Language("MySQL")
    public static final String FIND_SPECIALITY_BY_ID =
            "SELECT id_speciality, id_faculty_fk, speciality_name, plan " +
            "FROM speciality " +
            "WHERE id_speciality = ?";

    @Language("MySQL")
    public static final String UPDATE_ENROLLEE_STATUS =
    "UPDATE notification " +
    "SET `notification` = ? " +
    "WHERE id_enrollee_fk = " +
            "(SELECT id_enrollee " +
            "FROM enrollee AS e " +
            "INNER JOIN user AS u ON e.id_user_fk = u.id_user " +
            "WHERE `email` = ?)";

    @Language("MySQL")
    public static final String FIND_NOT_ENROLLED_STUDENTS_BY_FACULTY_NAME =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, birthday, district, locality, avatar, attempt " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "INNER JOIN " +
            "notification AS n ON n.id_enrollee_fk = e.id_enrollee " +
            "WHERE faculty_name = ? AND notification = 0";

    @Language("MySQL")
    public static final String USER_UPDATE_FULL_NAME =
            "UPDATE user SET `first_name` = ?, `last_name` = ?, `middle_name` = ? WHERE `email` = ?";

    @Language("MySQL")
    public static final String ENROLLEE_UPDATE_SPECIALITY =
            "UPDATE enrollee SET `id_speciality_fk` = ? WHERE `id_user_fk` = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEE_BY_USER_ID =
            "SELECT id_enrollee, id_user_fk, id_speciality_fk, birthday, district, locality, avatar, attempt " +
            "FROM enrollee " +
            "WHERE id_user_fk = ?";

    @Language("MySQL")
    public static final String ENROLLEE_UPDATE_DATE =
            "UPDATE enrollee SET `birthday` = ? WHERE `id_user_fk` = ?";

    @Language("MySQL")
    public static final String FIND_ENROLLEE_ATTEMPT_BY_EMAIL =
            "SELECT attempt " +
            "FROM enrollee AS e " +
            "INNER JOIN user AS u ON e.id_user_fk = u.id_user " +
            "WHERE email = ?";

    @Language("MySQL")
    public static final String FIND_SPECIALITY_FACULTY_BY_USER_ID =
            "SELECT speciality_name, faculty_name " +
            "FROM enrollee AS e " +
            "INNER JOIN " +
            "speciality AS s ON e.id_speciality_fk = s.id_speciality " +
            "INNER JOIN " +
            "faculty AS f ON s.id_faculty_fk = f.id_faculty " +
            "INNER JOIN " +
            "user AS u ON e.id_user_fk = u.id_user " +
            "WHERE u.id_user = ?";

    @Language("MySQL")
    public static final String ENROLLEE_UPDATE_ATTEMPT =
            "UPDATE enrollee SET `attempt` = `attempt` - 1 WHERE `id_user_fk` = ?";

    private SqlQuery() {}
}
