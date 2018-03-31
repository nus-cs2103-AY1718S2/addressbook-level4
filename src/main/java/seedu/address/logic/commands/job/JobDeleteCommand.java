package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.JobNotFoundException;

/**
 * Deletes a job identified using it's last displayed index from the address book.
 */
public class JobDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletejob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job identified by the index number used in the last job listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Deleted Job: %1$s";

    private final Index targetIndex;

    private Job jobToDelete;

    public JobDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(jobToDelete);
        try {
            model.deleteJob(jobToDelete);
        } catch (JobNotFoundException pnfe) {
            throw new AssertionError("The target job cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_JOB_SUCCESS, jobToDelete));
    }

    @Override
    public void preprocessUndoableCommand() throws CommandException {
        List<Job> lastShownList = model.getFilteredJobList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        jobToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobDeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((JobDeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.jobToDelete, ((JobDeleteCommand) other).jobToDelete));
    }
}
