package seedu.address.testutil;

import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * A utility class to help with building CalendarEntry objects.
 */
public class CalendarEntryBuilder {

    public static final String DEFAULT_EVENT_TITLE = "Meeting with boss";
    public static final String DEFAULT_START_DATE = "10-10-2018";
    public static final String DEFAULT_END_DATE = "10-10-2018";
    public static final String DEFAULT_START_TIME = "10:00";
    public static final String DEFAULT_END_TIME = "12:00";

    private EntryTitle entryTitle;
    private StartDate startDate;
    private EndDate endDate;
    private StartTime startTime;
    private EndTime endTime;

    public CalendarEntryBuilder() {
        entryTitle = new EntryTitle(DEFAULT_EVENT_TITLE);
        startDate = new StartDate(DEFAULT_START_DATE);
        endDate = new EndDate(DEFAULT_END_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
    }

    /**
     * Initializes the CalendarEntryBuilder with the data of {@code entryToCopy}.
     */
    public CalendarEntryBuilder(CalendarEntry entryToCopy) {
        entryTitle = entryToCopy.getEntryTitle();
        startDate = entryToCopy.getStartDate();
        endDate = entryToCopy.getEndDate();
        startTime = entryToCopy.getStartTime();
        endTime = entryToCopy.getEndTime();
    }

    /**
     * Sets the {@code EntryTitle} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEntryTitle(String eventTitle) {
        this.entryTitle = new EntryTitle(eventTitle);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withStartDate(String startDate) {
        this.startDate = new StartDate(startDate);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEndDate(String endDate) {
        this.endDate = new EndDate(endDate);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEndTime(String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    public CalendarEntry build() {
        return new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
    }
}
