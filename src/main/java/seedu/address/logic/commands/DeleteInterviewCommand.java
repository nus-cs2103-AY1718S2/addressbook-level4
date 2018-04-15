//@@author deeheenguyen
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;



/**
 * Deletes a job identified using it's last displayed index from the address book.
 */
public class DeleteInterviewCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteInterview";
    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the interview identified by the index number used in the last interview listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INTERVIEW_SUCCESS = "Deleted Interview: %1$s";

    private final Index targetIndex;

    private Interview interviewToDelete;

    public DeleteInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(interviewToDelete);
        try {
            model.deleteInterview(interviewToDelete);
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (InterviewNotFoundException jnfe) {
            throw new AssertionError("The target interview cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_INTERVIEW_SUCCESS, interviewToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        interviewToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteInterviewCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteInterviewCommand) other).targetIndex) // state check
                && Objects.equals(this.interviewToDelete, ((DeleteInterviewCommand) other).interviewToDelete));
    }

}

