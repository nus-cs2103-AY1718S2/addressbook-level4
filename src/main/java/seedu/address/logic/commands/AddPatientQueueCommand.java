package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;

/**
 * Add patient to visiting queue (registration)
 */
public class AddPatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addq";
    public static final String COMMAND_ALIAS = "aq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient into vising queue. "
            + "Parameters: "
            + PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "%1$s is registered in the waiting list";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already registered.";

    private Patient toAddQueue;
    private String patientName;
    private final NameContainsKeywordsPredicate predicate;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    //will be replaced using patient object as parameter
    public AddPatientQueueCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientQueueCommand // instanceof handles nulls
                && toAddQueue.equals(((AddPatientQueueCommand) other).toAddQueue)
                && patientName.equals(((AddPatientQueueCommand) other).patientName));
    }
}
