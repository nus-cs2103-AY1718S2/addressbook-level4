package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;

//@@author ChengSashankh
/***
 * Posts/adds a job posting to the infinity book.
 */
public class PostJobCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "postjob";
    public static final String COMMAND_ALIAS = "j";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job posting to the address book. "
            + "Parameters: "
            + PREFIX_JOBTITLE + "JOBTITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_SKILLS + "SKILLS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBTITLE + "Backend Engineer "
            + PREFIX_LOCATION + "Singapore "
            + PREFIX_SKILLS + "SQL, Javascript "
            + PREFIX_TAG + "FreshGrad ";

    public static final String MESSAGE_SUCCESS = "New job added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book";

    private final Job toAdd;

    /**
     * Creates an PostJobCommand to add the specified {@code Job}
     */
    public PostJobCommand(Job job) {
        requireNonNull(job);
        toAdd = job;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addJob(toAdd);
            // System.out.println(model.getFilteredJobList().get(0).toString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateJobException e) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostJobCommand // instanceof handles nulls
                && toAdd.equals(((PostJobCommand) other).toAdd));
    }
}

//@@author ChengSashankh
