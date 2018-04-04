package seedu.address.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

//@@author kengsengg
/**
 * Calendar of the App
 */
public class CalendarDisplay {

    private static final String APPLICATION_NAME =  "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static HttpTransport HTTP_TRANSPORT;

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = Calendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =  GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    private Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates an event on the Google Calendar
     * @throws IOException
     */
    public void createEvent(Appointment toAdd, Person selectedPerson) throws IOException {
        Calendar service = getCalendarService();

        Name name = selectedPerson.getName();

        Event event = new Event().setSummary(name.toString());

        DateTime startDateTime = new DateTime(formattedStartDateTime(toAdd));
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(formattedEndDateTime(toAdd));
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        String calendarId = "primary";
        service.events().insert(calendarId, event).execute();
    }

    /**
     * Sets the required format for start time
     * @param toAdd appointment details provided by the user
     * @return the start time in the required format
     */
    private String formattedStartDateTime(Appointment toAdd) {
        String date = toAdd.getDate();
        String startTime = toAdd.getStartTime();
        return (date.substring(4, 8)).concat("-").concat(date.substring(2, 4)).concat("-")
                .concat(date.substring(0, 2)).concat("T").concat(startTime.substring(0, 2))
                .concat(":").concat(startTime.substring(2, 4)).concat(":00+08:00");
    }

    /**
     * Sets the required format for end time
     * @param toAdd appointment details provided by the user
     * @return the end time in the required format
     */
    private String formattedEndDateTime(Appointment toAdd) {
        String date = toAdd.getDate();
        String endTime = toAdd.getEndTime();
        return (date.substring(4, 8)).concat("-").concat(date.substring(2, 4)).concat("-")
                .concat(date.substring(0, 2)).concat("T").concat(endTime.substring(0, 2))
                .concat(":").concat(endTime.substring(2, 4)).concat(":00+08:00");
    }
}
//@@author
