package seedu.address.commons.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;


public class Mailer {

    private static String PIGEONS_MAIL = "pigeonscs2103@gmail.com";
    private static String PASSWORD = "Pigeons2103";
    //private static String RECIPIENT = "delepine.matthieu@gmail.com";
    private static String RECIPIENT = "matthieu2301@hotmail.fr";

    public static void main(String[] args) {

        List<String> to = new ArrayList<>();
        to.add(RECIPIENT);

        email(to);
    }

    private static void email(List<String> recipients) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", PIGEONS_MAIL);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String subject = "Your delivery is expected around 2pm today";
        String body = "Hi, please be at your place around 2pm today some pigeons may visit you :)";

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(PIGEONS_MAIL));

            for(String destination: recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, PIGEONS_MAIL, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException ae) {
            ae.printStackTrace();
        }
        System.out.println("Message sent !");
    }
}
