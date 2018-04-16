package seedu.address.model.team.exceptions;

//@@author jordancjq
/**
 * Signals that the operation is unable to find the specified team.
 */
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException() {};

    public TeamNotFoundException(String message) {
        super(message);
    }
}
