package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a person to the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark for a person specified in the INDEX. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMARK + "REMARK] ";

    public static final String MESSAGE_ADD_SUCCESS = "Added remark to: %1$s";
    public static final String MESSAGE_REMOVE_SUCCESS = "Removed remark from: %1$s";

    private final Person toAdd;
    private final int index;
    private final String remark;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public RemarkCommand() {
        this.index = 0;
        this.remark = null;
        toAdd = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Not yet implemented");

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && toAdd.equals(((RemarkCommand) other).toAdd));
    }
}
