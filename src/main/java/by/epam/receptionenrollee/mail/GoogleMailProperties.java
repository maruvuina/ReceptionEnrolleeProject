package by.epam.receptionenrollee.mail;

import by.epam.receptionenrollee.manager.GoogleMailManager;

import java.util.Properties;

class GoogleMailProperties {
    static Properties getGoogleMailProperties() {
        Properties properties = new Properties();
        properties.put(GoogleMailManager.MAIL_SMTPS_HOST, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTPS_HOST));
        properties.put(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_CLASS, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_CLASS));
        properties.put(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_FALLBACK, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_FALLBACK));
        properties.put(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_PORT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_SOCKET_FACTORY_PORT));
        properties.put(GoogleMailManager.MAIL_SMTP_PORT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_PORT));
        properties.put(GoogleMailManager.MAIL_SMTPS_AUTH, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTPS_AUTH));
        properties.put(GoogleMailManager.MAIL_TRANSPORT_PROTOCOL, GoogleMailManager.getProperty(GoogleMailManager.MAIL_TRANSPORT_PROTOCOL));
        properties.put(GoogleMailManager.MAIL_SMTP_QUITWAIT, GoogleMailManager.getProperty(GoogleMailManager.MAIL_SMTP_QUITWAIT));
        return properties;
    }
}
