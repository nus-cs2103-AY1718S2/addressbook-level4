package seedu.address.commons.exceptions;

//@@author yeggasd
/**
 * Represents an error during decryption
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}

