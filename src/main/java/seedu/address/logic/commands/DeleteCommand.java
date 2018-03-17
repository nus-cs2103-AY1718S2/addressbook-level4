package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Deletes a patient identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by the index number used in the last patient listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Patient: %1$s";

    private final Index targetIndex;

    private Patient patientToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(patientToDelete);
        try {
            model.deletePerson(patientToDelete);
        } catch (PatientNotFoundException pnfe) {
            throw new AssertionError("The target patient cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, patientToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        patientToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.patientToDelete, ((DeleteCommand) other).patientToDelete));
    }
}
