package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;

/**
 * Adds an alias pair to the address book.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of alias or creates new alias.\n"
            + "Parameters: [COMMAND] [NEW_ALIAS]\n"
            + "Example: " + COMMAND_WORD + " add a";

    public static final String MESSAGE_SUCCESS = "New alias added";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias is already used";
    public static final String MESSAGE_INVALID_COMMAND_DESCRIPTION = "There is no such command to alias to.";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid alias command! \n%1$s";

    private final Alias toAdd;

    /**
     * Creates an AliasCommand to add the specified {@code Person}
     */
    public AliasCommand(Alias alias) {
        requireNonNull(alias);
        toAdd = alias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAlias(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAliasException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other.equals(this);
    }
}


