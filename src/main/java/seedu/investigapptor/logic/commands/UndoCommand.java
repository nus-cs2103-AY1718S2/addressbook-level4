package seedu.investigapptor.logic.commands;

import static seedu.investigapptor.commons.util.CollectionUtil.requireAllNonNull;

import seedu.investigapptor.logic.CommandHistory;
import seedu.investigapptor.logic.UndoRedoStack;
import seedu.investigapptor.logic.commands.exceptions.CommandException;
import seedu.investigapptor.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_ALIAS = "u";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popUndo().undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
