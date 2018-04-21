package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FindResults;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    //@@author tanhengyeow
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons after finding.
     *
     * @return summary find message for searches
     */
    public static String getFindMessageForPersonListShownSummary() {
        FindResults.getInstance().formTextResults();
        FindResults.getInstance().clearResults(); // clear current search results
        return FindResults.getInstance().getTextResults();
    }

    //@@author
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    /**
     * Return the parsed result of the command for real time parsing and validating
     * @return the parsed result of the command, default to null
     */
    public String getParsedResult() {
        return null;
    }
}
