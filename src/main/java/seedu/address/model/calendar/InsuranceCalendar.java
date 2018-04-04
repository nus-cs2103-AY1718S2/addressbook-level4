package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import seedu.address.model.calendar.exceptions.AppointmentNotFoundException;
import seedu.address.model.calendar.exceptions.DuplicateAppointmentException;
import seedu.address.model.calendar.exceptions.EditAppointmentFailException;


/**
 * The calendar in the address book.
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
     * Clear all appointments to the calendar.
     *
     */
    public void clearAppointments () {
        appointmentEntries.clear();
        calendar.clear();
    }

    /**
     * copy all appointments to the calendar given a new calendar.
     *
     */
    public void copyAppointments (InsuranceCalendar copyingCalendar) {

        for (AppointmentEntry entry: copyingCalendar.getAppointmentEntries()) {
            AppointmentEntry addedEntry = new AppointmentEntry(entry);
            calendar.addEntry(addedEntry.getAppointmentEntry());
            appointmentEntries.add(addedEntry);
        }
    }

    /**
     * Adds an appointment to the calendar.
     *
     * @throws DuplicateAppointmentException if the appointment to add is a duplicate of an existing appointments.
     */
    public void addAppointment(AppointmentEntry entry) throws DuplicateAppointmentException {
        if (contains(entry)) {
            throw new DuplicateAppointmentException();
        }

        calendar.addEntry(new AppointmentEntry(entry).getAppointmentEntry());
        appointmentEntries.add(entry);

    }

    /**
     * Remove appointments found with the given keywords in the calendar.
     *
     * @throws AppointmentNotFoundException if the appointment to remove does not exist.
     */
    public void removeAppointment(String searchText) throws AppointmentNotFoundException {

        List<Entry<?>> foundEntires = calendar.findEntries(searchText);
        if (foundEntires.isEmpty()) {
            throw new AppointmentNotFoundException();
        }

        for (Entry entry: foundEntires) {
            removeAppointmentEntry (entry);
        }
        calendar.removeEntries(foundEntires);


    }

    /**
     * remove a given entry in the appointmentEntries
     *
     */
    public void removeAppointmentEntry(Entry entryToCheck) {

        String givenTitle = entryToCheck.getTitle();
        Interval givenInterval = entryToCheck.getInterval();
        AppointmentEntry appointmentEntryToCheck = new AppointmentEntry(givenTitle, givenInterval);
        appointmentEntries.remove(appointmentEntryToCheck);
    }

    /**
     * edit appointments found with the the given searchText
     *
     * @throws EditAppointmentFailException if the appointment to remove does not exist or duplicate appointment to add.
     */
    public void editAppointmentEntry(String searchText, AppointmentEntry referenceEntry)
            throws EditAppointmentFailException {

        try {
            removeAppointment(searchText);
        } catch (AppointmentNotFoundException e) {
            throw new EditAppointmentFailException();
        }

        try {
            addAppointment(referenceEntry);
        } catch (DuplicateAppointmentException e) {
            throw new EditAppointmentFailException();
        }
    }

    /**
     * return appointments found with the given keywords in the calendar.
     *
     * @throws AppointmentNotFoundException if the appointment to find does not exist.
     */
    public AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException {

        List<Entry<?>> foundEntires = calendar.findEntries(searchText);

        if (foundEntires.isEmpty()) {
            throw new AppointmentNotFoundException();
        } else {
            return findAppointmentEntry(foundEntires.get(0));
        }
    }

    /**
     * return a given entry in the appointmentEntries
     *
     */
    public AppointmentEntry findAppointmentEntry(Entry entryToCheck) {

        String givenTitle = entryToCheck.getTitle();
        Interval givenInterval = entryToCheck.getInterval();
        AppointmentEntry appointmentEntryToCheck = new AppointmentEntry(givenTitle, givenInterval);

        for (AppointmentEntry entry : appointmentEntries) {
            if (entry.equals(appointmentEntryToCheck)) {
                return entry;
            }
        }
        return null;
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
