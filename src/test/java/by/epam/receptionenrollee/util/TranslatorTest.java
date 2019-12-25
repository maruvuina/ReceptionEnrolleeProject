package by.epam.receptionenrollee.util;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TranslatorTest {
    private Translator translatorFaculty;
    private Translator translatorSpeciality;

    @BeforeMethod
    public void initParameters() {
        translatorFaculty = new Translator(TranslatorDataType.TRANSLATOR_FACULTY);
        translatorSpeciality = new Translator(TranslatorDataType.TRANSLATOR_SPECIALITY);
    }

    @DataProvider
    Object[][] facultyNameInDifferentLanguages() {
        return new Object[][]
                {
                        { "EN_US", "Faculty of Mechanics and Mathematics" },
                        { "RU_RU", "Механико-математический факультет" },
                        { "BE_BY", "Механіка-матэматычны факультэт" }
                };
    }

    @DataProvider
    Object[][] specialityNameInDifferentLanguages() {
        return new Object[][]
                {
                        { "EN_US", "mathematics and information technologies (study field - web-programming and internet-technologies)" },
                        { "RU_RU", "математика и информационные технологии (направление - веб-программирование и интернет-технологии)" },
                        { "BE_BY", "матэматыка і інфармацыйныя тэхналогіі (напрамак - вэб-праграмаванне і інтэрнэт-тэхналогіі)" }
                };
    }

    @Test(dataProvider = "facultyNameInDifferentLanguages")
    public void isFacultyNameTranslatedSuccessfulInRussian(String languageName, String facultyNameForTranslate) {
        assertThat(translatorFaculty.translate(languageName, facultyNameForTranslate),
                is(equalTo("Механико-математический факультет")));
    }

    @Test(dataProvider = "specialityNameInDifferentLanguages")
    public void isSpecialityNameTranslatedSuccessfulInRussian(String languageName, String specialityNameForTranslate) {
        assertThat(translatorSpeciality.translate(languageName, specialityNameForTranslate),
                is(equalTo("математика и информационные технологии (направление - веб-программирование и интернет-технологии)")));
    }
}