//@@author Kyholmes
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Remove patient from visiting queue (checkout)
 */
public class RemovePatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeq";
    public static final String COMMAND_ALIAS = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the first patient from the visiting queue.";

    public static final String MESSAGE_USAGE_INDEX = COMMAND_WORD
            + ": Removes a specific patient from the visiting queue."
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "%1$s is removed from the visiting queue.";
    public static final String MESSAGE_PERSON_NOT_FOUND_QUEUE = "This patient is not in the visiting queue.";
    public static final String MESSAGE_QUEUE_EMPTY = "Visiting queue is empty";

    public final boolean indexArguementIsExist;
    private Index targetIndex;

    public RemovePatientQueueCommand() {
        indexArguementIsExist = false;

    }

    public RemovePatientQueueCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        indexArguementIsExist = true;
        this.targetIndex = targetIndex;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (indexArguementIsExist) {
            //Unfilter patient list
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

            if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            Patient patientToRemove;
            if (indexArguementIsExist) {
                patientToRemove = model.removePatientFromQueueByIndex(targetIndex);
            } else {
                patientToRemove = model.removePatientFromQueue();
            }
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, patientToRemove.getName().toString()));
        } catch (PatientNotFoundException e) {

            if (model.getVisitingQueue().size() > 0) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND_QUEUE);
            }

            throw new CommandException(MESSAGE_QUEUE_EMPTY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemovePatientQueueCommand)
                && this.indexArguementIsExist == ((RemovePatientQueueCommand) other).indexArguementIsExist
                && this.targetIndex.equals(((RemovePatientQueueCommand) other).targetIndex);
    }
}
