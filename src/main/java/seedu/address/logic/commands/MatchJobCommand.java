package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.model.job.Job;
import seedu.address.model.job.PersonMatchesJobPredicate;

//@@author ChengSashankh
/**
 * Finds and lists all persons in address book whose location, skills or tags match the job posting.
 * Keyword matching is case sensitive.
 */
public class MatchJobCommand extends Command {
    public static final String COMMAND_WORD = "matchjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Matches all persons whose profiles match job posting "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: INDEX...\n"
            + "Example: " + COMMAND_WORD + " 1";

    private PersonMatchesJobPredicate predicate;
    private final Index index;

    public MatchJobCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        ArrayList<Job> listOfJobs = new ArrayList<>(model.getFilteredJobList());
        this.predicate = new PersonMatchesJobPredicate(listOfJobs.get(index.getZeroBased()));
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchJobCommand // instanceof handles nulls
                && this.predicate.equals(((MatchJobCommand) other).predicate)); // state check
    }

}

//@@author ChengSashankh
