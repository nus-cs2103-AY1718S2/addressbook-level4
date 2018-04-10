package seedu.address.model.event;
//@@author SuxianAlicia
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Calendar Event in address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CalendarEntry {

    private final EntryTitle entryTitle;
    private final StartDate startDate;
    private final EndDate endDate;
    private final StartTime startTime;
    private final EndTime endTime;


    /**
     * Every field must be present, and not null.
     */
    public CalendarEntry(EntryTitle entryTitle, StartDate startDate, EndDate endDate,
                         StartTime startTime, EndTime endTime) {
        requireAllNonNull(entryTitle, startDate, endDate, startTime, endTime);
        this.entryTitle = entryTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public EntryTitle getEntryTitle() {
        return entryTitle;
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

        if (!(other instanceof CalendarEntry)) {
            return false;
        }

        CalendarEntry otherCalEvent = (CalendarEntry) other;
        return otherCalEvent.getEntryTitle().equals(this.getEntryTitle())
                && otherCalEvent.getStartDate().equals(this.getStartDate())
                && otherCalEvent.getEndDate().equals(this.getEndDate())
                && otherCalEvent.getStartTime().equals(this.getStartTime())
                && otherCalEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryTitle, startDate, endDate, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEntryTitle())
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
