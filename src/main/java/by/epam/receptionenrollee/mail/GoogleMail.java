package by.epam.receptionenrollee.mail;

import com.sun.mail.smtp.SMTPTransport;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class GoogleMail {
    private static final Logger logger = LogManager.getLogger(GoogleMail.class);
    private String username;
    private String password;
    private String title;
    private String recipientMail;
    private String message;

    public GoogleMail(String username, String password, String title, String recipientMail, String message) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.recipientMail = recipientMail;
        this.message = message;
    }

    public void sendNotificationToEnrollee() {
        Properties sesiionConfig = GoogleMailProperties.getGoogleMailProperties();
        Session session = Session.getInstance(sesiionConfig);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientMail, false));
            mimeMessage.setSubject(title);
            mimeMessage.setText(message, "UTF-8");
            mimeMessage.setSentDate(new Date());
            try (SMTPTransport transport = (SMTPTransport) session.getTransport("smtps")) {
                transport.connect("smtp.gmail.com", username, password);
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            }
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error while trying to send message to enrollee: ", e);
        }
    }
}
