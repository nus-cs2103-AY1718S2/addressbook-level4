package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;

/**
 * Adds a job opening to contactHeRo.
 */
public class JobAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addjob";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_POSITION + " "
            + PREFIX_TEAM + " "
            + PREFIX_LOCATION + " "
            + PREFIX_NUMBER_OF_POSITIONS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job opening to contactHeRo. "
            + "Parameters: "
            + PREFIX_POSITION + "POSITION "
            + PREFIX_TEAM + "TEAM "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_NUMBER_OF_POSITIONS + "NUMBER OF POSITIONS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POSITION + "Software Engineer "
            + PREFIX_TEAM + "Cloud Services "
            + PREFIX_LOCATION + "Singapore, Singapore "
            + PREFIX_NUMBER_OF_POSITIONS + "5";

    public static final String MESSAGE_SUCCESS = "New job opening added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB = "This job opening already exists in contactHeRo.";

    private final Job toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Job}
     */
    public JobAddCommand(Job job) {
        requireNonNull(job);
        toAdd = job;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addJob(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateJobException e) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobAddCommand // instanceof handles nulls
                && toAdd.equals(((JobAddCommand) other).toAdd));
    }

}
