package seedu.address.logic;

import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.gmail.Gmail;

/**
 * Creates an authorized Gmail client for Gmail services.
 */
public class GmailClient {

    private static final String APPLICATION_NAME = "contactHeRo";

    private static GmailClient instance = null;
    private static Gmail service;

    private GmailClient() {
        try {
            service = buildGmailClientService();
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
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail buildGmailClientService() throws IOException {
        Credential credential = GmailAuthentication.authorize();
        return new Gmail.Builder(GmailAuthentication.getHttpTransport(),
                GmailAuthentication.getJsonFactory(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Gmail getGmailService() {
        return service;
    }
}

