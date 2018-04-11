package seedu.address.commons.exceptions;

//@@author limzk1994-reused
/**
 * Signals that the password provided is wrong.
 */
public class WrongPasswordException extends Exception {

    private static final String MESSAGE_WRONG_PASSWORD = "Wrong Password";

    public WrongPasswordException() {
        super(MESSAGE_WRONG_PASSWORD);
    }

    /**
     * @param cause   of the main exception
     */
    public WrongPasswordException(Throwable cause) {
        super(MESSAGE_WRONG_PASSWORD, cause);
    }
}
