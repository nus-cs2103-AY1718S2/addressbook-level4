package seedu.address.commons.exceptions;

//@@author AzuraAiR
/**
 * Represents an error in trying to obtain the long url from the shortened Timetable URL
 */
public class NoInternetConnectionException extends Exception {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
