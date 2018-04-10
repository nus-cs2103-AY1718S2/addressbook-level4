package seedu.address.logic.commands.exceptions;

//@@author KevinCJH

/**
 * Represents an exception which occurs during google authentication
 */
public class GoogleAuthenticationException extends Exception {
    public GoogleAuthenticationException(String message) {
        super(message);
    }
}
