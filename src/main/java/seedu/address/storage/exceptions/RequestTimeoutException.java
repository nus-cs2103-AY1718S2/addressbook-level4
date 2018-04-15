package seedu.address.storage.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author Caijun7
/**
 * Signals that the authorization request has timed out.
 */
public class RequestTimeoutException extends CommandException {
    public RequestTimeoutException() {
        super("Authorization request timed out. Please try again.");
    }
}
