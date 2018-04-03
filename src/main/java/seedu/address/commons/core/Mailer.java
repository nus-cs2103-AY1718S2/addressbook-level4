package seedu.address.commons.core;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import seedu.address.model.person.Person;

/**
 * Send personalized emails to drivers and customers
 */
public class Mailer {

    private static String pigeonsMail = "pigeonscs2103@gmail.com";
    private static String password = "Pigeons2103";
    private static String host = "smtp.gmail.com";
    //private static String defaultRecipient = "delepine.matthieu@gmail.com";
    //private static String defaultRecipient = "matthieu2301@hotmail.fr";
    private static String defaultRecipient = pigeonsMail;

    /**
     * Send an email to the
     * @param recipients
     */
    public static boolean emailCustomers(List<Person> recipients) {

        Session session = getSession();

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(pigeonsMail));

            for (Person p: recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(p.getEmail().toString()));

                String subject = "Your delivery with Pigeons";

                String body = "Dear "
                        + p.getName().toString() + ", your delivery is arriving on "
                        + p.getDate().toString()
                        + " please be at your place some pigeons may visit you :)"
                        + "<img src=\"https://i.imgur.com/Eg6CDss.jpg\" "
                        + "alt=\"Pigeons Logo\" style=\"display: block\"><br>";

                message.setSubject(subject);
                message.setContent(body, "text/html");
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

    public static boolean emailDriver(List<String> route, String duration, String date) {
        Session session = getSession();

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(pigeonsMail));
            message.setSubject("Your itinerary on " + date);

            String body = "<h2>The estimated duration for this itinerary is : " +
                    duration
                    + " ("
                    + route.size()
                    + " deliveries)"
                    + "</h2>"
                    + "<ol>";

            for (String address: route) {

                body += "<li>" + address + "</li>";
            }

            body += "</ol>"
                    + "<img src=\"https://i.imgur.com/Eg6CDss.jpg\" "
                    + "alt=\"Pigeons Logo\" style=\"display: block\">";

            message.setContent(body, "text/html");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(pigeonsMail));

            Transport transport = session.getTransport("smtp");
            transport.connect(host, pigeonsMail, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException ae) {
            return false;
        }
        return true;
    }

    private static Session getSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", pigeonsMail);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", host);

        return Session.getDefaultInstance(props);
    }
}
