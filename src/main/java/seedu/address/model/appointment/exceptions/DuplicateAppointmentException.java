package seedu.address.model.appointment.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author wynonaK
/**
 * Signals that the operation will result in duplicate Appointment objects.
 */
public class DuplicateAppointmentException extends DuplicateDataException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
