//@@author Kyholmes
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Add patient to visiting queue (registration)
 */
public class AddPatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addq";
    public static final String COMMAND_ALIAS = "aq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient into vising queue. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s is registered in the waiting list";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already registered.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This patient cannot be found in the database.";
    private final Index targetIndex;


    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public AddPatientQueueCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        Patient toQueue;

        try {
            toQueue = model.addPatientToQueue(targetIndex);
        } catch (DuplicatePatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PatientNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toQueue.getName().fullName));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientQueueCommand // instanceof handles nulls
                && targetIndex.equals(((AddPatientQueueCommand) other).targetIndex)
                && Objects.equals(this.targetIndex, ((AddPatientQueueCommand) other).targetIndex));
    }
}
