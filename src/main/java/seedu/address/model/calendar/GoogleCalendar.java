package seedu.address.model.calendar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author cambioforma

/**
 * Class to handle insert, update and delete operations to Google Calendar
 */
public class GoogleCalendar {

    private static final Logger logger = LogsCenter.getLogger(GoogleCalendar.class);
    private static final String APPOINTMENT_DESCRIPTION = "Appointment";
    private static final String BIRTHDAY_DESCRIPTION = "Birthday";
    private Person data;

    public String getCalendarId() throws IOException {
        UserPrefs userPrefs = new UserPrefs();
        byte[] encoded = Files.readAllBytes(Paths.get(userPrefs.getCalendarIdFilePath()));
        return new String(encoded, "UTF-8");
    }

    /**
     * Adds the appointment of a {@code Person} to the Google Calendar
     * @param data {@code Person} to be added to the Google Calendar
     */
    public void addPersonToCalendar(Person data) {
        this.data = data;
        try {
            addBirthdayEvent();
            if (data.getAppointment() != null) {
                addAppointmentEvent();
            }
        } catch (IOException e) {
            logger.warning("Unable to add Event");
        }
    }

    /**
     * Adds an appointment {@code Event} to the Google Calendar
     * @throws IOException Unable to create calendarId from file
     */
    private void addAppointmentEvent() throws IOException {
        Event event = createAppointmentEvent();
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        String calendarId = this.getCalendarId();
        event = service.events().insert(calendarId, event).execute();
        logger.info("Event created: " + event.getHtmlLink());
    }

