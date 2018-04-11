package seedu.address.model.calendar;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
//@@author yuxiangSg
/**
 * Represents a appointment in the calendar.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class AppointmentEntry {

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS =
            "Date and Time should be in the format of dd/MM/yyyy HH:mm";
    public static final String MESSAGE_INTERVAL_CONSTRAINTS = "Start date time must be before end date time";
    public static final String DATE_VALIDATION = "d/MM/yyyy HH:mm";


    private final CalendarEntry appointmentEntry;
    private Interval interval;
    private String givenTitle;

    public AppointmentEntry(String title, Interval timeSlot) {
        requireAllNonNull(title, timeSlot);
        appointmentEntry = new CalendarEntry(title, timeSlot);
        interval = timeSlot;
        givenTitle = title;
    }

    public AppointmentEntry(AppointmentEntry clonedEntry) {
        requireAllNonNull(clonedEntry);
        appointmentEntry = new CalendarEntry(clonedEntry.getGivenTitle(), clonedEntry.getInterval());
        interval = clonedEntry.getInterval();
        givenTitle = clonedEntry.getGivenTitle();
    }

    public LocalDateTime getStartDateTime() {
        return interval.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return interval.getEndDateTime();
    }

    public Entry getAppointmentEntry() {
        return appointmentEntry;
    }

    public String getGivenTitle() {
        return givenTitle;
    }

    public Interval getInterval() {
        return interval;
    }

    /**
     * checks if the startDateTime is before the endDateTime
     */
    public static boolean isValidInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return startDateTime.isBefore(endDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_VALIDATION);
        builder.append(givenTitle)
                .append(" Start Date Time: ")
                .append(interval.getStartDateTime().format(formatter))
                .append(" End Date Time: ")
                .append(interval.getEndDateTime().format(formatter));

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentEntry)) {
            return false;
        }

        AppointmentEntry otherAppointment = (AppointmentEntry) other;
        return otherAppointment.givenTitle.equals(this.getGivenTitle());
    }
    /**
     * CalendarFx Entry, overrides matches
     */
    public static class CalendarEntry extends Entry {
        public CalendarEntry(String title, Interval interval) {
            super(title, interval);
        }

        @Override
        public boolean matches(String searchTerm) {
            if (searchTerm.equals(this.getTitle())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
