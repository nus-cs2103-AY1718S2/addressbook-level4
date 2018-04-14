package seedu.address.testutil;
//@@author SuxianAlicia
import seedu.address.model.CalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * A utility class to help with building CalendarManager objects.
 * Example usage: <br>
 *     {@code CalendarManager cm = new CalendarManagerBuilder().withEntries("Meet Boss", "Get Stocks").build();}
 */
public class CalendarManagerBuilder {
    private CalendarManager calendarManager;

    public CalendarManagerBuilder() {
        calendarManager = new CalendarManager();
    }

    public CalendarManagerBuilder(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public CalendarManagerBuilder withEntry(CalendarEntry calendarEntry) {
        try {
            calendarManager.addCalendarEntry(calendarEntry);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new IllegalArgumentException("Entry is expected to be unique.");
        }
        return this;
    }

    public CalendarManager build() {
        return calendarManager;
    }
}

