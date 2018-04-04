package seedu.address.model.calendar;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

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
    public static final String DATE_VALIDATION = "d/MM/yyyy HH:mm";

    private final Entry appointmentEntry;
    private Interval interval;
    private String givenTitle;

    public AppointmentEntry(String title, Interval timeSlot) {
        requireAllNonNull(title, timeSlot);
        appointmentEntry = new Entry(title, timeSlot);
        interval = timeSlot;
        givenTitle = title;
    }

    public AppointmentEntry(AppointmentEntry clonedEntry) {
        requireAllNonNull(clonedEntry);
        appointmentEntry = new Entry(clonedEntry.getGivenTitle(), clonedEntry.getInterval());
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

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(givenTitle)
                .append(" Start Date: ")
                .append(interval.getStartDate().toString())
                .append(" End Date: ")
                .append(interval.getEndDate().toString());

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
}
