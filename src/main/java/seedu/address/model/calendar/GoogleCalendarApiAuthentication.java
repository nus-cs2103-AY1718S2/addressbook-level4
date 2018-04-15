package seedu.address.model.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import seedu.address.commons.core.LogsCenter;

//@@author cambioforma
/**
 * Authenticates with oAuth Google API
 */
public class GoogleCalendarApiAuthentication {
    private static final Logger logger = LogsCenter.getLogger(GoogleCalendarApiAuthentication.class);
    /** Application name. */
    private static final String APPLICATION_NAME = "reInsurance";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException if error reading oAuth key client_secret.json
     */
    private static GoogleCredential authorize() {
        try {
            InputStream in = GoogleCalendarApiAuthentication.class
                    .getResourceAsStream("/oAuth/reInsurance-2f4f33627950.json");
            return GoogleCredential.fromStream(in).createScoped(SCOPES);
        } catch (IOException e) {
            logger.severe("Unable to create oAuth key from file");
            return null;
        }
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException if error reading oAuth key client_secret.json
     */
    public static Calendar getCalendarService() {
        Credential credential = authorize();
        return new Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
