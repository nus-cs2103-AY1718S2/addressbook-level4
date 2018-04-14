package seedu.address.storage;
//@@author SuxianAlicia
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

/**
 * JAXB-friendly version of a CalendarEntry.
 */
public class XmlAdaptedCalendarEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CalendarEntry's %s field is missing!";

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
     * Constructs an {@code XmlAdaptedCalendarEntry} with the given calendar entry details.
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
     * Converts the jaxb-friendly adapted calendar entry object into the model's CalendarEntry object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted calendar entry's fields.
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

        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);

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
