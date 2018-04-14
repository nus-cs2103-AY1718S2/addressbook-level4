package seedu.address.model.entry.exceptions;
//@@author SuxianAlicia
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the {@code UniqueCalendarEntryList}.
 */
public class DuplicateCalendarEntryException extends DuplicateDataException {

    public DuplicateCalendarEntryException() {
        super("Operation would result in duplicate events");
    }
}
