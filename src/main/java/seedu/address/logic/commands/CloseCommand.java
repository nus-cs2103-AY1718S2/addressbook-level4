package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
/**
 * Deletes a person from the active list and add it to the closed list
 */
public class CloseCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "close";
    public static final String COMMAND_ALIAS = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Close an active tutor or student and store them in "
            + "a closed student or tutor list. \n"
            + "Parameters: " + COMMAND_WORD + " " + "INDEX" + " " + PREFIX_CATEGORY + "CATEGORY \n"
            + "INDEX should be non-zero and non-negative and "
            + "CATEGORY can only be either 's' or 't', where 's' represents students and 't' represents tutor).\n"
            + "Example: " + COMMAND_WORD + " " + "1" + " " + PREFIX_CATEGORY + "t\n";

    public static final String MESSAGE_CLOSE_STUDENT_SUCCESS = "Student closed: %1$s";
    public static final String MESSAGE_CLOSE_TUTOR_SUCCESS = "Tutor closed: %1$s";

    private final Index targetIndex;
    private final Category category;

    private Client clientToClose;

    public CloseCommand(Index targetIndex, Category category) {
        this.targetIndex = targetIndex;
        this.category = category;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(clientToClose);
        try {
            model.deleteClient(clientToClose, category);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target client cannot be missing");
        }

        try {
            if (category.isStudent()) {
                model.addClosedStudent(clientToClose);
            } else {
                model.addClosedTutor(clientToClose);
            }
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The client should not be duplicated");
        }

        if (category.isStudent()) {
            return new CommandResult(String.format(MESSAGE_CLOSE_STUDENT_SUCCESS, clientToClose));
        } else {
            return new CommandResult(String.format(MESSAGE_CLOSE_TUTOR_SUCCESS, clientToClose));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToClose = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CloseCommand // instanceof handles nulls
                && this.targetIndex.equals(((CloseCommand) other).targetIndex) // state check
                && Objects.equals(this.clientToClose, ((CloseCommand) other).clientToClose));
    }
}
