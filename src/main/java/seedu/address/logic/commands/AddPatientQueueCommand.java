//@@author Kyholmes
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;

/**
 * Add patient to visiting queue (registration)
 */
public class AddPatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addq";
    public static final String COMMAND_ALIAS = "aq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient into visiting queue. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s is added into visiting queue";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already added into visiting queue.";
    private final Index targetIndex;
    private Index actualSourceIndex;

    /**
     * Creates an AddPatientQueueCommand to add the specified patient by its list index
     */
    public AddPatientQueueCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Patient toQueue;

        try {
            toQueue = model.addPatientToQueue(actualSourceIndex);
        } catch (DuplicatePatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toQueue.getName().fullName));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String actualIndexInString = model.getPatientSourceIndexInList(targetIndex.getZeroBased()) + "";

        try {
            actualSourceIndex = ParserUtil.parseIndex(actualIndexInString);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target patient cannot be missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddPatientQueueCommand
                && this.targetIndex.equals(((AddPatientQueueCommand) other).targetIndex));
    }
}
