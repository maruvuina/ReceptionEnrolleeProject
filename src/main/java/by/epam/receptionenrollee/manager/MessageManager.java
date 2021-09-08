package by.epam.receptionenrollee.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private Locale locale;

    public MessageManager(String language) {
        locale = new Locale(language.substring(0, 2), language.substring(language.length() - 2));
    }

    public String getProperty(String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle.getString(key);
    }
}
