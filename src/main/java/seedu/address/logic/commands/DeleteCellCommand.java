//@@author sarahgoh97
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cell.exceptions.NotImprisonedException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a prisoner from a cell in the address book.
 */
public class DeleteCellCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletecell";
    public static final String COMMAND_ALIAS = "dc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes prisoners in specified cell.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_DELETE_CELL_SUCCESS = "Prisoner %s has been released.";
    public static final String MESSAGE_NOT_IMPRISONED = "The target person is not imprisoned here";

    public final Index targetIndex;

    private Person prisonerToDelete;
    private String cellAddress;

    /**
     * Creates a deleteCellCommand object
     * @param targetIndex of the person being deleted
     */
    public DeleteCellCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(prisonerToDelete);
        try {
            model.deletePrisonerFromCell(prisonerToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (NotImprisonedException nie) {
            throw new CommandException(MESSAGE_NOT_IMPRISONED);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CELL_SUCCESS, prisonerToDelete.getName().toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        prisonerToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (prisonerToDelete.getIsInCell()) {
            cellAddress = prisonerToDelete.getCellAddress().toString();
        }
    }

    public Person getPrisonerToDelete() {
        return prisonerToDelete;
    }

    public String getCellAddress() {
        return cellAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCellCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCellCommand) other).targetIndex) // state check
                && Objects.equals(this.prisonerToDelete, ((DeleteCellCommand) other).prisonerToDelete));
    }
}
