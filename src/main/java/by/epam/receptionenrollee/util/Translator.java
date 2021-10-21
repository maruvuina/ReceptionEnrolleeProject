package by.epam.receptionenrollee.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Translator {
    private static final String DEFAULT_LANGUAGE = String.valueOf(LanguageParam.RU_RU);
    private TranslatorDataType translatorDataType;

    private enum Language {
        EN, RU, BE
    }

    public Translator(TranslatorDataType translatorDataType) {
        this.translatorDataType = translatorDataType;
    }

    private String getDictionaryType() {
        return switch (translatorDataType) {
            case TRANSLATOR_FACULTY -> TranslationVocabulary.TRANSLATION_FACULTIES;
            case TRANSLATOR_SPECIALITY -> TranslationVocabulary.TRANSLATION_SPECIALITIES;
        };
    }

    private String getDictionaryToTranslate(Language lang) {
        return String.format(String.valueOf(getDictionaryType()), lang);
    }

    private BufferedReader getFileDictionary(String lang) {
        InputStream is =
                    Translator.class.getClassLoader().getResourceAsStream(getDictionary(lang));
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));
    }

    private String getDictionary(String lang) {
        LanguageParam countryLanguage = LanguageParam.valueOf(lang.toUpperCase());
        return switch (countryLanguage) {
            case RU_RU -> getDictionaryToTranslate(Language.RU);
            case EN_US -> getDictionaryToTranslate(Language.EN);
            case BE_BY -> getDictionaryToTranslate(Language.BE);
        };
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
}
