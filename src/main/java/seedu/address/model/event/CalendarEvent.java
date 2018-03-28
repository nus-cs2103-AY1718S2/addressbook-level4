package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Calendar Event in address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CalendarEvent {

    private final EventTitle eventTitle;
    private final StartDate startDate;
    private final EndDate endDate;
    private final StartTime startTime;
    private final EndTime endTime;


    /**
     * Every field must be present, and not null.
     */
    public CalendarEvent (EventTitle eventTitle, StartDate startDate, EndDate endDate,
                          StartTime startTime, EndTime endTime) {
        requireAllNonNull(eventTitle, startDate, endDate, startTime, endTime);
        this.eventTitle = eventTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public EventTitle getEventTitle() {
        return eventTitle;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalendarEvent)) {
            return false;
        }

        CalendarEvent otherCalEvent = (CalendarEvent) other;
        return otherCalEvent.getEventTitle().equals(this.getEventTitle())
                && otherCalEvent.getStartDate().equals(this.getStartDate())
                && otherCalEvent.getEndDate().equals(this.getEndDate())
                && otherCalEvent.getStartTime().equals(this.getStartTime())
                && otherCalEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventTitle())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime());
        return builder.toString();
    }
}
