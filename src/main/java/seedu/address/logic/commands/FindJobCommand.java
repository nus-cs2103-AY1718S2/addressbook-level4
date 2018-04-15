package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.job.Job;

//@@author ChengSashankh

/**
 * Lists all jobs in Infinity Book for which Job Title, Location or Tags contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindJobCommand extends Command {
    public static final String COMMAND_WORD = "findjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose job title, location or tags"
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with index "
            + "numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " frontend backend systems";

    private final Predicate<Job> predicate;

    public FindJobCommand(Predicate<Job> predicate) {
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
                || (other instanceof FindJobCommand // instanceof handles nulls
                && this.predicate.equals(((FindJobCommand) other).predicate)); // state check
    }
}
//@@author
