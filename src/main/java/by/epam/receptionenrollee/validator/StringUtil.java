package by.epam.receptionenrollee.validator;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class StringUtil {
    public static boolean isNullOrEmpty(String string) {
        return isNull(string) || string.strip().isEmpty();
    }

    public static String randomString(int lenght) {
        String alphaNumericString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(lenght);
        for(int i = 0; i < lenght; i++) {
            stringBuilder.append(alphaNumericString.charAt(random.nextInt(alphaNumericString.length())));
        }
        return stringBuilder.toString();
    }

    public static String getCurrentDateTime() {
        DateFormat dateFormat =
                new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        return dateFormat.format(now);
    }

    public static boolean isParamsNotNull(String ... params) {
        return Stream.of(params).allMatch(Objects::nonNull);
    }

    public static boolean isParamsNotEmpty(String ... params) {
        return !Stream.of(params).allMatch(String::isEmpty);
    }

}
