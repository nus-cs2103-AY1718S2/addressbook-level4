package seedu.address.external;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GCalendarService {
    private Credential credential;
    private HttpTransport httpTransport;
    private JsonFactory JSON_FACTORY;

    public static final String MESSAGE_SUMMARY = "Lesson with %1$s";
    public static final String SingaporeTimeZone = "Asia/Singapore";

    public GCalendarService() {}

    public GCalendarService(Credential credential,
                            HttpTransport httpTransport, JsonFactory JSON_FACTORY) {
        this.credential = credential;
        this.httpTransport = httpTransport;
        this.JSON_FACTORY = JSON_FACTORY;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public void setJSON_FACTORY(JsonFactory JSON_FACTORY) {
        this.JSON_FACTORY = JSON_FACTORY;
    }

    public JsonFactory getJSON_FACTORY() {
        return JSON_FACTORY;
    }

    /**
     * Synchronizes the user's Google Calendar with the local version
     * @param schedule
     */
    public void synchronize(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        Calendar service = new Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(GServiceManager.APPLICATION_NAME)
                .build();
        DateTime now = new DateTime(System.currentTimeMillis());
        try {
            for (Lesson lesson : schedule.getSchedule()) {
                Event newEvent = lessonToCalendarEvent(lesson, addressBook);
                String calendarId = "primary";
                Event insertedEvent = service.events().insert(calendarId, newEvent).execute();
                System.out.printf("Event created: %s\n", insertedEvent.getHtmlLink());
            }
        } catch (Exception e) {
            System.out.println("Lmao " + e);
        }
    }

    public Event lessonToCalendarEvent(Lesson lesson, ReadOnlyAddressBook addressBook) {
        Event event = new Event();

        Student student = addressBook.findStudentByKey(lesson.getUniqueKey());

        event.setSummary(String.format(MESSAGE_SUMMARY, student.getName()));
        event.setLocation(student.getAddress().toString());

        DateTime startDateTime = new DateTime("2018-04-07T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(SingaporeTimeZone);
        event.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-07T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(SingaporeTimeZone);
        event.setEnd(end);

        return event;
    }
}
