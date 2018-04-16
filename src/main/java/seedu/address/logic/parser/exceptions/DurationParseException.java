package seedu.address.logic.parser.exceptions;

//@@author ChoChihTun
/**
 * Signals that the input duration format is invalid
 */
public class DurationParseException extends Exception {
    public DurationParseException(String message) {
        super(message);
    }
}
