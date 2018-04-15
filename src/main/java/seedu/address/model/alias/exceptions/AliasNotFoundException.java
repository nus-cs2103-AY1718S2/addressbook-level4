package seedu.address.model.alias.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author jingyinno
/**
 * Signals that the operation is unable to find the specified alias.
 */
public class AliasNotFoundException extends CommandException {
    public AliasNotFoundException() {
        super("Alias does not exist.");
    }
}
