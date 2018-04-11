package seedu.address.model.person.exceptions;

/**
 * Signals that the operation uses illegal mark values for Participation
 */
public class IllegalMarksException extends IllegalArgumentException {
    public IllegalMarksException() {
        super("Mark values are not allowed");
    }
}
