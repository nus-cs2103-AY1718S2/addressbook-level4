package seedu.address.model.event.exceptions;
//@@author SuxianAlicia
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateCalendarEntryException extends DuplicateDataException {

    public DuplicateCalendarEntryException() {
        super("Operation would result in duplicate events");
    }
}
