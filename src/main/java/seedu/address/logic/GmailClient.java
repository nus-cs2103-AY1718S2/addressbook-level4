package seedu.address.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
//@@author KevinCJH
/**
 * Creates an authorized Gmail client for all services that uses Gmail API.
 */
public class GmailClient {

    private static final String APPLICATION_NAME = "contactHeRo";

    private static GmailClient instance = null;
    private static Gmail service;

    private GmailClient() {
        try {
            service = createGmailClientService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GmailClient getInstance() {
        if (instance == null) {
            instance = new GmailClient();
        }
        return instance;
    }

    /**
     * Create and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail createGmailClientService() throws IOException {
        Credential credential = GmailAuthentication.authorize();
        return new Gmail.Builder(GmailAuthentication.getHttpTransport(),
                GmailAuthentication.getJsonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a message from an email to be send out.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     * @param emailContent Email content to be sent.
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send("me", message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }


    public static Gmail getGmailService() {
        return service;
    }
}

