package seedu.address.ui;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mail Server activities
 */
//@@author glorialaw
public class MailServer {
    private static String USERNAME = "sell.it.sg@gmail.com";
    private static String PASSWORD = "gloriacs2103";
    private static String HOST = "smtp.gmail.com";

    public MailServer(String[] recipient, String subject, String msg) {
        sendEmail(recipient, subject, msg);
    }

    /**
     * starts a new smtp session
     */
    public static Session startSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        Session session = Session.getInstance(props, auth);
        return session;
    }

    /**
     * Puts together the email message and sends it
     *
     * @param recipients - the addresses the message is sent to
     * @param subject    - user chooses subject
     * @param msg        - contents the user types
     */
    private static void sendEmail(String[] recipients, String subject, String msg) {
        Session session = startSession();
        //create the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            for (String recipient : recipients) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            }
            message.setSubject(subject);
            message.setText(msg);
            //send the message
            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println("Messaging Exception Detected");
            System.out.println(e.getCause());
        }

    }
}
