package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
//@@author SuxianAlicia
public class DuplicateCalendarEntryException extends DuplicateDataException {

    public DuplicateCalendarEntryException() {
        super("Operation would result in duplicate events");
    }
}
