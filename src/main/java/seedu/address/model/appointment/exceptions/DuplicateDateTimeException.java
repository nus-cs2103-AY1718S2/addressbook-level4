package seedu.address.model.appointment.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author wynonaK
/**
 * Signals that the operation will result in double booking.
 */
public class DuplicateDateTimeException extends DuplicateDataException {
    public DuplicateDateTimeException() {
        super("Operation would result in multiple bookings in the same time slot");
    }
}
