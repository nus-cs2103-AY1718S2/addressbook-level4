package seedu.address.external.exceptions;

/**
 * @@author demitycho
 */
public class CredentialsException extends Exception {
    /**
     * Returns a CredentialsExceptions when misuse happens, when
     * calling functions involving credentials
     * @param message
     */
    public CredentialsException(String message) {
        super(message);
    }
}
