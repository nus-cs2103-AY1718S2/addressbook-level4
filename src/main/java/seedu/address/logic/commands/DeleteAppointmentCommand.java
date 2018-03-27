package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

/**
 * Delete a patient's appointment
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delappt";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a patient's appointment."
            + "Parameters: "
            + "NAME"
            + "INDEX_NO";

    public static final String MESSAGE_DELETE_SUCCESS = "The appointment is canceled";

    public static final String MESSAGE_PERSON_NOT_FOUND = "This patient cannot be found in the database";

    private final NameContainsKeywordsPredicate predicate;

    private final Index targetIndex;

    public DeleteAppointmentCommand(NameContainsKeywordsPredicate predicate, Index targetIndex) {
        this.predicate = predicate;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteAppointmentCommand);
    }
}
