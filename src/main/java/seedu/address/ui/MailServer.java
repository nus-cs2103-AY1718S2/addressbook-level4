package seedu.address.ui;

import javafx.collections.ObservableList;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Mail Server activities
 */
//@@author glorialaw
public class MailServer {
    private static String USERNAME = "sell.it.sg";
    private static String PASSWORD = "gloriacs2103";
    private static String HOST = "smtp.gmail.com";

    public MailServer( String[] recipient, String subject, String msg ){
        String sender = USERNAME;
        String pwd = PASSWORD;

        sendEmail( sender, pwd, recipient, subject, msg );
    }

    /**
     * Puts together the email message and sends it
     * @param sender - email the program is using
     * @param pwd - password of the email account
     * @param recipients - the addresses the message is sent to
     * @param subject - user chooses subject
     * @param msg - contents the user types
     */
    private static void sendEmail(String sender, String pwd, String[] recipients, String subject, String msg) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.password", pwd);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, pwd);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);



        //create the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            for( String recipient : recipients ) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            }
            message.setSubject(subject);
            message.setText(msg);
            //send the message
            Transport.send(message);

        } catch ( MessagingException e ) {
            System.out.println("Messaging Exception Detected");
        }

    }


}
