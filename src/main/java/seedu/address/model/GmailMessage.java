package seedu.address.model;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.api.services.gmail.Gmail;

import seedu.address.logic.GmailClient;

/**
 * Creates an email message containing contents to be sent via gmail
 */
public class GmailMessage {

    private static Gmail service;
    private static MimeMessage emailContent;
    private static String subject;
    private static String bodyText;
    private static String receiver;

    public GmailMessage(String receiver, String subject, String bodyText) {
        GmailClient client = GmailClient.getInstance();
        service = client.getGmailService();

        this.receiver = receiver;
        this.subject = subject;
        this.bodyText = bodyText;

        try {
            emailContent = createEmailContent(receiver, getSenderEmail(), subject, bodyText);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmailContent(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setContent(bodyText, "text/html; charset=utf-8");
        return email;
    }

    /**
     * Get the email address of the authenticated user.
     * @return String of the email address
     * @throws IOException
     */
    private static String getSenderEmail() throws IOException {
        return service.users().getProfile("me").execute().getEmailAddress();
    }

    public static MimeMessage getEmailContent() {
        return emailContent;
    }

    public static String getSubject() {
        return subject;
    }

    public static String getBodyText() {
        return bodyText;
    }

    public static String getReceiverEmail() {
        return receiver;
    }
}

