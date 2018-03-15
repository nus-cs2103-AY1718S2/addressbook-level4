package seedu.address.model.InsuranceCalendar;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;

import seedu.address.model.person.exceptions.DuplicateAppointmentException;


/**
 * The calendar in the address book.
 *
 */
public class InsuranceCalendar {

    private CalendarSource calendarSource;
    private Calendar calendar;
    private ArrayList<AppointmentEntry> appointmentEntries;

    public InsuranceCalendar() {

        calendar = new Calendar();
        calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);
        appointmentEntries = new ArrayList<>();
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
        appointmentEntries.add(entry);
    }

    /**
     * Returns true if the calender contains an equivalent appointment as the given argument.
     */
    public boolean contains(AppointmentEntry toCheck) {
        requireNonNull(toCheck);
        return appointmentEntries.contains(toCheck);
    }

    public CalendarSource getCalendar() {
        return calendarSource;
    }

    public ArrayList<AppointmentEntry> getAppointmentEntries() {
        return appointmentEntries;
    }
}
