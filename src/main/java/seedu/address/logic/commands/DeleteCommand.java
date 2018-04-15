package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.util.ListPanelController;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student/tutor identified by the index number used in the last student/tutor listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_CATEGORY + "s";

    public static final String MESSAGE_DELETE_CLIENT_SUCCESS = "Deleted Client: %1$s";

    private final Index targetIndex;
    private final Category category;

    private Client clientToDelete;

    public DeleteCommand(Index targetIndex, Category category) {
        this.targetIndex = targetIndex;
        this.category = category;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(clientToDelete);
        if (ListPanelController.isCurrentDisplayActiveList()) {
            try {
                model.deleteClient(clientToDelete, category);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        } else {
            assert(!ListPanelController.isCurrentDisplayActiveList());
            try {
                model.deleteClosedClient(clientToDelete, category);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CLIENT_SUCCESS, clientToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Client> lastShownList;

        if (ListPanelController.isCurrentDisplayActiveList()) {
            if (category.isStudent()) {
                lastShownList = model.getFilteredStudentList();
            } else {
                lastShownList = model.getFilteredTutorList();
            }
        } else {
            assert(!ListPanelController.isCurrentDisplayActiveList());
            if (category.isStudent()) {
                lastShownList = model.getFilteredClosedStudentList();
            } else {
                lastShownList = model.getFilteredClosedTutorList();
            }
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.clientToDelete, ((DeleteCommand) other).clientToDelete));
    }
}
