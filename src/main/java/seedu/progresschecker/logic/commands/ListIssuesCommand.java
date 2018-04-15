package seedu.progresschecker.logic.commands;

import java.io.IOException;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author adityaa1998
/**
 * Finds and lists all issues from github with the specified state in the argument.
 * Keyword matching is case insensitive.
 */
public class ListIssuesCommand extends Command {

    public static final String COMMAND_WORD = "listissue";
    public static final String COMMAND_ALIAS = "lis";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " STATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the issues "
            + "of the specified state with the respective github issue index.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CLOSE";
    private static final String MESSAGE_INVALID_STATE = "Please enter correct issue state";
    private static final String MESSAGE_VALIDATION_FAILURE = "Please log into github first";
    private static final String tabType = "issues";
    private static final String MESSAGE_SUCCESS = "All the %s issues are being viewed";

    private static String state;

    public ListIssuesCommand(String state) {
        this.state = state;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.listIssues(state);
            EventsCenter.getInstance().post(new TabLoadChangedEvent(tabType));
            return new CommandResult(String.format(MESSAGE_SUCCESS, state));
        } catch (IllegalValueException ie) {
            throw new CommandException(MESSAGE_INVALID_STATE);
        } catch (IOException ie) {
            throw new CommandException(MESSAGE_INVALID_STATE);
        } catch (CommandException ce) {
            throw new CommandException(MESSAGE_VALIDATION_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListIssuesCommand // instanceof handles nulls
                && this.state.equals(((ListIssuesCommand) other).state)); // state check
    }
}
