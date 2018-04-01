//@@author kush1509
package seedu.address.logic.commands.job;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.tag.TagMatchesPredicate;

/**
 * Matches the job to potential employees.
 */
public class JobMatchCommand extends Command {

    public static final String COMMAND_WORD = "matchjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Matches the job identified by the job number to potential employees.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public JobMatchCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Job> lastShownList = model.getFilteredJobList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        Job jobToMatch = lastShownList.get(targetIndex.getZeroBased());

        model.updateFilteredPersonList(new TagMatchesPredicate(jobToMatch.getTags()));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobMatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((JobMatchCommand) other).targetIndex)); // state check
    }

}
