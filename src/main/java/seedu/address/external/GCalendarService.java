package seedu.address.external;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Student;

//@@author demitycho

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 * The upload process is still synchronous, the UI will freeze as data is being uploaded
 * There will be logging here to show state updates as it is difficult to show UI changes.
 */
public class GCalendarService {
    public static final String MESSAGE_SUMMARY = "Lesson with %1$s";
    public static final String SINGAPORE_TIME_ZONE = "Asia/Singapore";
    public static final String STRING_COLON = ":";
    public static final String STRING_DASH = "-";
    public static final String STRING_GMT_SG = "+08:00";
    public static final String STRING_STUDENT_CALENDAR_DESCRIPTION = "Calendar for your Student Lessons";
    public static final String STRING_STUDENT_CALENDAR_NAME = "Student Lessons";

    private static final Logger logger = LogsCenter.getLogger(GCalendarService.class);

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
        /**
         * 1. Clear all old Calendar events
         *  a) Get the old "Student Lesson" Calendar ID from CalenderList
         *  b) Delete all Events of that CalendarID
         * 2. Create a new "Student Lesson" Calendar
         * 3. Insert new schedule
         */
        try {
            Integer displayIndex = 1;
            deleteExistingCalendarCopy(service);
            String newCalenderId = createStudentLessonCalendar(service);
            for (Lesson lesson : schedule.getSchedule()) {
                Event newEvent = lessonToCalendarEvent(lesson, addressBook);
                Event insertedEvent = service.events().insert(newCalenderId, newEvent).execute();
                logger.info(String.format("Event created for Lesson %d: %s\n",
                        displayIndex++, insertedEvent.getHtmlLink()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Successfully updated Google Calendar.");
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
        event.setLocation(student.getAddress().toString());
        event.setAttendees(studentToAttendees(student));

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

    /**
     * Creates an Attendee object using the attributes of Student
     * @param student
     * @return
     */
    public static List<EventAttendee> studentToAttendees(Student student) {
        EventAttendee newAttendee = new EventAttendee();
        newAttendee.setDisplayName(student.getName().toString());
        newAttendee.setEmail(student.getEmail().toString());

        return Arrays.asList(newAttendee);
    }

    /**
     * Creates a secondary Google Calendar with the Name "Student Lessons"
     * @param service
     * @return
     * @throws IOException
     */
    public static String createStudentLessonCalendar(Calendar service) throws IOException {
        com.google.api.services.calendar.model.Calendar newCalendar =
                new com.google.api.services.calendar.model.Calendar();
        newCalendar.setSummary(STRING_STUDENT_CALENDAR_NAME);
        newCalendar.setTimeZone(SINGAPORE_TIME_ZONE);
        newCalendar.setDescription(STRING_STUDENT_CALENDAR_DESCRIPTION);

        com.google.api.services.calendar.model.Calendar createdCalendar =
                service.calendars().insert(newCalendar).execute();

        logger.info("Successfully created Google Calendar: Student Lessons");

        return createdCalendar.getId();
    }

    /**
     * Deletes the existing Calendar with the name "Student Lessons"
     * @param service
     * @throws IOException
     */
    public static void deleteExistingCalendarCopy(Calendar service) throws IOException {
        String pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().execute();
            List<CalendarListEntry> items = calendarList.getItems();

            for (CalendarListEntry calendarListEntry : items) {
                if (calendarListEntry.getSummary().equals(STRING_STUDENT_CALENDAR_NAME)) {
                    deleteLessonsOfCalendar(service, calendarListEntry.getId());
                    service.calendars().delete(calendarListEntry.getId()).execute();
                }
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);


        logger.info("Successfully deleted all previous events");
    }

    /**
     * Batch delete the Events from Calendar with {@code calendarID}
     * @param service
     * @param calendarId
     * @throws IOException
     */
    public static void deleteLessonsOfCalendar(Calendar service, String calendarId) throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = service.events().list(calendarId)
                .setMaxResults(200)
                .setTimeMin(now)
                .setSingleEvents(true)
                .execute();
        for (Event event : events.getItems()) {
            service.events().delete(calendarId, event.getId()).execute();
        }
    }
}
