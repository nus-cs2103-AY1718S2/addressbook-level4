package seedu.address.model.InsuranceCalendar;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;

import javafx.event.EventHandler;
import seedu.address.model.person.exceptions.DuplicateAppointmentException;


/**
 * The calendar in the address book.
 *
 */

public class InsuranceCalendar {

    private CalendarSource calendarSource;
    private Calendar calendar;

    public InsuranceCalendar() {

        calendar = new Calendar();
        calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);
    }

    /**
     * Adds an appointment to the calendar.
     *
     * @throws DuplicateAppointmentException if the appointment to add is a duplicate of an existing appointments.
     */
    public void addAppointment(AppointmentEntry entry)throws DuplicateAppointmentException {
        if (contains(entry)) {
            throw new DuplicateAppointmentException();
        }
        calendar.addEntry(entry.getAppointmentEntry());
    }

    /**
     * Returns true if the calender contains an equivalent appointment as the given argument.
     */
    public boolean contains(AppointmentEntry toCheck) {
        requireNonNull(toCheck);
        LocalDate startDate = toCheck.getStartDate();
        LocalDate endDate = toCheck.getEndDate();
        Map foundAppointments = calendar.findEntries(startDate, endDate, ZoneId.of("Asia/Singapore"));
        return foundAppointments.containsValue(toCheck.getAppointmentEntry());
    }

    public CalendarSource getCalendar() {
        return calendarSource;
    }
}
