package seedu.progresschecker.logic.commands;

import java.io.IOException;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.commands.exceptions.CommandException;


/**
 * Reopens an issue on github
 */
public class ReopenIssueCommand extends Command {

    public static final String COMMAND_WORD = "reopenissue";
    public static final String COMMAND_ALIAS = "ri";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " ISSUE-INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\nParameters: ISSUE_INDEX (must be a positive valid index number)"
            + "Example: \n" + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Issue #%1$s was reopened successfully";
    public static final String MESSAGE_FAILURE = "Issue wasn't reopened. Enter correct index number.";

    private final Index targetIndex;

    public ReopenIssueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.reopenIssueOnGithub(targetIndex);
        } catch (IOException ie) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReopenIssueCommand // instanceof handles nulls
                && this.targetIndex.equals(((ReopenIssueCommand) other).targetIndex)); // state check
    }
}

