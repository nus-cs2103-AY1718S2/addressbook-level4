package seedu.address.testutil;

import java.time.LocalDateTime;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import seedu.address.model.calendar.AppointmentEntry;

/**
 * A utility class to help with building AppointmentEntry objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_TITLE = "Meet John";
    public static final LocalDateTime DEFAULT_START_DATE_TIME = LocalDateTime.of
            (2018, 04, 04, 12, 00);

    public static final LocalDateTime DEFAULT_END_DATE_TIME = LocalDateTime.of
            (2018, 04, 04, 13, 00);

    private Entry appointmentEntry;
    private Interval interval;
    private String givenTitle;

    public AppointmentBuilder() {
        givenTitle = DEFAULT_TITLE;
        interval = new Interval(DEFAULT_START_DATE_TIME, DEFAULT_END_DATE_TIME);
        appointmentEntry = new Entry(givenTitle, interval);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */

    public AppointmentBuilder(AppointmentEntry appointmentToCopy) {
        givenTitle = appointmentToCopy.getGivenTitle();
        interval = new Interval(appointmentToCopy.getStartDateTime(), appointmentToCopy.getEndDateTime());
        appointmentEntry = new Entry(givenTitle, interval);
    }

    /**
     * Sets the {@code givenTitle} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTitle(String title) {
        givenTitle = title;
        return this;
    }

    /**
     * Sets the {@code title} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withInterval(Interval interval) {
        this.interval = interval;
        return this;
    }

    public AppointmentEntry build() {
        return new AppointmentEntry(givenTitle, interval);
    }


}
