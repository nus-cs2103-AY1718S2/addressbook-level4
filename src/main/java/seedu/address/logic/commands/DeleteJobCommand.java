package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.JobNotFoundException;

//@@author ChengSashankh
/**
 * Deletes a job identified using it's last displayed index from the address book.
 */
public class DeleteJobCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletejob";
    public static final String COMMAND_ALIAS = "dj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job identified by the index number used in the last job listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Deleted job: %1$s";

    private final Index targetIndex;

    private Job jobToDelete;

    public DeleteJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(jobToDelete);
        try {
            model.deleteJob(jobToDelete);
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (JobNotFoundException jnfe) {
            throw new AssertionError("The target job cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_SUCCESS, jobToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Job> lastShownList = model.getFilteredJobList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        jobToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJobCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteJobCommand) other).targetIndex) // state check
                && Objects.equals(this.jobToDelete, ((DeleteJobCommand) other).jobToDelete));
    }

}

//@@author
