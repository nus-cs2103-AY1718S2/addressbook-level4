package seedu.address.model.InsuranceCalendar;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

/**
 * Represents a appointment in the calendar.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class AppointmentEntry {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format of dd/MM/yyyy";
    public static final String DATE_VALIDATION = "d/MM/yyyy";

    private final Entry appointmentEntry;
    private final Interval interval;
    private final String givenTitle;

    public AppointmentEntry(String title, Interval timeSlot) {
        requireAllNonNull(title, timeSlot);
        appointmentEntry = new Entry(title, timeSlot);
        interval = timeSlot;
        givenTitle = title;
    }

    public LocalDate getStartDate() {
        return  interval.getStartDate();
    }

    public LocalDate getEndDate() {
        return  interval.getEndDate();
    }

    public Entry getAppointmentEntry() {
        return appointmentEntry;
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
}
