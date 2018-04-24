package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage; // only required for export command, can be null otherwise
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of activities.
     *
     * @param displaySize used to generate summary
     * @return summary message for activities displayed
     */
    public static String getMessageForActivityListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_ACTIVITY_LISTED_OVERVIEW, displaySize);
    }

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

    //@@author karenfrilya97
    /**
     * Provides any needed dependencies to the command.
     */
    public void setData(Model model, Storage storage, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.setData(model, history, undoRedoStack);
        this.storage = storage;
    }
}
