package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;


/**
 * Adds a patient to the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark for a patient specified in the INDEX. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMARK + "REMARK] ";

    public static final String MESSAGE_ADD_SUCCESS = "Added remark to: %1$s";
    public static final String MESSAGE_REMOVE_SUCCESS = "Removed remark from: %1$s";

    private final Patient toAdd;
    private final Index index;
    private final String remark;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public RemarkCommand(Index index, String remark) {
        this.index = index;
        this.remark = remark;
        toAdd = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException("Index: " + index.getOneBased() + " Remark: " + remark);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && remark.equals(((RemarkCommand) other).remark)
                && index.equals(((RemarkCommand) other).index));
    }
}
