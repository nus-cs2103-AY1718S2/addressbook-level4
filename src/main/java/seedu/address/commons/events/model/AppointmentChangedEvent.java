package seedu.address.commons.events.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author cambioforma

/**
 * Indicates when a person's birthday has been changed
 */
public class AppointmentChangedEvent extends BaseEvent {


    public final Person data;

    public AppointmentChangedEvent(Person data, String command) {
        this.data = data;
        try {
            if (command.equals("add")) {
                addAppointmentToCalendar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add birthday event of person to the Google Calendar
     */
    private void addAppointmentToCalendar() throws IOException {
        Calendar service = GoogleAuthentication.getCalendarService();

        Event event = new Event()
                .setSummary(data.getName().fullName)
                .setDescription("appointment");


        try {
            String startDateString = data.getBirthday().value;
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = requiredFormat.format(originalFormat.parse(startDateString));
            DateTime startDateTime = new DateTime(startDateString);
            EventDateTime start = new EventDateTime()
                    .setDate(startDateTime);
            event.setStart(start);
            event.setEnd(start);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("popup").setMinutes(24 * 60)
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
    @Override
    public String toString() {
        return "new birthday " + data.getBirthday();
    }
}

