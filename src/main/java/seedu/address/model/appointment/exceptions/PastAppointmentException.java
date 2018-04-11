package seedu.address.model.appointment.exceptions;

//@@author Robert-Peng

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an error message when adding appointment with a past date
 */
public class PastAppointmentException extends IllegalValueException {

    public PastAppointmentException() {
        super("AddressBook should not add appointments with past DateTime");
    }
}

