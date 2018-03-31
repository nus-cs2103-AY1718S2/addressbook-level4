package seedu.organizer.model.user.exceptions;

//@@author dominickenn
/**
 * Signals that a user is currently logged in
 */
public class CurrentlyLoggedInException extends Exception {
    public CurrentlyLoggedInException() {
        super("A user is currently logged in");
    }
}
