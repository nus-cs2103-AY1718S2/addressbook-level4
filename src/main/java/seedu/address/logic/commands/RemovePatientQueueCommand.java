//@@author Kyholmes
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Remove patient from visiting queue (checkout)
 */
public class RemovePatientQueueCommand extends Command {

    public static final String COMMAND_WORD = "removeq";
    public static final String COMMAND_ALIAS = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the first patient from the visiting queue.";

    public static final String MESSAGE_USAGE_INDEX = COMMAND_WORD
            + ": Removes a specific patient from the visiting queue."
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "%1$s is removed from the waiting list";
    public static final String MESSAGE_PERSON_NOT_FOUND_QUEUE = "This patient is not in the waiting list";
    public static final String MESSAGE_QUEUE_EMPTY = "Waiting list is empty";

    public final boolean indexIsExist;
    private Index targetIndex;

    public RemovePatientQueueCommand() {
        indexIsExist = false;

    }

    public RemovePatientQueueCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        indexIsExist = true;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (indexIsExist) {
            List<Patient> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        try {
            Patient patientToRemove;
            if (indexIsExist) {
                patientToRemove = model.removePatientFromQueueByIndex(targetIndex);
            } else {
                patientToRemove = model.removePatientFromQueue();
            }
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, patientToRemove.getName().toString()));
        } catch (PatientNotFoundException e) {

            if (model.getVisitingQueue().size() > 0)
            {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND_QUEUE);
            }

            throw new CommandException(MESSAGE_QUEUE_EMPTY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemovePatientQueueCommand)
                && this.indexIsExist == ((RemovePatientQueueCommand) other).indexIsExist
                && this.targetIndex.equals(((RemovePatientQueueCommand) other).targetIndex);
    }
}
