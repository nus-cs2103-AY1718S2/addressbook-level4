package seedu.address.model.alias.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

public class AliasNotFoundException extends CommandException {
    public AliasNotFoundException() {
        super("Alias does not exist.");
    }
}
