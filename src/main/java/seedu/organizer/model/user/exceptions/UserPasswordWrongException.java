package seedu.organizer.model.user.exceptions;

//@@author dominickenn
/**
 * Signals that the user's username matches but password does not
 */
public class UserPasswordWrongException extends Exception {
    public UserPasswordWrongException() {
        super("Wrong password");
    }
}
