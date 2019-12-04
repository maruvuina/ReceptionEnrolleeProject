package by.epam.receptionenrollee.util;

import by.epam.receptionenrollee.exception.TranslationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.pattern.LineSeparatorPatternConverter;

public class Translator {
    private static final Logger logger = LogManager.getLogger(Translator.class);
    private static final String DEFAULT_LANGUAGE = String.valueOf(LanguageParam.RU_RU);
    private TranslatorDataType translatorDataType;

    private enum Language {
        EN, RU, BE
    }

    public Translator(TranslatorDataType translatorDataType) {
        this.translatorDataType = translatorDataType;
    }

    private String getDictionaryType() throws TranslationException {
        String dicrionaryName;
        switch (translatorDataType) {
            case TRANSLATOR_FACULTY:
                dicrionaryName = TranslationVocabulary.TRANSLATION_FACULTIES;
                break;
            case TRANSLATOR_SPECIALITY:
                dicrionaryName = TranslationVocabulary.TRANSLATION_SPECIALITIES;
                break;
            default:
                throw new TranslationException("No such dictionary.");
        }
        return dicrionaryName;
    }

    private String getDictionaryToTranslate(Language lang) throws TranslationException {
        return String.format(String.valueOf(getDictionaryType()), lang);
    }

    private BufferedReader getFileDictionary(String lang) {
        InputStream is =
                    Translator.class.getClassLoader().getResourceAsStream(getDictionary(lang));
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));
    }

    private String getDictionary(String lang) {
        String dictionary = null;
        LanguageParam countryLanguage = LanguageParam.valueOf(lang.toUpperCase());
        try {
            switch (countryLanguage) {
                case RU_RU:
                    dictionary = getDictionaryToTranslate(Language.RU);
                    break;
                case EN_US:
                    dictionary = getDictionaryToTranslate(Language.EN);
                    break;
                case BE_BY:
                    dictionary = getDictionaryToTranslate(Language.BE);
                    break;
            }
        } catch (TranslationException e) {
            logger.log(Level.ERROR, "Error while trying to get dictionary: ", e);
        }
        return dictionary;
    }

    private Map<String, String> fillDictionary(Map<String, String> dictionary, String lang) {
        try(BufferedReader readerToTranslate = getFileDictionary(lang);
            BufferedReader readerDefault = getFileDictionary(DEFAULT_LANGUAGE)) {
            String lineToTranslate;
            String defaultLanguage;
            while ((lineToTranslate = readerToTranslate.readLine()) != null &&
                    (defaultLanguage = readerDefault.readLine()) != null) {
                if (lineToTranslate.trim().length() > 0 &&
                        defaultLanguage.trim().length() > 0) {
                    dictionary.put(lineToTranslate, defaultLanguage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private Map<String, String> getFormedDictionary(String lang) {
        Map<String, String> vocabulary = new HashMap<>();
        return fillDictionary(vocabulary, lang);
    }

    public String translate(String language, String stringToTranslate) {
        Map<String, String> vocabulary = getFormedDictionary(language);
        return vocabulary.get(stringToTranslate);
    }

    public static void main(final String[] args) {

//        Translator translator = new Translator();
//        String translate =
//                translator.translate("be_BY",
//                        "мікрабіялогія");
//        System.out.println(translate);


//        String regex = "^([А-Я][а-яё]{1,23}|[A-Z][a-z]{1,23})$";
//        String test = "Вершинина";
//        boolean b = test.matches(regex);
//        System.out.println(b);
    }
}
