package seedu.address.model.appointment.exceptions;

//@@author wynonaK
/**
 * Signals that the operation cannot be done as there is no appointments in said year.
 */
public class NoAppointmentInYearException extends Exception {
    public NoAppointmentInYearException(String message) {
        super(message);
    }
}
