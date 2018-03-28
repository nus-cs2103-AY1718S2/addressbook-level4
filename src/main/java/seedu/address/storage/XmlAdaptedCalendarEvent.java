package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * JAXB-friendly version of a CalendarEvent.
 */
public class XmlAdaptedCalendarEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CalendarEvent's %s field is missing!";

    @XmlElement
    private String eventTitle;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;

    /**
     * Constructs an XmlAdaptedCalendarEvent.
     */
    public XmlAdaptedCalendarEvent() {}

    /**
     * Constructs an {@code XmlAdaptedCalendarEvent} with the given calendar event details.
     */
    public XmlAdaptedCalendarEvent(String eventTitle, String startDate, String endDate,
                                   String startTime, String endTime) {
        this.eventTitle = eventTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCalendarEvent
     */
    public XmlAdaptedCalendarEvent(CalendarEvent source) {
        eventTitle = source.getEventTitle().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
    }

    /**
     * Converts the jaxb-friendly adapted calendar event object into the model's CalendarEvent object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted calendar event's fields.
     */
    public CalendarEvent toModelType() throws IllegalValueException {
        if (this.eventTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventTitle.class.getSimpleName()));
        }
        if (!EventTitle.isValidEventTitle(this.eventTitle)) {
            throw new IllegalValueException(EventTitle.MESSAGE_EVENT_TITLE_CONSTRAINTS);
        }
        final EventTitle eventTitle = new EventTitle(this.eventTitle);

        if (this.startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }

        if (!StartDate.isValidStartDate(this.startDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        final StartDate startDate = new StartDate(this.startDate);

        if (this.endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDate.class.getSimpleName()));
        }

        if (!EndDate.isValidEndDate(this.endDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        final EndDate endDate = new EndDate(this.endDate);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }

        if (!StartTime.isValidStartTime(this.startTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        final StartTime startTime = new StartTime(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndTime.class.getSimpleName()));
        }

        if (!EndTime.isValidEndTime(this.endTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }

        final EndTime endTime = new EndTime(this.endTime);

        return new CalendarEvent(eventTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCalendarEvent)) {
            return false;
        }

        XmlAdaptedCalendarEvent otherCalEvent = (XmlAdaptedCalendarEvent) other;
        return Objects.equals(eventTitle, otherCalEvent.eventTitle)
                && Objects.equals(startDate, otherCalEvent.startDate)
                && Objects.equals(endDate, otherCalEvent.endDate)
                && Objects.equals(startTime, otherCalEvent.startTime)
                && Objects.equals(endTime, otherCalEvent.endTime);
    }

}
