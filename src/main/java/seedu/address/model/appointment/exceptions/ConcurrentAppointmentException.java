package seedu.address.model.appointment.exceptions;

//@@author Robert-Peng
/**
 * Represents an error message when user attempt to add new appointment that
 * interfere with other appointment time interval
 */
public class ConcurrentAppointmentException extends Exception {

    public ConcurrentAppointmentException () {
        super();
    }

}

