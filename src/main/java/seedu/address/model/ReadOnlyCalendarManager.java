package seedu.address.model;
//@@author SuxianAlicia
import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEntry;

/**
 * Unmodifiable view of an calendar manager.
 */
public interface ReadOnlyCalendarManager {

    /**
     * Returns an unmodifiable view of the calendar entry list.
     * This list will not contain any duplicate calendar entries.
     */
    ObservableList<CalendarEntry> getCalendarEntryList();
}
