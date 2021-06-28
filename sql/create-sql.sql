CREATE TABLE user
(
    id_user   MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    email     VARCHAR(40) NOT NULL,
    password  VARCHAR(100) NOT NULL,
    role VARCHAR(7) NOT NULL
);

CREATE TABLE school_subject
(
    id_school_subject MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    school_subject_name VARCHAR(60) NOT NULL
);

CREATE TABLE faculty
(
    id_faculty MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    faculty_name VARCHAR(90) NOT NULL,
    test_one VARCHAR(20),
    test_two VARCHAR(20),
    test_three VARCHAR(20)
);

CREATE TABLE speciality
(
    id_speciality MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_faculty_fk MEDIUMINT NOT NULL,
    speciality_name VARCHAR(130) NOT NULL,
    plan INT NOT NULL,
    FOREIGN KEY (id_faculty_fk) REFERENCES faculty (id_faculty)
);

CREATE TABLE enrollee
(
    id_enrollee MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_user_fk MEDIUMINT NOT NULL,
    id_speciality_fk MEDIUMINT NOT NULL,
    birthday DATE NOT NULL,
    district VARCHAR(30) NOT NULL,
    locality VARCHAR(30) NOT NULL,
    avatar VARCHAR(255),
    attempt INT NOT NULL,
    FOREIGN KEY (id_user_fk) REFERENCES user (id_user),
    FOREIGN KEY (id_speciality_fk) REFERENCES speciality (id_speciality)
);

CREATE TABLE school_mark
(
    id_school_mark MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_subject_mark_fk MEDIUMINT NOT NULL,
    id_enrollee_fk MEDIUMINT NOT NULL,
    mark INT NOT NULL,
    FOREIGN KEY (id_subject_mark_fk) REFERENCES school_subject (id_school_subject),
    FOREIGN KEY (id_enrollee_fk) REFERENCES enrollee (id_enrollee)
);

CREATE TABLE notification
(
    id_notification MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_enrollee_fk MEDIUMINT NOT NULL,
    notification TINYINT(1) NOT NULL,
    FOREIGN KEY (id_enrollee_fk) REFERENCES enrollee (id_enrollee)
);

CREATE TABLE entrance_examination
(
    id_entrance_examination MEDIUMINT AUTO_INCREMENT PRIMARY KEY,
    id_enrollee_fk MEDIUMINT NOT NULL,
    language_mark INT NOT NULL,
    first_profile_exam_mark INT NOT NULL,
    second_profile_exam_mark INT NOT NULL,
    FOREIGN KEY (id_enrollee_fk) REFERENCES enrollee (id_enrollee)
);

INSERT INTO user (first_name, last_name, middle_name, email, password, role) VALUES ('Инна', 'Примова', 'Михайловна', 'mmf.inna@gmail.com', '$2a$10$1CivJR4cfpsR4X7vmxbqoesaNfSpxYRiUw6n2nWq/RUzHNRwO6Mze', 'ADMIN');

INSERT INTO school_subject (subject_name) VALUES ('Белорусский язык');
INSERT INTO school_subject (subject_name) VALUES ('Белорусская литература');
INSERT INTO school_subject (subject_name) VALUES ('Русский язык');
INSERT INTO school_subject (subject_name) VALUES ('Русская литература');
INSERT INTO school_subject (subject_name) VALUES ('Иностранный язык');
INSERT INTO school_subject (subject_name) VALUES ('Математика');
INSERT INTO school_subject (subject_name) VALUES ('Иформатика');
INSERT INTO school_subject (subject_name) VALUES ('История Беларуси');
INSERT INTO school_subject (subject_name) VALUES ('Всемирная История');
INSERT INTO school_subject (subject_name) VALUES ('Обществоведение');
INSERT INTO school_subject (subject_name) VALUES ('География');
INSERT INTO school_subject (subject_name) VALUES ('Биология');
INSERT INTO school_subject (subject_name) VALUES ('Физика');
INSERT INTO school_subject (subject_name) VALUES ('Астрономия');
INSERT INTO school_subject (subject_name) VALUES ('Химия');
INSERT INTO school_subject (subject_name) VALUES ('Допризывная и медицинская подготовка');


INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Биологический факультет', 'язык', 'билогия', 'химия');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Исторический факультет', 'язык', 'история Беларуси', 'Всемирная история новейшего времени');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Международный государственный экологический институт имени А. Д. Сахарова', 'язык', 'математика', 'физика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Механико-математический факультет', 'язык', 'математика', 'физика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет географии и геоинформатики', 'язык', 'география', 'физика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет журналистики', 'язык', 'обществоведение', 'история Беларуси');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет международных отношений', 'язык', 'иностранный язык', 'обществоведение');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет прикладной математики и информатики', 'язык', 'математика', 'физика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет радиофизики и компьютерных технологий', 'язык', 'математика', 'физика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Факультет философии и социальных наук', 'язык', 'обществоведение', 'история Беларуси');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Физический факультет', 'язык', 'физика', 'математика');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Филологический факультет', 'язык', 'история Беларуси', 'литература');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Химический факультет', 'язык', 'математика', 'химия');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Экономический факультет', 'язык', 'математика', 'иностранный язык');
INSERT INTO faculty (faculty_name, test_one, test_two, test_three) VALUES ('Юридический факультет', 'язык', 'обществоведение', 'иностранный язык');


INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'биология (направление - биотехнология),', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'биология (направление - научно-педагогическая деятельность),', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'биология (направление - научно-производственная деятельность),', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'биохимия,', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'биоэкология,', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (1, 'микробиология', 10);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (2, 'документоведение (по направлениям)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (2, 'историко-архивоведение', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (2, 'история (по направлениям)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (2, 'музейное дело и охрана историко-культурного наследия (по направлениям)', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'информационные системы и технологии (в здравоохранении)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'информационные системы и технологии (в экологии)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'медико-биологическое дело', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'медицинская экология', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'медицинская физика', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'природоохранная деятельность (по направлениям)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'энергоэффективные технологии и энергетический менеджмент', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (3, 'ядерная и радиационная безопасность', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'компьютерная математика и системный анализ', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика (направление - научно-конструкторская деятельность)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика (направление - научно-педагогическая деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика (направление - научно-производственная деятельность)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика (направление - экономическая деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика и информационные технологии (направление - веб-программирование и интернет-технологии)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'математика и информационные технологии (направление - математическое и программное обеспечение мобильных устройств)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'механика и математическое моделирование', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (4, 'механика и математическое моделирование - совместная программа БГУ и ДПУ', 10);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'география (по направлениям)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'геоинформационные системы (направление - земельно-кадастровые)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'геология и разведка месторождений полезных ископаемых', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'геоэкология', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'гидрометеорология', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (5, 'космоаэрокартография', 10);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (6, 'информация и коммуникация', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'лингвострановедение', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'международное право', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'международные отношения', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'менеджмент (направление - менеджмент в сфере международного туризма)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'мировая экономика', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (7, 'таможенное дело', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'актуарная математика', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'информатика', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'компьютерная безопасность (направление - математические методы и программные системы)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'прикладная информатика (направление - программное обеспечение компьютерных систем)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'прикладная математика (направление - научно-производственная деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (8, 'экономическая кибернетика (направление - математические методы и компьютерное моделирование в экономике)', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (9, 'аэрокосмические радиоэлектронные и информационные системы и технологии', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (9, 'компьютерная безопасность (направление - радиофизические методы и программно-технические средства)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (9, 'прикладная информатика (направление - информационные технологии телекоммуникационных систем)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (9, 'радиофизика', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (9, 'физическая электроника', 10);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (10, 'психология', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (10, 'социальная работа (по направлениям)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (10, 'социальные коммуникации', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (10, 'социология', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (10, 'философия', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'компьютерная физика', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'физика (направление - научно-исследовательская деятельность)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'физика (направление - производственная деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'физика (направление - производственная деятельность) - совместная программа БГУ и ДПУ', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'физика наноматериалов и нанотехнологий', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (11, 'ядерные физика и технологии', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (12, 'романо-германская (английская) филология', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (12, 'восточная (китайская) филология', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (12, 'романо-германская (итальянская) филология', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (12, 'романо-германская (немецкая) филология', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (12, 'романо-германская (французская) филология', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'фундаментальная химия', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'химия (направление - научно-педагогическая деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'химия (направление - научно-производственная деятельность)', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'химия (направление - фармацевтическая деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'химия высоких энергий', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (13, 'химия лекарственных соединений', 10);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (14, 'менеджмент (направления - международный менеджмент)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (14, 'финансы и кредит', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (14, 'экономика', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (14, 'экономическая информатика', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (14, 'экономическая теория', 12);

INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (15, 'политология (направление - политико-юридическая деятельность)', 10);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (15, 'правоведение', 12);
INSERT INTO speciality (id_faculty_fk, speciality_name, plan) VALUES (15, 'экономическое право', 10);
