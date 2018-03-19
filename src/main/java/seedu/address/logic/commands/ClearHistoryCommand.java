package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Clears the history of user input commands.
 */
public class ClearHistoryCommand extends Command {

    public static final String COMMAND_WORD = "clearhistory";
    public static final String MESSAGE_SUCCESS = "Your history has been cleared.";

    @Override
    public CommandResult execute() {
        history.clearHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        requireNonNull(history);
        this.history = history;
    }
}
