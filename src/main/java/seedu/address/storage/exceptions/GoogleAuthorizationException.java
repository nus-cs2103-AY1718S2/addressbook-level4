package seedu.address.storage.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author Caijun7
/**
 * Signals that the application is unable to gain user's authorization.
 */
public class GoogleAuthorizationException extends CommandException {
    public GoogleAuthorizationException() {
        super("Unable to access your Google Drive. Please grant authorization.");
    }
}
