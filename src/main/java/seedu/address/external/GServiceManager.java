package seedu.address.external;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import com.google.gdata.util.ServiceException;

import seedu.address.external.exceptions.CredentialsException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;

//@@author demitycho

/**
 * Manages the two Google Services, Google Contacts as well as Google Calendar
 */
public class GServiceManager {
    public static final String[] SCOPES_ARRAY = new String[]{
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.google.com/m8/feeds/",
        CalendarScopes.CALENDAR};

    /** OAuth 2.0 scopes. */
    public static final List<String> SCOPES = Arrays.asList(SCOPES_ARRAY);

    public static final String APPLICATION_NAME = "codeducator/v1.5";

    private static final String CLIENT_ID = "126472549776-8cd9bk56sfubm9rkacjivecikppte982.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "nyBpzm1OjnKNZOd0-kT1uo7W";

    private static FileDataStoreFactory dataStoreFactory;
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    private static GoogleClientSecrets clientSecrets;

    private static final File DATA_STORE_DIR = new java.io.File(
            "./credentials");

    private Credential credential;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public GServiceManager() { }

    /**
     * Login method for user to login to their Google account
     * @throws CredentialsException
     */
    public void login() throws CredentialsException {
        if (credential != null) {
            throw new CredentialsException("You are already logged in.");
        }
        // Build flow and trigger user authorization request.
        try {
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            httpTransport, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
                            .setDataStoreFactory(dataStoreFactory)
                            .setAccessType("offline")
                            .build();

            credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    /**
     * Logout method for the user to logout of their Google account locally
     * @throws CredentialsException
     */
    public void logout() throws CredentialsException {
        // Delete credentials from data store directory
        File dataStoreDirectory = dataStoreFactory.getDataDirectory();
        if (dataStoreDirectory.list().length == 0 || credential == null) {
            throw new CredentialsException("You are not logged in");
        }
        credential = null;
        for (File file : dataStoreDirectory.listFiles()) {
            file.delete();
        }
        try {
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uploads the schedule to Google Calendar
     * @param addressBook
     * @param schedule
     */
    public void synchronize(ReadOnlyAddressBook addressBook, ReadOnlySchedule schedule)
            throws IOException, ServiceException {
        GContactsService gContactsService = new GContactsService(credential);
        gContactsService.synchronize(addressBook);


        GCalendarService gCalendarService = new GCalendarService(
                credential, httpTransport, JSON_FACTORY);
        gCalendarService.synchronize(schedule, addressBook);

    }
}
