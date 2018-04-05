package seedu.address.external;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.ServiceException;

import seedu.address.external.exceptions.CredentialsException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.student.Student;

/**
 * Constructs a new GContactsManager object to communicate with Google's APIs
 */
public class GContactsManager {
    private static final String CLIENT_ID = "126472549776-8cd9bk56sfubm9rkacjivecikppte982.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "nyBpzm1OjnKNZOd0-kT1uo7W";

    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    private static final String APPLICATION_NAME = "codeducator/v1.4";

    private final String[] scopesArray = new String[]{
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.google.com/m8/feeds/",
        CalendarScopes.CALENDAR};

    /** OAuth 2.0 scopes. */
    public final List<String> scopes = Arrays.asList(scopesArray);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private Credential credential;

    public GContactsManager() {

    }

    /**
     * Login method for user to login to their Google account
     * @throws CredentialsException
     */
    public void login() throws CredentialsException {
        if (credential != null) {
            throw new CredentialsException("You are already logged in.");
        }
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, scopes)
                        .setAccessType("offline")
                        .build();
        try {
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
        if (credential == null) {
            throw new CredentialsException("You are not logged in");
        }
        credential = null;
    }

    /**
     * Converts a Student object to a ContactEntry for insertion
     * @param student
     * @return
     */
    private ContactEntry studentToContactEntry(Student student) {
        ContactEntry contact = new ContactEntry();

        Name name = new Name();
        FullName newFullName = new FullName();
        newFullName.setValue(student.getName().toString());
        name.setFullName(newFullName);
        contact.setName(name);

        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(student.getPhone().toString());
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);

        Email primaryMail = new Email();
        primaryMail.setAddress(student.getEmail().toString());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        return contact;
    }

    /**
     * Syncs the Addressbook with the user's Google account's Google Contacts.
     * @param addressBook
     * @throws ServiceException
     * @throws IOException
     */
    public void synchronize(ReadOnlyAddressBook addressBook)
            throws ServiceException, IOException {
        ContactEntry contact = new ContactEntry();
        // Set the contact's name.
        ContactsService myService = new ContactsService(APPLICATION_NAME);
        myService.setOAuth2Credentials(credential);
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        for (Student student : addressBook.getStudentList()) {
            ContactEntry toBeInserted = studentToContactEntry(student);
            myService.insert(postUrl, toBeInserted);
        }

    }

    public void synchronize(ReadOnlySchedule schedule) {
        Calendar service = new Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        DateTime now = new DateTime(System.currentTimeMillis());
        try {
            Events events = service.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }

        } catch (Exception e) {
            System.out.println("Lmao " + e);
        }
    }
}
