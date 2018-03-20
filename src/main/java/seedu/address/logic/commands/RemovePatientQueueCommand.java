package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;

/**
 * Remove patient from visiting queue (checkout)
 */
public class RemovePatientQueueCommand extends Command {

    public static final String COMMAND_WORD = "removeq";
    public static final String COMMAND_ALIAS = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a patient from the visiting queue."
            + "Parameters: "
            + "QUEUE INDEX NO";

    public static final String MESSAGE_REMOVE_SUCCESS = "%1$s is removed from the waiting list";

    private final Index targetIndex;

    private Patient patientToRemove;

    public RemovePatientQueueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePatientQueueCommand // instanceof handles nulls
                && targetIndex.equals(((RemovePatientQueueCommand) other).targetIndex));
    }
}
