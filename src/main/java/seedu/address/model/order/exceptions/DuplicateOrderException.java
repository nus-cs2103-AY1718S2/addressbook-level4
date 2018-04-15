package seedu.address.model.order.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateOrderException extends DuplicateDataException {
    public DuplicateOrderException() {
        super("Operation would result in duplicate orders");
    }
}
