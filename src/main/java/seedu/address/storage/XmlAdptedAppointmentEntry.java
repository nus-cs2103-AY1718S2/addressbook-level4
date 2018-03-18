package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import com.calendarfx.model.Interval;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insuranceCalendar.AppointmentEntry;

/**
 * JAXB-friendly version of the Appointment Entry.
 */
public class XmlAdptedAppointmentEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment Entry's %s field is missing!";
    public static final String MISSING_FIELD_TITLE = "[TITLE]";
    public static final String MISSING_FIELD_START_DATE = "[START_DATE]";
    public static final String MISSING_FIELD_END_DATE = "[END_DATE]";
    public static final String DATE_VALIDATION = "yyyy-MM-d";
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format of yyyy-MM-d";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;

    /**
     * Constructs an XmlAdaptedCalendarEntry.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdptedAppointmentEntry() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdptedAppointmentEntry(String title, String startDate, String endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    /**
     * Converts a given appoinmentEntry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointmentEntry
     */
    public XmlAdptedAppointmentEntry(AppointmentEntry source) {
        title = source.getGivenTitle();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted calendarEntry object into the model's AppointmentEntry object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public AppointmentEntry toModelType() throws IllegalValueException {

        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_TITLE ));
        }

        final String title = this.title;

        if (this.startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_START_DATE));
        }

        final String newStartDate = this.startDate;

        if (this.endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MISSING_FIELD_END_DATE));
        }

        final String newEndDate = this.endDate;

        final Interval interval = new Interval(getLocatDate(newStartDate) , LocalTime.MIN, getLocatDate(newEndDate),
                LocalTime.MAX);

        return new AppointmentEntry(title, interval);
    }

    LocalDate getLocatDate(String date) throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_VALIDATION);

        try {

            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;

        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdptedAppointmentEntry otherEntry = (XmlAdptedAppointmentEntry) other;
        return Objects.equals(title, otherEntry.title)
                && Objects.equals(startDate, otherEntry.startDate)
                && Objects.equals(endDate, otherEntry.endDate);
    }
}