    /**
     * Creates a Google Calendar Event for appointment
     * @return Returns an {@code Event} of appointment of {@code Person}
     */
    private Event createAppointmentEvent() {
        Event event = new Event()
            .setSummary(data.getName().fullName)
            .setDescription(APPOINTMENT_DESCRIPTION);

        String startDateString = getAppointmentDateFromPerson();
        DateTime startDateTime = new DateTime(startDateString);
        EventDateTime start = new EventDateTime().setDate(startDateTime);
        event.setStart(start);
        event.setEnd(start);

        EventReminder[] reminderOverrides = new EventReminder[] {
            new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        return event;
    }
    /**
     * Adds an appointment {@code Event} to the Google Calendar
     * @throws IOException Unable to create calendarId from file
     */
    private void addBirthdayEvent() throws IOException {
        Event event = createBirthdayEvent();
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        String calendarId = this.getCalendarId();
        event = service.events().insert(calendarId, event).execute();
        logger.info("Event created: " + event.getHtmlLink());
    }
    /**
     * Creates a Google Calendar Event for birthday
     * @return Returns an {@code Event} of birthday of {@code Person}
     */
    private Event createBirthdayEvent() {
        Event event = new Event()
                .setSummary(data.getName().fullName)
                .setDescription(BIRTHDAY_DESCRIPTION);

        String startDateString = getBirthdayDateFromPerson();
        DateTime startDateTime = new DateTime(startDateString);
        EventDateTime start = new EventDateTime().setDate(startDateTime);
        event.setStart(start);
        event.setEnd(start);

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String[] recurrence = new String[] {"RRULE:FREQ=YEARLY;"};
        event.setRecurrence(Arrays.asList(recurrence));

        return event;
    }

    private String getAppointmentDateFromPerson() {
        try {
            String startDateString = data.getAppointment().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            return startDateString;
        } catch (ParseException e) {
            logger.warning("Unable to convert date format for appointment");
        }
        return null;
    }

    private String getBirthdayDateFromPerson() {
        try {
            String startDateString = data.getBirthday().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            return startDateString;
        } catch (ParseException e) {
            logger.warning("Unable to convert date format for birthday");
        }
        return null;
    }

    /**
     * Clear all events from the Google Calendar
     * @throws IOException Unable to read calendarId from file
     */
    public void resetCalendar() {
        try {
            Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
            String calendarId = this.getCalendarId();
            service.calendars().delete(calendarId).execute();
            GoogleCalendarInit.init();
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("Unable to delete calendar");
        }
    }

    /**
     * Deletes the appointment of a {@code Person} from the Google Calendar
     * @param data {@code Person} to be deleted from the Google Calendar
     */
    public void removePersonFromCalendar(Person data) {
        this.data = data;
        try {
            deleteBirthdayEvent();
            if (data.getAppointment() != null) {
                deleteAppointmentEvent();
            }
        } catch (IOException e) {
            logger.warning("Deleting appointment event failed");
        }
    }

    /**
     * Deletes an appoinement {@code Event} from the Google Calendar
     * @throws IOException Deleting appointment event failed
     */
    private void deleteAppointmentEvent() throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(APPOINTMENT_DESCRIPTION)
                    && event.getSummary().equals(data.getName().fullName)) {
                    service.events().delete(this.getCalendarId(), event.getId()).execute();
                    logger.fine("Appointment deleted: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }

    /**
     * Deletes a birthday {@code Event} from the Google Calendar
     * @throws IOException Deleting birthday event failed
     */
    private void deleteBirthdayEvent() throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(BIRTHDAY_DESCRIPTION)
                    && event.getSummary().equals(data.getName().fullName)) {
                    service.events().delete(this.getCalendarId(), event.getId()).execute();
                    logger.fine("Birthday deleted: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }

    /**
     * Edits a Google Calendar Event for Appointment
     * @param target Target {@code Person} to be edited
     * @param data New {@code Person} be edited into the Google Calendar
     */
    public void updatePersonToCalendar(Person target, Person data) {
        this.data = data;
        if (!target.getBirthday().equals(data.getBirthday())) {
            try {
                editBirthdayEvent(target);
            } catch (IOException e) {
                logger.warning("Failed to edit birthday event in Google Calendar");
            }
        }
        if (!target.getAppointment().equals(data.getAppointment())) {
            try {
                editAppointmentEvent(target);
            } catch (IOException e) {
                logger.warning("Failed to edit appointment event in Google Calendar");
            }
        }
        if (!target.getName().fullName.equals((data.getName().fullName))) {
            try {
                editEventsSummary(target);
            } catch (IOException e) {
                logger.warning("Failed to edit Person name in Google Calendar");
            }
        }
    }

    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editAppointmentEvent(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(APPOINTMENT_DESCRIPTION)
                    && event.getSummary().equals(target.getName().fullName)) {
                    String startDateString = getAppointmentDateFromPerson();
                    DateTime startDateTime = new DateTime(startDateString);
                    EventDateTime start = new EventDateTime().setDate(startDateTime);
                    event.setStart(start);
                    event.setEnd(start);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Appointment updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editBirthdayEvent(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getDescription().equals(BIRTHDAY_DESCRIPTION)
                    && event.getSummary().equals(target.getName().fullName)) {
                    String startDateString = getBirthdayDateFromPerson();
                    DateTime startDateTime = new DateTime(startDateString);
                    EventDateTime start = new EventDateTime().setDate(startDateTime);
                    event.setStart(start);
                    event.setEnd(start);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Birthday updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
    /**
     * Edits an appointment {@code Event} from the Google Calendar
     * @param target Target {@code Person} to be edited
     * @throws IOException
     */
    private void editEventsSummary(Person target) throws IOException {
        Calendar service = GoogleCalendarApiAuthentication.getCalendarService();
        // Iterate over the events in the specified calendar
        String pageToken = null;
        do {
            Events events = service.events().list(this.getCalendarId()).setPageToken(pageToken).execute();
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getSummary().equals(target.getName().fullName)) {
                    event.setSummary(data.getName().fullName);
                    service.events().update(this.getCalendarId(), event.getId(), event).execute();
                    logger.fine("Name updated: " + event.getId());
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
    }
}
