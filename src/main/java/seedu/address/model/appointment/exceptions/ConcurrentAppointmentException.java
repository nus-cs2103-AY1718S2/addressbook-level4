package seedu.address.model.appointment.exceptions;

//@@author Robert-Peng

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an error message when user attempt to add new appointment that
 * interfere with other appointment time interval
 */
public class ConcurrentAppointmentException extends IllegalValueException {

    public ConcurrentAppointmentException () {
        super("AddressBook should not add appointments to on-going appointment slots");
    }

}

