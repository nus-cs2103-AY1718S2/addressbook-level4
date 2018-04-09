//@@author kush1509
package seedu.address.logic.commands.job;

import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.job.Job;

/**
 * Finds and lists all jobs in address book whose position or skills or location contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class JobFindCommand extends Command {

    public static final String COMMAND_WORD = "findjob";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " " + PREFIX_POSITION;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose"
            + " POSITION or SKILL or LOCATION"
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: p/POSITION_KEYWORDS [MORE_POSITION_KEYWORDS] or s/SKILL_KEYWORDS [MORE_SKILL_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " p/Alice Bob\n"
            + "Example: " + COMMAND_WORD + " s/Java C"
            + "Example: " + COMMAND_WORD + " l/Singapore";

    private final Predicate<Job> predicate;

    public JobFindCommand(Predicate<Job> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {

        model.updateFilteredJobList(predicate);
        return new CommandResult(getMessageForJobListShownSummary(model.getFilteredJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobFindCommand // instanceof handles nulls
                && this.predicate.equals(((JobFindCommand) other).predicate)); // state check
    }
}
