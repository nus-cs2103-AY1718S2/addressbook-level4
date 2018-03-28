package seedu.address.commons.core;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send personalized emails to drivers and customers
 */
public class Mailer {

    private static String pigeonsMail = "pigeonscs2103@gmail.com";
    private static String password = "Pigeons2103";
    //private static String defaultRecipient = "delepine.matthieu@gmail.com";
    //private static String defaultRecipient = "matthieu2301@hotmail.fr";
    private static String defaultRecipient = pigeonsMail;

    /**
     * Send an email to the
     * @param recipients
     */
    public static boolean email(List<Person> recipients) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", pigeonsMail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(pigeonsMail));

            for (Person p: recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(p.getEmail().toString()));

                String subject = "Your delivery is arriving today!";
                String body = "Dear " + p.getName().toString() + ", please be at your place today some pigeons may visit you :)";
                message.setSubject(subject);
                message.setText(body);
            }
            Transport transport = session.getTransport("smtp");
            transport.connect(host, pigeonsMail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException ae) {
            return false;
        }
        return true;
    }
}
