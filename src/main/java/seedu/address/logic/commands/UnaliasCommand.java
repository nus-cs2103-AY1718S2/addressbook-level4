package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.alias.exceptions.AliasNotFoundException;

//@@author jingyinno
/**
 * Removes an alias pair from the address book.
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes alias for previously aliased command.\n"
            + "Parameters: [CURRENT_ALIAS]\n"
            + "Example: " + COMMAND_WORD + " a";

    public static final String MESSAGE_SUCCESS = "Alias has been removed!";
    public static final String MESSAGE_UNKNOWN_UNALIAS = "This alias does not exist.";

    private final String toRemove;

    /**
     * Creates an UnaliasCommand to add the specified {@code Alias}
     */
    public UnaliasCommand(String unalias) {
        requireNonNull(unalias);
        toRemove = unalias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeAlias(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (AliasNotFoundException e) {
            throw new CommandException(MESSAGE_UNKNOWN_UNALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == (this)
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && toRemove.equals(((UnaliasCommand) other).toRemove));
    }
}
