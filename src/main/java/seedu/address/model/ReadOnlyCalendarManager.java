package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEntry;

/**
 * Unmodifiable view of an calendar manager.
 */
//@@author SuxianAlicia
public interface ReadOnlyCalendarManager {

    /**
     * Returns an unmodifiable view of the calendar entry list.
     * This list will not contain any duplicate calendar entries.
     */
    ObservableList<CalendarEntry> getCalendarEntryList();
}
