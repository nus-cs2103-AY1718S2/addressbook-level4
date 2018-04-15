package seedu.address.model.cinema.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Cinema objects.
 */
public class DuplicateCinemaException extends DuplicateDataException {
    public DuplicateCinemaException() {
        super("Operation would result in duplicate cinemas");
    }
}
