package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author x3tsunayh

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Event already exists in the event list!");
    }
}
