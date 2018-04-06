package seedu.address.external;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Student;

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GCalendarService {
    public static final String MESSAGE_SUMMARY = "Lesson with %1$s";
    public static final String SINGAPORE_TIME_ZONE = "Asia/Singapore";
    public static final String STRING_COLON = ":";
    public static final String STRING_DASH = "-";
    public static final String STRING_GMT_SG = "+08:00";

    private Credential credential;
    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    public GCalendarService() {}

    public GCalendarService(Credential credential,
                            HttpTransport httpTransport, JsonFactory jsonFactory) {
        this.credential = credential;
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
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

    public void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * Synchronizes the user's Google Calendar with the local version
     * @param schedule
     */
    public void synchronize(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        Calendar service = new Calendar.Builder(
                httpTransport, jsonFactory, credential)
                .setApplicationName(GServiceManager.APPLICATION_NAME)
                .build();
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

    /**
     * Converts a lesson to a recurring Calendar Event format
     * @param lesson
     * @param addressBook
     * @return
     */
    public Event lessonToCalendarEvent(Lesson lesson, ReadOnlyAddressBook addressBook) {
        Event event = new Event();

        Student student = addressBook.findStudentByKey(lesson.getUniqueKey());

        event.setSummary(String.format(MESSAGE_SUMMARY, student.getName()));
        event.setLocation(student.getAddress().toString());

        DateTime startDateTime = new DateTime(
                dayTimeToDateTimeString(lesson.getDay(), lesson.getStartTime()));
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(SINGAPORE_TIME_ZONE);
        event.setStart(start);

        DateTime endDateTime = new DateTime(
                dayTimeToDateTimeString(lesson.getDay(), lesson.getEndTime()));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(SINGAPORE_TIME_ZONE);
        event.setEnd(end);

        //Set recurring for a month
        event.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;COUNT=4"));
        return event;
    }

    /**
     * Converts a Day and Time object to a RFC822 String format
     * @param day
     * @param time
     * @return
     */
    public static String dayTimeToDateTimeString(Day day, Time time) {
        LocalDate next = LocalDate.now().with(
                TemporalAdjusters.next(Day.dayToDayOfWeekEnum(day)));

        StringBuilder dateTimeString = new StringBuilder();
        dateTimeString.append(Integer.toString(next.getYear()))
                .append(STRING_DASH)
                .append(String.format("%02d", next.getMonthValue()))
                .append(STRING_DASH)
                .append(String.format("%02d", next.getDayOfMonth()))
                .append("T")
                .append(time)
                .append(STRING_COLON)
                .append("00")
                .append(STRING_GMT_SG);
        return dateTimeString.toString();
    }
}
