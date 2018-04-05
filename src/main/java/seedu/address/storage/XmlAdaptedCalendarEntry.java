package seedu.address.storage;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * JAXB-friendly version of a CalendarEntry.
 */
//@@author SuxianAlicia
public class XmlAdaptedCalendarEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CalendarEntry's %s field is missing!";
    public static final String START_AND_END_DATE_CONSTRAINTS = "Start Date cannot be later than End Date.";
    public static final String START_AND_END_TIME_CONSTRAINTS =
            "Start Time cannot be later than End Time if Event ends on same date.";
    public static final String EVENT_DURATION_CONSTRAINTS =
            "Event must last at least 15 minutes if ending in same day."; //Constraint of CalendarFX entries

    private static final int MINIMAL_DURATION = 15; //Constraint of CalendarFX entries

    @XmlElement
    private String entryTitle;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;

    /**
     * Constructs an XmlAdaptedCalendarEntry.
     */
    public XmlAdaptedCalendarEntry() {}

    /**
     * Constructs an {@code XmlAdaptedCalendarEntry} with the given calendar event details.
     */
    public XmlAdaptedCalendarEntry(String entryTitle, String startDate, String endDate,
                                   String startTime, String endTime) {
        this.entryTitle = entryTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCalendarEntry
     */
    public XmlAdaptedCalendarEntry(CalendarEntry source) {
        entryTitle = source.getEntryTitle().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts the jaxb-friendly adapted calendar event object into the model's CalendarEntry object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted calendar event's fields.
     */
    public CalendarEntry toModelType() throws IllegalValueException {
        if (this.entryTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EntryTitle.class.getSimpleName()));
        }
        if (!EntryTitle.isValidEntryTitle(this.entryTitle)) {
            throw new IllegalValueException(EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        }
        final EntryTitle entryTitle = new EntryTitle(this.entryTitle);

        if (this.startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }

        if (!DateUtil.isValidDate(this.startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        final StartDate startDate = new StartDate(this.startDate);

        if (this.endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDate.class.getSimpleName()));
        }

        if (!DateUtil.isValidDate(this.endDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        final EndDate endDate = new EndDate(this.endDate);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }

        if (!TimeUtil.isValidTime(this.startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        final StartTime startTime = new StartTime(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndTime.class.getSimpleName()));
        }

        if (!TimeUtil.isValidTime(this.endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }

        final EndTime endTime = new EndTime(this.endTime);

        // Exception thrown if Start Date is later than End Date
        if (startDate.getLocalDate().isAfter(endDate.getLocalDate())) {
            throw new IllegalValueException(START_AND_END_DATE_CONSTRAINTS);
        }

        // Check for cases when Start Date is equal to End Date
        if (startDate.getLocalDate().equals(endDate.getLocalDate())) {
            // Check if start time is later than end time
            if (startTime.getLocalTime().isAfter(endTime.getLocalTime())) {
                throw new IllegalValueException(START_AND_END_TIME_CONSTRAINTS);
            }

            // Check if duration of event is less than 15 minutes
            if (MINUTES.between(startTime.getLocalTime(), endTime.getLocalTime()) < MINIMAL_DURATION) {
                throw new IllegalValueException(EVENT_DURATION_CONSTRAINTS);
            }
        }


        return new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCalendarEntry)) {
            return false;
        }

        XmlAdaptedCalendarEntry otherCalEvent = (XmlAdaptedCalendarEntry) other;
        return Objects.equals(entryTitle, otherCalEvent.entryTitle)
                && Objects.equals(startDate, otherCalEvent.startDate)
                && Objects.equals(endDate, otherCalEvent.endDate)
                && Objects.equals(startTime, otherCalEvent.startTime)
                && Objects.equals(endTime, otherCalEvent.endTime);
    }

}
